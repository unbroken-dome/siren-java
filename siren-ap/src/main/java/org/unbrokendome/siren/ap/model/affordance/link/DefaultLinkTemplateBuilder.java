package org.unbrokendome.siren.ap.model.affordance.link;

import com.google.common.collect.ImmutableMap;
import org.unbrokendome.siren.ap.model.affordance.AbstractAffordanceTemplateBuilder;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.lang.model.element.VariableElement;
import java.util.Map;


public final class DefaultLinkTemplateBuilder
        extends AbstractAffordanceTemplateBuilder<LinkTemplate, LinkTemplateBuilder>
        implements LinkTemplateBuilder {

    private ImmutableMap.Builder<String, VariableElement> queryParameters = ImmutableMap.builder();
    private ImmutableMap.Builder<String, AffordanceTitle> titleMap = ImmutableMap.builder();


    protected DefaultLinkTemplateBuilder(String name, RequestHandlerMethodInfo sourceMethodInfo) {
        super(name, sourceMethodInfo);
    }


    @Override
    public LinkTemplateBuilder setQueryParameters(Map<String, VariableElement> queryParameters) {
        this.queryParameters = ImmutableMap.builder();
        this.queryParameters.putAll(queryParameters);
        return this;
    }


    @Override
    public LinkTemplateBuilder addQueryParameter(String queryParameterName, VariableElement methodParameter) {
        this.queryParameters.put(queryParameterName, methodParameter);
        return this;
    }


    @Override
    public LinkTemplateBuilder setTitleMap(Map<String, AffordanceTitle> titleMap) {
        this.titleMap = ImmutableMap.builder();
        this.titleMap.putAll(titleMap);
        return this;
    }


    @Override
    public LinkTemplateBuilder addTitle(String rel, AffordanceTitle title) {
        titleMap.put(rel, title);
        return this;
    }


    @Override
    public LinkTemplate build() {
        return new DefaultLinkTemplate(
                getName(),
                getSourceMethodInfo(),
                getPathSegments(),
                getPathParameters(),
                queryParameters.build(),
                getType(),
                getClassNames(),
                titleMap.build(),
                getTitle());
    }
}
