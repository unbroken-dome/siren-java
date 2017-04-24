package org.unbrokendome.siren.spring.ap.controller;

import org.unbrokendome.siren.ap.AnnotationUtils;

import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;


public abstract class AbstractNamedParameterAnnotationMirror implements NamedParameterAnnotationMirror {

    private final AnnotationMirror annotation;


    protected AbstractNamedParameterAnnotationMirror(AnnotationMirror annotation) {
        this.annotation = annotation;
    }


    @Override
    @Nullable
    public String name() {
        String name = AnnotationUtils.getElementValueAsString(annotation, "name");
        if (name != null && !name.isEmpty()) {
            return name;
        }
        return AnnotationUtils.getElementValueAsString(annotation, "value");
    }


    protected final AnnotationMirror getAnnotation() {
        return annotation;
    }
}
