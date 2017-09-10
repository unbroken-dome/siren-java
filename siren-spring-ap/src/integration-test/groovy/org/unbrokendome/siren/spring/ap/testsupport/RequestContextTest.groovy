package org.unbrokendome.siren.spring.ap.testsupport

import org.junit.After
import org.junit.Before
import org.spockframework.mock.MockNature
import org.springframework.context.MessageSource
import org.springframework.context.support.StaticMessageSource
import org.springframework.http.HttpMethod
import org.springframework.mock.web.MockServletContext
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.DispatcherServlet
import spock.lang.Shared
import spock.mock.DetachedMockFactory
import spock.mock.MockFactory
import spock.mock.MockingApi

import javax.servlet.http.HttpServletRequest
import java.util.function.Consumer


trait RequestContextTest {

    private final mockFactory = new DetachedMockFactory()

    def servletContext = new MockServletContext()

    StaticMessageSource messageSource = new StaticMessageSource()

    WebApplicationContext applicationContext = mockFactory.Stub(WebApplicationContext,
            defaultResponse: new DelegatingDefaultResponse<>(messageSource, MessageSource))

    @After
    void resetRequest() {
        RequestContextHolder.resetRequestAttributes()
    }

    void currentRequest(HttpMethod httpMethod, String url,
                        Consumer<MockHttpServletRequestBuilder> config = null) {

        def request = createRequest(httpMethod, url, config)
        def requestAttributes = new ServletRequestAttributes(request)
        RequestContextHolder.setRequestAttributes(requestAttributes)
    }

    HttpServletRequest createRequest(HttpMethod httpMethod, String url,
                                     Consumer<MockHttpServletRequestBuilder> config = null) {
        def builder = MockMvcRequestBuilders.request(httpMethod, URI.create(url))
                .requestAttr(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext)
        if (config != null) {
            config.accept(builder)
        }
        return builder.buildRequest(servletContext)
    }
}
