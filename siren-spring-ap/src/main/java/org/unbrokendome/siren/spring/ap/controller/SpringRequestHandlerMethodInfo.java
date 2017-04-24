package org.unbrokendome.siren.spring.ap.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;
import org.unbrokendome.siren.ap.AnnotationUtils;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SpringRequestHandlerMethodInfo implements RequestHandlerMethodInfo {

    private final ExecutableElement handlerMethod;
    private final RequestMappingInfo requestMappingInfo;
    private final Elements elements;


    public SpringRequestHandlerMethodInfo(ExecutableElement handlerMethod, Elements elements) {
        this.handlerMethod = handlerMethod;
        this.elements = elements;
        this.requestMappingInfo = RequestMappingInfo.fromHandlerMethod(handlerMethod);
    }


    @Nonnull
    @Override
    public ExecutableElement getHandlerMethod() {
        return handlerMethod;
    }


    @Nonnull
    @Override
    public Map<String, VariableElement> getPathParameters() {

        ImmutableMap.Builder<String, VariableElement> builder = ImmutableMap.builder();

        handlerMethod.accept(new ElementScanner8<Void, Void>() {
            @Override
            public Void visitVariable(VariableElement e, Void aVoid) {
                if (e.getKind() == ElementKind.PARAMETER) {
                    PathVariableAnnotationMirror annotation = PathVariableAnnotationMirror.findOnElement(e);
                    if (annotation != null) {
                        String pathVariableName = getNameFromAnnotatedParameter(e, annotation);
                        builder.put(pathVariableName, e);
                    }
                }
                return aVoid;
            }
        }, null);

        return builder.build();
    }


    @Nonnull
    @Override
    public Map<String, VariableElement> getQueryParameters() {
        ImmutableMap.Builder<String, VariableElement> builder = ImmutableMap.builder();

        handlerMethod.accept(new ElementScanner8<Void, Void>() {
            @Override
            public Void visitVariable(VariableElement e, Void aVoid) {
                if (e.getKind() == ElementKind.PARAMETER) {
                    RequestParamAnnotationMirror annotation = RequestParamAnnotationMirror.findOnElement(e);
                    if (annotation != null) {
                        String requestParamName = getNameFromAnnotatedParameter(e, annotation);
                        builder.put(requestParamName, e);
                    }
                }
                return aVoid;
            }
        }, null);

        return builder.build();
    }


    @Nullable
    @Override
    public VariableElement getRequestBodyParameter() {

        final TypeElement requestBodyAnnotationType = elements.getTypeElement(
                "org.springframework.web.bind.annotation.RequestBody");

        return handlerMethod.accept(new ElementScanner8<VariableElement, Void>() {
            @Override
            public VariableElement visitVariable(VariableElement e, Void aVoid) {
                if (AnnotationUtils.hasAnnotation(e, requestBodyAnnotationType)) {
                    return e;
                } else {
                    return null;
                }
            }
        }, null);
    }


    @Nonnull
    @Override
    public List<String> getPathSegments() {
        return requestMappingInfo.getPathSegments();
    }


    @Nonnull
    @Override
    public Set<String> getRequestMethods() {
        return requestMappingInfo.getRequestMethods();
    }


    @Nonnull
    @Override
    public List<MediaType> getConsumedMediaTypes() {
        return requestMappingInfo.getConsumedMediaTypes();
    }


    @Nonnull
    @Override
    public List<MediaType> getProducedMediaTypes() {
        return requestMappingInfo.getProducedMediaTypes();
    }


    @Nonnull
    private String getNameFromAnnotatedParameter(VariableElement parameterElement,
                                                 NamedParameterAnnotationMirror annotation) {
        String nameFromAnnotation = annotation.name();
        if (nameFromAnnotation != null) {
            return nameFromAnnotation;
        }

        return parameterElement.getSimpleName().toString();
    }
}
