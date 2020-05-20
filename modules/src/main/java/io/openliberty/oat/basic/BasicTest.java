package io.openliberty.oat.basic;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;
import org.jboss.eap.additional.testsuite.annotations.ATFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;

@RunWith(Arquillian.class)
@EapAdditionalTestsuite({"modules/testcases/OpenLiberty/basic/src/main/java"})
public class BasicTest {

    @Deployment
    public static Archive<?> getDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
        archive.addClass(BasicTest.class);
        return archive;
    }

    @Test
    @ATFeature(feature={"localConnector,servlet"},minVersion={"1.0,4.0"},maxVersion={"null,null"})
    public void testServerStart() {
        assertFalse("Running a basic arquillian test ... ", false);
    }

    @Test
    public void defaultTest() {
    }
}
