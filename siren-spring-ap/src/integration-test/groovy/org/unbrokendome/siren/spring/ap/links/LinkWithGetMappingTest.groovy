package org.unbrokendome.siren.spring.ap.links

import org.springframework.http.HttpMethod
import org.unbrokendome.siren.model.LinkBuilder
import org.unbrokendome.siren.spring.ap.testsupport.CompilerTest
import org.unbrokendome.siren.spring.ap.testsupport.RequestContextTest
import org.unbrokendome.siren.spring.ap.testsupport.SourceCode
import spock.lang.Specification

import java.util.function.Consumer


class LinkWithGetMappingTest extends Specification implements CompilerTest, RequestContextTest {

    @SourceCode('example.HomeController')
    static final String HOMECONTROLLER_SOURCE = '''
        package example;
        
        import org.unbrokendome.siren.model.RootEntity;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        
        public class HomeController {
            @GetMapping("/")
            public RootEntity home() {
                return RootEntity.builder().build();
            }
        }
        '''


    def "Should generate ControllerLinks class"() {
        when:
            compile()
        then:
            classPresent('example.HomeControllerLinks')
    }


    def "Should generate link for controller method"() {
        given:
            def HomeControllerLinks = assumeClassPresent('example.HomeControllerLinks')
        and:
            currentRequest(HttpMethod.GET, 'http://api.example.com/')

        when:
            Consumer<LinkBuilder> linkSpec = HomeControllerLinks.home()
            def link = new LinkBuilder('self').with(linkSpec).build()

        then:
            link.href == 'http://api.example.com/'
    }
}
