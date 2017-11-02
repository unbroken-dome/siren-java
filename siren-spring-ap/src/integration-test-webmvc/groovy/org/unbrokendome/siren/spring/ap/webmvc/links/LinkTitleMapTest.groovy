package org.unbrokendome.siren.spring.ap.webmvc.links

import org.springframework.http.HttpMethod
import org.unbrokendome.siren.model.LinkBuilder
import org.unbrokendome.siren.spring.ap.testsupport.CompilerTest
import org.unbrokendome.siren.spring.ap.webmvc.testsupport.RequestContextTest
import org.unbrokendome.siren.spring.ap.testsupport.SourceCode
import spock.lang.Specification

import java.util.function.Consumer


class LinkTitleMapTest extends Specification implements CompilerTest, RequestContextTest {

    @SourceCode('example.HomeController')
    static final String HOMECONTROLLER_SOURCE = '''
        package example;
        
        import org.unbrokendome.siren.annotation.LinkTitle;
        import org.unbrokendome.siren.model.RootEntity;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        
        public class HomeController {
            @RequestMapping(value="/", method=RequestMethod.GET)
            @LinkTitle(rel="self", value="self home title")
            @LinkTitle(rel="other", value="other home title")
            @LinkTitle("default home title")
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


    def "Should generate different link titles for controller method"() {
        given:
            def HomeControllerLinks = assumeClassPresent('example.HomeControllerLinks')
        and:
            currentRequest(HttpMethod.GET, 'http://api.example.com/')

        when:
            Consumer<LinkBuilder> linkSpec = HomeControllerLinks.home()
            def selfLink = new LinkBuilder('self').with(linkSpec).build()
            def otherLink = new LinkBuilder('other').with(linkSpec).build()
            def yetAnotherLink = new LinkBuilder('yetanother').with(linkSpec).build()

        then:
            selfLink.title == 'self home title'
        and:
            otherLink.title == 'other home title'
        and:
            yetAnotherLink.title == 'default home title'
    }
}
