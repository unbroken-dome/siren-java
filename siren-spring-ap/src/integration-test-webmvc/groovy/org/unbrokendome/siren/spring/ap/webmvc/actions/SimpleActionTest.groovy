package org.unbrokendome.siren.spring.ap.webmvc.actions

import org.springframework.http.HttpMethod
import org.unbrokendome.siren.spring.ap.webmvc.testsupport.RequestContextTest
import org.unbrokendome.siren.model.ActionBuilder
import org.unbrokendome.siren.spring.ap.testsupport.CompilerTest
import org.unbrokendome.siren.spring.ap.testsupport.SourceCode
import spock.lang.Specification

import java.util.function.Consumer


class SimpleActionTest extends Specification implements CompilerTest, RequestContextTest {

    @SourceCode('example.PingController')
    static final String CONTROLLER_SOURCE = '''
        package example;
        
        import org.unbrokendome.siren.model.RootEntity;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        
        public class PingController {
            @RequestMapping(value="/", method=RequestMethod.POST)
            public RootEntity ping() {
                return RootEntity.builder().build();
            }
        }
        '''


    def "Should generate ControllerActions class"() {
        when:
            compile()
        then:
            classPresent('example.PingControllerActions')
    }


    def "Should generate action for controller method"() {
        given:
            def PingControllerActions = assumeClassPresent('example.PingControllerActions')
        and:
            currentRequest(HttpMethod.GET, 'http://api.example.com/')

        when:
            Consumer<ActionBuilder> actionSpec = PingControllerActions.ping()
            def action = new ActionBuilder('ping').with(actionSpec).build()

        then:
            action.href == 'http://api.example.com/'
            action.method == 'POST'
    }
}
