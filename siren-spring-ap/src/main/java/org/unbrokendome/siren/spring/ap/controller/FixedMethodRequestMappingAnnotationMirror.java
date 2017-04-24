package org.unbrokendome.siren.spring.ap.controller;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import java.util.Collections;
import java.util.Set;


public class FixedMethodRequestMappingAnnotationMirror extends AbstractRequestMappingAnnotationMirror {

    private final String method;


    public FixedMethodRequestMappingAnnotationMirror(@Nonnull AnnotationMirror annotation,
                                                     @Nonnull String method) {
        super(annotation);
        this.method = method;
    }


    @Override
    @Nonnull
    public Set<String> method() {
        return Collections.singleton(method);
    }
}
