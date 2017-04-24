package org.unbrokendome.siren.ap.model.controller;

import com.google.common.net.MediaType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface RequestHandlerMethodInfo {

    @Nonnull
    default TypeElement getControllerType() {
        return (TypeElement) getHandlerMethod().getEnclosingElement();
    }

    @Nonnull
    ExecutableElement getHandlerMethod();

    @Nonnull
    default String getName() {
        return getHandlerMethod().getSimpleName().toString();
    }

    @Nonnull
    Map<String, VariableElement> getPathParameters();

    @Nonnull
    Map<String, VariableElement> getQueryParameters();

    @Nullable
    VariableElement getRequestBodyParameter();

    @Nonnull
    List<String> getPathSegments();

    @Nonnull
    Set<String> getRequestMethods();

    @Nonnull
    List<MediaType> getConsumedMediaTypes();

    @Nonnull
    List<MediaType> getProducedMediaTypes();


    default boolean handlesGETRequests() {
        return getRequestMethods().contains("GET");
    }


    default boolean handlesNonGETRequests() {
        Set<String> requestMethods = getRequestMethods();
        return !requestMethods.isEmpty() && (!requestMethods.contains("GET") || requestMethods.size() > 1);
    }
}
