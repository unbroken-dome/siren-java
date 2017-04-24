package org.unbrokendome.siren.spring.ap.controller;

import org.unbrokendome.siren.ap.AnnotationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Objects;


public class PathVariableAnnotationMirror extends AbstractNamedParameterAnnotationMirror {


    private PathVariableAnnotationMirror(AnnotationMirror annotation) {
        super(annotation);
    }


    public boolean required() {
        Boolean required = AnnotationUtils.getElementValueAsBoolean(getAnnotation(), "required");
        return required != null ? required : false;
    }


    @Nullable
    public static PathVariableAnnotationMirror fromAnnotation(@Nonnull AnnotationMirror annotation) {
        Name qualifiedName = ((TypeElement) annotation.getAnnotationType().asElement()).getQualifiedName();
        if (qualifiedName.contentEquals("org.springframework.web.bind.annotation.PathVariable")) {
            return new PathVariableAnnotationMirror(annotation);
        } else {
            return null;
        }
    }


    @Nullable
    public static PathVariableAnnotationMirror findOnElement(@Nonnull VariableElement element) {
        return element.getAnnotationMirrors().stream()
                .map(PathVariableAnnotationMirror::fromAnnotation)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
