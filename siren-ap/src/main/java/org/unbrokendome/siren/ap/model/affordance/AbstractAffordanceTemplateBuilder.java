package org.unbrokendome.siren.ap.model.affordance;

import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.annotation.Nullable;
import javax.lang.model.element.VariableElement;
import java.util.Collections;
import java.util.Map;


public abstract class AbstractAffordanceTemplateBuilder<T extends AffordanceTemplate, B extends AffordanceTemplateBuilder<T, B>>
        implements AffordanceTemplateBuilder<T, B> {

    private final String name;
    private final RequestHandlerMethodInfo sourceMethodInfo;
    private Iterable<String> pathSegments;
    private ImmutableMap.Builder<String, VariableElement> pathParameters = ImmutableMap.builder();
    private MediaType type;
    private Iterable<String> classNames = Collections.emptyList();
    private AffordanceTitle title;


    protected AbstractAffordanceTemplateBuilder(String name, RequestHandlerMethodInfo sourceMethodInfo) {
        this.name = name;
        this.sourceMethodInfo = sourceMethodInfo;
    }


    protected final String getName() {
        return name;
    }


    protected final RequestHandlerMethodInfo getSourceMethodInfo() {
        return sourceMethodInfo;
    }


    protected final Iterable<String> getPathSegments() {
        return pathSegments;
    }


    @Override
    public final B setPathSegments(Iterable<String> pathSegments) {
        this.pathSegments = pathSegments;
        return self();
    }


    protected final Map<String, VariableElement> getPathParameters() {
        return pathParameters.build();
    }


    @Override
    public final B setPathParameters(Map<String, VariableElement> pathParameters) {
        this.pathParameters = ImmutableMap.builder();
        this.pathParameters.putAll(pathParameters);
        return self();
    }


    @Override
    public B addPathParameter(String pathParameterName, VariableElement methodParameter) {
        this.pathParameters.put(pathParameterName, methodParameter);
        return self();
    }


    @Nullable
    protected final MediaType getType() {
        return type;
    }


    @Override
    public B setType(MediaType type) {
        this.type = type;
        return self();
    }


    protected final Iterable<String> getClassNames() {
        return classNames;
    }


    @Override
    public final B setClassNames(Iterable<String> classNames) {
        this.classNames = classNames;
        return self();
    }


    protected final AffordanceTitle getTitle() {
        return title;
    }


    @Override
    public B setTitle(AffordanceTitle title) {
        this.title = title;
        return self();
    }

    @SuppressWarnings("unchecked")
    private B self() {
        return (B) this;
    }
}
