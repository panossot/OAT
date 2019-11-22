package io.openliberty.oat.jaxb;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;


@EapAdditionalTestsuite({"modules/testcases/OpenLiberty/jaxrs/src/main/java"})
@ApplicationPath("/rest")
public class TestApplication extends Application {
 
}
