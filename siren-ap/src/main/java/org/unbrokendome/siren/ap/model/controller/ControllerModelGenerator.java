package org.unbrokendome.siren.ap.model.controller;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import java.util.Set;


public interface ControllerModelGenerator {

    @Nonnull
    ControllerModel generate(Set<? extends TypeElement> requestMappingAnnotationTypes);
}
