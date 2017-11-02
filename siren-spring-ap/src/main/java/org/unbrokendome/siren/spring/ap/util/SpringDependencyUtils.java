package org.unbrokendome.siren.spring.ap.util;

public final class SpringDependencyUtils {

    public static boolean isSpringWebMvcPresent() {
        return ClassUtils.isPresent("org.springframework.web.servlet.support.ServletUriComponentsBuilder");
    }

    public static boolean isSpringWebFluxPresent() {
        return ClassUtils.isPresent("org.springframework.web.reactive.function.server.ServerRequest");
    }
}
