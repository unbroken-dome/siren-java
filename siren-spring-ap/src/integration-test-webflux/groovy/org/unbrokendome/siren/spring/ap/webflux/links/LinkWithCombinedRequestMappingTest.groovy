package org.unbrokendome.siren.spring.ap.webflux.links

import org.springframework.http.HttpMethod
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import org.unbrokendome.siren.model.LinkBuilder
import org.unbrokendome.siren.spring.ap.testsupport.CompilerTest
import org.unbrokendome.siren.spring.ap.testsupport.SourceCode
import spock.lang.Specification

import java.util.function.Consumer


class LinkWithCombinedRequestMappingTest extends Specification implements CompilerTest {

    @SourceCode('example.CustomerController')
    static final String CUSTOMERCONTROLLER_SOURCE = '''
        package example;
        
        import org.unbrokendome.siren.model.RootEntity;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.PathVariable;
        
        @RequestMapping("/customers")
        public class CustomerController {
            @RequestMapping(value="/{customerId}", method=RequestMethod.GET)
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
            def request = MockServerRequest.builder()
                    .method(HttpMethod.GET)
                    .uri(URI.create('http://api.example.com/'))
                    .build()

        when:
            Consumer<LinkBuilder> linkSpec = CustomerControllerLinks.getCustomer(request, 123)
            def link = new LinkBuilder('self').with(linkSpec).build()

        then:
            link.href == 'http://api.example.com/customers/123'
    }
}
