package io.openliberty.oat.jaxb;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.test.integration.common.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.aries.transaction.jms.internal.XaPooledConnectionFactory;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.springframework.jacksontest.BogusApplicationContext;
import org.springframework.jacksontest.BogusPointcutAdvisor;
import com.mchange.v2.c3p0.jacksonTest.ComboPooledDataSource;
import org.apache.ibatis.datasource.jndi.JndiDataSourceFactory;
import org.springframework.jacksontest.AbstractPointcutAdvisor;
import org.springframework.jacksontest.AbstractApplicationContext;
import org.hibernate.jmx.StatisticsService;
import org.apache.openjpa.ee.JNDIManagedRuntime;
import org.jboss.eap.additional.testsuite.annotations.ATFeature;

/**
 * Tests a JAX-RS deployment without an application bundled.
 *
 * The container should register a servlet with the name
 *
 * javax.ws.rs.core.Application
 *
 * It is the app providers responsibility to provide a mapping for the servlet
 *
 * JAX-RS 1.1 2.3.2 bullet point 1
 *
 * @author Stuart Douglas
 */
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/OpenLiberty/jaxrs/src/main/java"})
public class JaxbProviderDeserializationSecurityCheckTestCase {

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,"jaxrssecurity.war");
        war.addPackage(HttpRequest.class.getPackage());
        war.addPackages(true, "com.fasterxml.jackson");
        war.addClasses(JaxbProviderDeserializationSecurityCheckTestCase.class, JaxbResourceDeserializationSecurityCheck.class,ExampleApplication.class,
                       org.springframework.jacksontest.BogusPointcutAdvisor.class, org.springframework.jacksontest.AbstractPointcutAdvisor.class,
                       org.springframework.jacksontest.BogusApplicationContext.class, org.springframework.jacksontest.AbstractApplicationContext.class,
                       org.apache.ibatis.datasource.jndi.JndiDataSourceFactory.class, org.hibernate.jmx.StatisticsService.class,
                       TestMapperResolver.class,com.mchange.v2.c3p0.jacksonTest.ComboPooledDataSource.class, org.apache.openjpa.ee.JNDIManagedRuntime.class,
                       org.apache.openjpa.ee.ManagedRuntime.class,org.apache.openjpa.ee.AbstractManagedRuntime.class, org.apache.aries.transaction.jms.internal.XaPooledConnectionFactory.class);
        war.addPackage(org.apache.openjpa.util.GeneralException.class.getPackage());
        war.addPackage(org.apache.openjpa.lib.util.Localizer.class.getPackage());
        return war;
    }

    @ArquillianResource
    private URL url;

    private String performCall(String urlPattern) throws Exception {
        return HttpRequest.get(url + urlPattern, 10, TimeUnit.SECONDS);
    }

    @Test
    @ATFeature(feature={"jaxrs,jaxb,jsonp,cdi,localConnector,servlet"},minVersion={"2.1,2.2,1.1,2.0,1.0,4.0"},maxVersion={"null,null,null,null,null,null"})
    public void testPointcutAdvisor() throws Exception {
        String result = performCall("rest/jaxb/advisor");

        try{
            BogusPointcutAdvisor jaxbModel = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(result, BogusPointcutAdvisor.class);
            Assert.fail("Should prevente json deserialization because of security reasons.");
        }catch(JsonMappingException e){
            Assert.assertTrue("Should prevente json deserialization because of security reasons.", e.getMessage().contains("Illegal type"));
        }

    }

    @Test
    @ATFeature(feature={"jaxrs,jaxb,jsonp,cdi,localConnector,servlet"},minVersion={"2.1,2.2,1.1,2.0,1.0,4.0"},maxVersion={"null,null,null,null,null,null"})
    public void testApplicationContext() throws Exception {
        String result = performCall("rest/jaxb/appcontext");

        try{
            BogusApplicationContext jaxbModel = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(result, BogusApplicationContext.class);
            Assert.fail("Should prevente json deserialization because of security reasons.");
        }catch(JsonMappingException e){
            Assert.assertTrue("Should prevente json deserialization because of security reasons.", e.getMessage().contains("Illegal type"));
        }

    }
    
    @Test
    @ATFeature(feature={"jaxrs,jaxb,jsonp,cdi,localConnector,servlet"},minVersion={"2.1,2.2,1.1,2.0,1.0,4.0"},maxVersion={"null,null,null,null,null,null"})
    public void testMChangeV2C3p0() throws Exception {
        String result = performCall("rest/jaxb/mchange");

        try{
            ComboPooledDataSource jaxbModel = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(result, ComboPooledDataSource.class);
            Assert.fail("Should prevente json deserialization because of security reasons.");
        }catch(JsonMappingException e){
            Assert.assertTrue("Should prevente json deserialization because of security reasons.", e.getMessage().contains("Illegal type"));
        }

    }

    @Test
    @ATFeature(feature={"jaxrs,jaxb,jsonp,cdi,localConnector,servlet"},minVersion={"2.1,2.2,1.1,2.0,1.0,4.0"},maxVersion={"null,null,null,null,null,null"})
    public void testStatisticsService() throws Exception{
        String result = performCall("rest/jaxb/statistics");

        try{
            StatisticsService jaxbModel = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(result, StatisticsService.class);
            Assert.fail("Should prevente json deserialization because of security reasons.");
        }catch(JsonMappingException e){
            Assert.assertTrue("Should prevente json deserialization because of security reasons.", e.getMessage().contains("Illegal type"));
        }

    }

    @Test
    @ATFeature(feature={"jaxrs,jaxb,jsonp,cdi,localConnector,servlet"},minVersion={"2.1,2.2,1.1,2.0,1.0,4.0"},maxVersion={"null,null,null,null,null,null"})
    public void testMyBatisJndiDataSourceFactory() throws Exception{
        String result = performCall("rest/jaxb/datasource");

        try{
            JndiDataSourceFactory jaxbModel = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(result, JndiDataSourceFactory.class);
            Assert.fail("Should prevente json deserialization because of security reasons.");
        }catch(JsonMappingException e){
            Assert.assertTrue("Should prevente json deserialization because of security reasons.", e.getMessage().contains("Illegal type"));
        }
    }

    @Test
    @ATFeature(feature={"jaxrs,jaxb,jsonp,cdi,localConnector,servlet"},minVersion={"2.1,2.2,1.1,2.0,1.0,4.0"},maxVersion={"null,null,null,null,null,null"})
    public void testXaPooledConnectionFactoryService() throws Exception{
        try {
            String result = performCall("rest/jaxb/xapooledconnectionfactory");

            try{
                XaPooledConnectionFactory jaxbModel = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(result, XaPooledConnectionFactory.class);
                Assert.fail("Should prevente json deserialization because of security reasons.");
            }catch(JsonMappingException e){
                Assert.assertTrue("Should prevente json deserialization because of security reasons.", e.getMessage().contains("Illegal type"));
            }
        }catch(Exception ex){
            Assert.assertTrue("In case it is not called we are fine ... ", true);
        }
    }

    @Test
    public void defaultTest(){
        System.out.println("Adding a default test for usage of this class with the EAT workshop");
    }

}
