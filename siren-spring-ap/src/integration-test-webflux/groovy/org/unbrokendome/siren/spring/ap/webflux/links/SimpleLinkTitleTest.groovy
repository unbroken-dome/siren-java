package org.unbrokendome.siren.spring.ap.webflux.links

import org.springframework.http.HttpMethod
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import org.unbrokendome.siren.model.LinkBuilder
import org.unbrokendome.siren.spring.ap.testsupport.CompilerTest
import org.unbrokendome.siren.spring.ap.testsupport.SourceCode
import spock.lang.Specification

import java.util.function.Consumer


class SimpleLinkTitleTest extends Specification implements CompilerTest {

    @SourceCode('example.HomeController')
    static final String HOMECONTROLLER_SOURCE = '''
        package example;
        
        import org.unbrokendome.siren.annotation.LinkTitle;
        import org.unbrokendome.siren.model.RootEntity;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        
        public class HomeController {
            @RequestMapping(value="/", method=RequestMethod.GET)
            @LinkTitle("home title")
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


    def "Should generate link title for controller method"() {
        given:
            def HomeControllerLinks = assumeClassPresent('example.HomeControllerLinks')
        and:
            def request = MockServerRequest.builder()
                    .method(HttpMethod.GET)
                    .uri(URI.create('http://api.example.com/'))
                    .build()

        when:
            Consumer<LinkBuilder> linkSpec = HomeControllerLinks.home(request)
            def link = new LinkBuilder('self').with(linkSpec).build()

        then:
            link.title == 'home title'
    }
}
