package org.unbrokendome.siren.spring.ap.controller;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.util.List;
import java.util.Set;


public interface RequestMappingAnnotationMirror {

    @Nullable
    String name();

    @Nonnull
    String path();

    @Nonnull
    Set<String> method();

    @Nonnull
    List<String> params();

    @Nonnull
    List<String> headers();

    @Nonnull
    List<String> consumes();

    @Nonnull
    List<String> produces();


    @Nullable
    static RequestMappingAnnotationMirror fromAnnotation(AnnotationMirror annotation) {
        return RequestMappingAnnotationMirrorFactory.fromAnnotation(annotation);
    }


    @Nullable
    static RequestMappingAnnotationMirror findOnElement(Element element) {
        return RequestMappingAnnotationMirrorFactory.findOnElement(element);
    }
}
