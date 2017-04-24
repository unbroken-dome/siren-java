package org.unbrokendome.siren.spring.ap.controller;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;


public class DefaultRequestMappingAnnotationMirror extends AbstractRequestMappingAnnotationMirror {

    public DefaultRequestMappingAnnotationMirror(@Nonnull AnnotationMirror annotation) {
        super(annotation);
    }
}
