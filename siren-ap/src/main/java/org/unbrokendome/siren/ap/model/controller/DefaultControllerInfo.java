package org.unbrokendome.siren.ap.model.controller;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import java.util.Collection;


public class DefaultControllerInfo implements ControllerInfo {

    private final TypeElement controllerType;
    private final Collection<RequestHandlerMethodInfo> handlerMethods;


    public DefaultControllerInfo(TypeElement controllerType, Collection<RequestHandlerMethodInfo> handlerMethods) {
        this.controllerType = controllerType;
        this.handlerMethods = handlerMethods;
    }


    @Nonnull
    @Override
    public TypeElement getControllerType() {
        return controllerType;
    }


    @Nonnull
    @Override
    public Collection<RequestHandlerMethodInfo> getHandlerMethods() {
        return handlerMethods;
    }
}
