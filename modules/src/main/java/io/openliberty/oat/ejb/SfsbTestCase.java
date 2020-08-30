
package io.openliberty.oat.ejb;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.net.URL;

@RunAsClient
@RunWith(Arquillian.class)
@EAT({"modules/testcases/OpenLiberty/ejb/src/main/java"})
public class SfsbTestCase {

    public static final String DEPLOYMENT = "sfsbServlet.war";

    @Deployment(name = DEPLOYMENT)
    public static Archive<?> getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT);
        war.addClass(SfsbServlet.class);
        war.addClass(ShoppingCart.class);
        war.addClass(ShoppingCartBean.class);
        return war;
    }

    @Test
    @ATFeature(feature={"ejb,localConnector,servlet"},minVersion={"3.2,1.0,4.0"},maxVersion={"null,null,null"})
    @OperateOnDeployment(DEPLOYMENT)
    public void sfsbTest(@ArquillianResource URL url) throws Exception {
        URL testURL = new URL(url.toString() + "sfsbCache?product=makaronia&quantity=10");

        final HttpGet request = new HttpGet(testURL.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testURL, HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
            Thread.sleep(1000);
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testURL, HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
        }finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }
}
