package org.unbrokendome.siren.spring.ap.actions

import org.springframework.http.HttpMethod
import org.unbrokendome.siren.model.ActionBuilder
import org.unbrokendome.siren.model.ActionField
import org.unbrokendome.siren.spring.ap.testsupport.CompilerTest
import org.unbrokendome.siren.spring.ap.testsupport.RequestContextTest
import org.unbrokendome.siren.spring.ap.testsupport.SourceCode
import spock.lang.Specification

import java.util.function.Consumer


class ActionFieldsFromRequestBodyTest extends Specification implements CompilerTest, RequestContextTest {

    @SourceCode('example.LoginController')
    static final String CONTROLLER_SOURCE = '''
        package example;
        
        import org.unbrokendome.siren.model.RootEntity;
        import org.springframework.web.bind.annotation.*;
        
        public class LoginController {
            @RequestMapping(value="/login", method=RequestMethod.POST)
            public RootEntity login(@RequestBody LoginRequest loginRequest) {
                return RootEntity.builder().build();
            }
        }
        '''


    @SourceCode('example.LoginRequest')
    static final String REQUEST_OBJECT_SOURCE = '''
        package example;
        
        import org.unbrokendome.siren.annotation.ActionFieldType;
        import org.unbrokendome.siren.model.ActionField;
        
        public class LoginRequest {
            private String email;
            private String password;
            
            @ActionFieldType(ActionField.Type.EMAIL)
            public String getEmail() { return email; }
         
            @ActionFieldType(ActionField.Type.PASSWORD)   
            public String getPassword() { return password; }
        }
    '''


    def "Should generate ControllerActions class"() {
        when:
            compile()
        then:
            classPresent('example.LoginControllerActions')
    }


    def "Should generate action for controller method"() {
        given:
            def LoginControllerActions = assumeClassPresent('example.LoginControllerActions')
        and:
            currentRequest(HttpMethod.GET, 'http://api.example.com/')

        when:
            Consumer<ActionBuilder> actionSpec = LoginControllerActions.login()
            def action = new ActionBuilder('login')
                    .with(actionSpec).build()

        then:
            action.href == 'http://api.example.com/login'
            action.method == 'POST'
            action.fields == [
                    new ActionField('email', ActionField.Type.EMAIL),
                    new ActionField('password', ActionField.Type.PASSWORD)
            ]
    }
}
