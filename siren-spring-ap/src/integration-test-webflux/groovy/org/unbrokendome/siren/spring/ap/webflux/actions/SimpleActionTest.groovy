package org.unbrokendome.siren.spring.ap.webflux.actions

import org.springframework.http.HttpMethod
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import org.unbrokendome.siren.model.ActionBuilder
import org.unbrokendome.siren.spring.ap.testsupport.CompilerTest
import org.unbrokendome.siren.spring.ap.testsupport.SourceCode
import spock.lang.Specification

import java.util.function.Consumer


class SimpleActionTest extends Specification implements CompilerTest {

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
            def request = MockServerRequest.builder()
                    .method(HttpMethod.GET)
                    .uri(URI.create('http://api.example.com/'))
                    .build()

        when:
            Consumer<ActionBuilder> actionSpec = PingControllerActions.ping(request)
            def action = new ActionBuilder('ping').with(actionSpec).build()

        then:
            action.href == 'http://api.example.com/'
            action.method == 'POST'
    }
}
