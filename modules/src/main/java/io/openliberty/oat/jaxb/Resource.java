package io.openliberty.oat.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Path("resource")
@Produces({"application/xml"})
@EAT({"modules/testcases/OpenLiberty/jaxrs/src/main/java"})
public class Resource {
    @GET
    public Model get() {
        return new Model("Niki");
    }
}
