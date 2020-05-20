package io.openliberty.oat.jaxb;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;
import org.jboss.eap.additional.testsuite.annotations.ATFeature;

@EapAdditionalTestsuite({"modules/testcases/OpenLiberty/jaxrs/src/main/java"})
@RunWith(Arquillian.class)
@RunAsClient
public class JaxbTest {

    private final static String WARNAME = "arquillian-managed.war";

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,WARNAME);
        war.addClasses(JaxbTest.class, Model.class, Resource.class, TestApplication.class);
        return war;
    }

    @ArquillianResource
    private URL urlresouce;

    private String httpCall(String urlPattern) throws Exception {
        final URL url = new URL(urlresouce + urlPattern);
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                int responseCode = conn.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
		    final InputStream err = conn.getErrorStream();
		    try {
		        throw new Exception("Error in response ...");
		    }
		    finally {
		        err.close();
		    }
		}
		final InputStream in = conn.getInputStream();
		try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    int b;
		    while((b = in.read()) != -1) {
		        out.write(b);
		    }
		    return out.toString();
		}
		finally {
		    in.close();
		}
            }
        };

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<String> result = executor.submit(task);
        try {
            return result.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            // should not happen
            throw e;
        } finally {
            executor.shutdownNow();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    @Test
    @ATFeature(feature={"jaxrs,jaxb,jsonp,cdi,localConnector,servlet"},minVersion={"2.1,2.2,1.1,2.0,1.0,4.0"},maxVersion={"null,null,null,null,null,null"})
    public void testJaxRs() throws Exception {
        String result = httpCall("rest/resource");
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><model><name>Niki</name></model>", result);
    }

    @Test
    public void defaultTest() {
    }

}
