package org.unbrokendome.siren.ap.model.affordance.link;

import org.unbrokendome.siren.ap.model.affordance.AbstractAffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.VariableElement;
import java.util.Map;


public final class DefaultLinkTemplate
        extends AbstractAffordanceTemplate
        implements LinkTemplate {

    private final Map<String, VariableElement> queryParameters;
    private final Map<String, AffordanceTitle> titleMap;


    public DefaultLinkTemplate(String name,
                               RequestHandlerMethodInfo sourceMethodInfo,
                               Iterable<String> pathSegments,
                               Map<String, VariableElement> pathParameters,
                               Map<String, VariableElement> queryParameters,
                               @Nullable MediaType type,
                               Iterable<String> classNames,
                               Map<String, AffordanceTitle> titleMap,
                               @Nullable AffordanceTitle title) {
        super(name, sourceMethodInfo, pathSegments, pathParameters, type, classNames, title);
        this.queryParameters = ImmutableMap.copyOf(queryParameters);
        this.titleMap = ImmutableMap.copyOf(titleMap);
    }


    @Nonnull
    @Override
    public Map<String, VariableElement> getQueryParameters() {
        return queryParameters;
    }


    @Nonnull
    @Override
    public Map<String, AffordanceTitle> getTitleMap() {
        return titleMap;
    }
}
