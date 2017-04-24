package org.unbrokendome.siren.spring.ap.controller;

import org.unbrokendome.siren.ap.AnnotationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


abstract class AbstractRequestMappingAnnotationMirror implements RequestMappingAnnotationMirror {

    private final AnnotationMirror annotation;


    public AbstractRequestMappingAnnotationMirror(@Nonnull AnnotationMirror annotation) {
        this.annotation = annotation;
    }


    @Override
    @Nullable
    public String name() {
        return AnnotationUtils.getElementValueAsString(annotation, "name");
    }


    @Override
    @Nonnull
    public String path() {
        List<String> path = AnnotationUtils.getElementValueAsList(annotation, "path");
        if (!path.isEmpty()) {
            return path.get(0);
        }
        path = AnnotationUtils.getElementValueAsList(annotation, "value");
        if (!path.isEmpty()) {
            return path.get(0);
        }
        return "";
    }


    @Override
    @Nonnull
    public Set<String> method() {
        List<VariableElement> methods = AnnotationUtils.getElementValueAsList(annotation, "method");
        return methods.stream()
                .map(it -> it.getSimpleName().toString())
                .collect(Collectors.toSet());
    }


    @Override
    @Nonnull
    public List<String> params() {
        return AnnotationUtils.getElementValueAsList(annotation, "params");
    }


    @Override
    @Nonnull
    public List<String> headers() {
        return AnnotationUtils.getElementValueAsList(annotation, "headers");
    }


    @Override
    @Nonnull
    public List<String> consumes() {
        return AnnotationUtils.getElementValueAsList(annotation, "consumes");
    }


    @Override
    @Nonnull
    public List<String> produces() {
        return AnnotationUtils.getElementValueAsList(annotation, "produces");
    }
}
