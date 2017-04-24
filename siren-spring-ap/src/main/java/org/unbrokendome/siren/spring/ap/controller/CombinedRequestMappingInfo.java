package org.unbrokendome.siren.spring.ap.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.net.MediaType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;


public class CombinedRequestMappingInfo extends AbstractRequestMappingInfo {

    private final RequestMappingInfo typeLevelMappingInfo;
    private final RequestMappingInfo methodLevelMappingInfo;


    public CombinedRequestMappingInfo(RequestMappingInfo typeLevelMappingInfo,
                                      RequestMappingInfo methodLevelMappingInfo) {
        this.typeLevelMappingInfo = typeLevelMappingInfo;
        this.methodLevelMappingInfo = methodLevelMappingInfo;
    }


    @Nullable
    @Override
    public String getName() {
        return methodLevelMappingInfo.getName();
    }


    @Nonnull
    @Override
    public List<String> getPathSegments() {
        return FluentIterable.from(typeLevelMappingInfo.getPathSegments())
                .append(methodLevelMappingInfo.getPathSegments())
                .toList();
    }


    @Nonnull
    @Override
    public Set<String> getRequestMethods() {
        return methodLevelMappingInfo.getRequestMethods();
    }


    @Nonnull
    @Override
    public List<MediaType> getConsumedMediaTypes() {
        List<MediaType> methodLevelMediaTypes = methodLevelMappingInfo.getConsumedMediaTypes();
        if (!methodLevelMediaTypes.isEmpty()) {
            return methodLevelMediaTypes;
        } else {
            return typeLevelMappingInfo.getConsumedMediaTypes();
        }
    }


    @Nonnull
    @Override
    public List<MediaType> getProducedMediaTypes() {
        List<MediaType> methodLevelMediaTypes = methodLevelMappingInfo.getProducedMediaTypes();
        if (!methodLevelMediaTypes.isEmpty()) {
            return methodLevelMediaTypes;
        } else {
            return typeLevelMappingInfo.getProducedMediaTypes();
        }
    }


    @Override
    public String toString() {
        return "path=/" + Joiner.on('/').join(getPathSegments()) + ", org.unbrokendome.siren.ap.codegeneration.method=" + getRequestMethods();
    }
}
