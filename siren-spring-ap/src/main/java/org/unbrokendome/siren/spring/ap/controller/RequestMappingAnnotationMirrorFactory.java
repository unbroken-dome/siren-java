package org.unbrokendome.siren.spring.ap.controller;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.*;
import java.util.function.Function;


/**
 * Provides helper methods to construct {@link RequestMappingAnnotationMirror} instances.
 */
public class RequestMappingAnnotationMirrorFactory {

    private static final Map<String, Function<AnnotationMirror, RequestMappingAnnotationMirror>> FACTORIES =
            ImmutableMap.<String, Function<AnnotationMirror, RequestMappingAnnotationMirror>>builder()
                    .put("org.springframework.web.bind.annotation.RequestMapping",
                            DefaultRequestMappingAnnotationMirror::new)
                    .put("org.springframework.web.bind.annotation.GetMapping",
                            it -> new FixedMethodRequestMappingAnnotationMirror(it, "GET"))
                    .put("org.springframework.web.bind.annotation.PostMapping",
                            it -> new FixedMethodRequestMappingAnnotationMirror(it, "POST"))
                    .put("org.springframework.web.bind.annotation.PutMapping",
                            it -> new FixedMethodRequestMappingAnnotationMirror(it, "PUT"))
                    .put("org.springframework.web.bind.annotation.DeleteMapping",
                            it -> new FixedMethodRequestMappingAnnotationMirror(it, "DELETE"))
                    .put("org.springframework.web.bind.annotation.PatchMapping",
                            it -> new FixedMethodRequestMappingAnnotationMirror(it, "PATCH"))
            .build();


    /**
     * Tries to wrap a given annotation as a request mapping annotation.
     *
     * <p>Returns {@code null} if the given annotation is not a request mapping annotation.
     *
     * @param annotation the annotation to inspect
     * @return a {@link RequestMappingAnnotationMirror} for the annotation, or {@code null}
     */
    @Nullable
    static RequestMappingAnnotationMirror fromAnnotation(@Nonnull AnnotationMirror annotation) {
        Name qualifiedName = ((TypeElement) annotation.getAnnotationType().asElement()).getQualifiedName();
        Function<AnnotationMirror, RequestMappingAnnotationMirror> factoryFunction =
                FACTORIES.get(qualifiedName.toString());
        if (factoryFunction != null) {
            return factoryFunction.apply(annotation);
        }
        return null;
    }


    /**
     * Finds a request mapping annotation on an annotated element.
     *
     * @param element the annotated {@link Element} to inspect
     * @return the {@link RequestMappingAnnotationMirror} for the annotation, or {@code null} if no
     *         suitable annotation was found
     */
    @Nullable
    static RequestMappingAnnotationMirror findOnElement(@Nonnull Element element) {
        return element.getAnnotationMirrors().stream()
                .map(RequestMappingAnnotationMirrorFactory::fromAnnotation)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
