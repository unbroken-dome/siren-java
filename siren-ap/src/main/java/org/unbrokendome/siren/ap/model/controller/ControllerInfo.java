package org.unbrokendome.siren.ap.model.controller;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import java.util.Collection;


public interface ControllerInfo {

    @Nonnull
    TypeElement getControllerType();


    @Nonnull
    default String getSimpleName() {
        return getControllerType().getSimpleName().toString();
    }


    @Nonnull
    default String getQualifiedName() {
        return getControllerType().getQualifiedName().toString();
    }


    @Nonnull
    Collection<RequestHandlerMethodInfo> getHandlerMethods();
}
