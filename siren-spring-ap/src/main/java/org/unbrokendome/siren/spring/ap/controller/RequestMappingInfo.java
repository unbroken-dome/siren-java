package org.unbrokendome.siren.spring.ap.controller;

import com.google.common.net.MediaType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface RequestMappingInfo {

    @Nullable
    String getName();


    @Nonnull
    List<String> getPathSegments();


    @Nonnull
    Set<String> getRequestMethods();


    @Nonnull
    List<MediaType> getConsumedMediaTypes();


    @Nonnull
    List<MediaType> getProducedMediaTypes();


    @Nonnull
    static Optional<RequestMappingInfo> fromElement(Element element) {
        RequestMappingAnnotationMirror annotation = RequestMappingAnnotationMirror.findOnElement(element);
        if (annotation != null) {
            return Optional.of(new AnnotationRequestMappingInfo(annotation));
        } else {
            return Optional.empty();
        }
    }


    @Nonnull
    static RequestMappingInfo fromHandlerMethod(ExecutableElement handlerMethod) {

        RequestMappingInfo methodLevelMappingInfo = fromElement(handlerMethod)
                .orElseThrow(() -> new IllegalArgumentException("Method " + handlerMethod + " is not annotated with @RequestMapping"));

        return fromElement(handlerMethod.getEnclosingElement())
                .map(typeLevelMappingInfo ->
                        (RequestMappingInfo) new CombinedRequestMappingInfo(typeLevelMappingInfo, methodLevelMappingInfo))
                .orElse(methodLevelMappingInfo);
    }
}
