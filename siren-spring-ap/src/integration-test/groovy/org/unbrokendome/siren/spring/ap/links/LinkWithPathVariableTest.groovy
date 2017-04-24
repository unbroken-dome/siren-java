package org.unbrokendome.siren.spring.ap.links

import org.springframework.http.HttpMethod
import org.unbrokendome.siren.spring.ap.testsupport.CompilerTest
import org.unbrokendome.siren.spring.ap.testsupport.RequestContextTest
import org.unbrokendome.siren.model.LinkBuilder
import org.unbrokendome.siren.spring.ap.testsupport.SourceCode
import spock.lang.Specification

import java.util.function.Consumer


class LinkWithPathVariableTest extends Specification implements CompilerTest, RequestContextTest {

    @SourceCode('example.CustomerController')
    static final String CUSTOMERCONTROLLER_SOURCE = '''
        package example;
        
        import org.unbrokendome.siren.model.RootEntity;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.PathVariable;
        
        public class CustomerController {
            @RequestMapping(value="/customers/{customerId}", method=RequestMethod.GET)
            public RootEntity getCustomer(@PathVariable int customerId) {
                return RootEntity.builder().build();
            }
        }
        '''


    def "Should generate ControllerLinks class"() {
        when:
            compile()
        then:
            classPresent('example.CustomerControllerLinks')
    }


    def "Should generate link for controller method"() {
        given:
            def CustomerControllerLinks = assumeClassPresent('example.CustomerControllerLinks')
        and:
            currentRequest(HttpMethod.GET, 'http://api.example.com/')

        when:
            Consumer<LinkBuilder> linkSpec = CustomerControllerLinks.getCustomer(123)
            def link = new LinkBuilder('self').with(linkSpec).build()

        then:
            link.href == 'http://api.example.com/customers/123'
    }
}
