
package io.openliberty.oat.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Path("jaxb")
@Produces({"application/xml"})
@EAT({"modules/testcases/OpenLiberty/jaxrs/src/main/java"})
public class JaxbResource {
    @GET
    public JaxbModel get() {
        return new JaxbModel("John","Citizen");
    }
}
