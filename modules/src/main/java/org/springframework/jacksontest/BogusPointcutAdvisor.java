package org.springframework.jacksontest;

import javax.xml.bind.annotation.XmlRootElement;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/OpenLiberty/jaxrs/src/main/java"})
@XmlRootElement
public class BogusPointcutAdvisor extends AbstractPointcutAdvisor {
    public BogusPointcutAdvisor() {
        super();
    }
}
