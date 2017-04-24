package org.unbrokendome.siren.spring.ap.controller;

import com.google.common.base.Splitter;
import com.google.common.net.MediaType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


public class AnnotationRequestMappingInfo extends AbstractRequestMappingInfo {

    @Nonnull
    protected final RequestMappingAnnotationMirror requestMappingAnnotation;


    protected AnnotationRequestMappingInfo(@Nonnull RequestMappingAnnotationMirror requestMappingAnnotation) {
        this.requestMappingAnnotation = requestMappingAnnotation;
    }


    @Nullable
    @Override
    public String getName() {
        return requestMappingAnnotation.name();
    }


    @Nonnull
    @Override
    public List<String> getPathSegments() {
        String path = requestMappingAnnotation.path();
        return Splitter.on('/').omitEmptyStrings().splitToList(path);
    }


    @Nonnull
    @Override
    public Set<String> getRequestMethods() {
        return requestMappingAnnotation.method();
    }


    @Nonnull
    @Override
    public List<MediaType> getConsumedMediaTypes() {
        return requestMappingAnnotation.consumes().stream()
                .map(MediaType::parse)
                .collect(toList());
    }


    @Nonnull
    @Override
    public List<MediaType> getProducedMediaTypes() {
        return requestMappingAnnotation.produces().stream()
                .map(MediaType::parse)
                .collect(toList());
    }
}
