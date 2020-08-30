
package io.openliberty.oat.jaxb;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;
 
@ApplicationPath("/rest")
@EAT({"modules/testcases/OpenLiberty/jaxrs/src/main/java"})
public class ExampleApplication extends Application {
 
}
