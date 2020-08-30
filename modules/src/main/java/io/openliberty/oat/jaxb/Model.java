package io.openliberty.oat.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.eap.additional.testsuite.annotations.EAT;


@EAT({"modules/testcases/OpenLiberty/jaxrs/src/main/java"})
@XmlRootElement
public class Model {

    private String name;

    public Model(String name) {
        this.name = name;
    }

    public Model() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
