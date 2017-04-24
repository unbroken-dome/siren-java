package org.unbrokendome.siren.spring.ap.testsupport

import org.springframework.context.support.StaticMessageSource
import org.springframework.web.context.WebApplicationContext


abstract class StubWebApplicationContext implements WebApplicationContext {

    @Delegate(includes = 'getMessage')
    final StaticMessageSource messageSource = new StaticMessageSource()
}
