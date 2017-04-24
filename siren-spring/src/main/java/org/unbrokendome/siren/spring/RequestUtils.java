package org.unbrokendome.siren.spring;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;


public class RequestUtils {

    @Nonnull
    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null,
                "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null,
                "Could not find current HttpServletRequest");
        return servletRequest;
    }


    @Nonnull
    public static UriComponentsBuilder createUriBuilderFromCurrentRequest() {
        HttpServletRequest request = getCurrentRequest();
        return createUriBuilderFromRequest(request);
    }


    @Nonnull
    public static UriComponentsBuilder createUriBuilderFromRequest(HttpServletRequest request) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromServletMapping(request);

        String forwardedSsl = request.getHeader("X-Forwarded-Ssl");
        if (StringUtils.hasText(forwardedSsl) && forwardedSsl.equalsIgnoreCase("on")) {
            builder.scheme("https");
        }

        String host = request.getHeader("X-Forwarded-Host");
        if (!StringUtils.hasText(host)) {
            return builder;
        }

        String[] hosts = StringUtils.commaDelimitedListToStringArray(host);
        String hostToUse = hosts[0];

        if (hostToUse.contains(":")) {
            String[] hostAndPort = StringUtils.split(hostToUse, ":");
            builder.host(hostAndPort[0]);
            builder.port(Integer.parseInt(hostAndPort[1]));

        } else {
            builder.host(hostToUse);
            builder.port(-1); // reset port if it was forwarded from default port
        }

        String port = request.getHeader("X-Forwarded-Port");
        if (StringUtils.hasText(port)) {
            builder.port(Integer.parseInt(port));
        }

        return builder;
    }
}
