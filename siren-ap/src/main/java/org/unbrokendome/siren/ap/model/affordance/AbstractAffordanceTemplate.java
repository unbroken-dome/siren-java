package org.unbrokendome.siren.ap.model.affordance;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Map;


public abstract class AbstractAffordanceTemplate implements AffordanceTemplate {

    private final String name;
    private final RequestHandlerMethodInfo sourceMethodInfo;
    private final List<String> pathSegments;
    private final Map<String, VariableElement> pathParameters;
    private final MediaType type;
    private final Iterable<String> classNames;
    private final AffordanceTitle title;


    protected AbstractAffordanceTemplate(String name,
                                         RequestHandlerMethodInfo sourceMethodInfo,
                                         Iterable<String> pathSegments,
                                         Map<String, VariableElement> pathParameters,
                                         @Nullable MediaType type,
                                         Iterable<String> classNames,
                                         @Nullable AffordanceTitle title) {
        this.name = name;
        this.sourceMethodInfo = sourceMethodInfo;
        this.pathSegments = ImmutableList.copyOf(pathSegments);
        this.pathParameters = ImmutableMap.copyOf(pathParameters);
        this.type = type;
        this.classNames = ImmutableList.copyOf(classNames);
        this.title = title;
    }


    @Nonnull
    @Override
    public String getName() {
        return name;
    }


    @Nonnull
    @Override
    public RequestHandlerMethodInfo getSourceMethodInfo() {
        return sourceMethodInfo;
    }


    @Nonnull
    @Override
    public List<String> getPathSegments() {
        return pathSegments;
    }


    @Nonnull
    @Override
    public Map<String, VariableElement> getPathParameters() {
        return pathParameters;
    }


    @Nullable
    @Override
    public MediaType getType() {
        return type;
    }


    @Nonnull
    @Override
    public Iterable<String> getClassNames() {
        return classNames;
    }


    @Nullable
    @Override
    public AffordanceTitle getTitle() {
        return title;
    }
}
