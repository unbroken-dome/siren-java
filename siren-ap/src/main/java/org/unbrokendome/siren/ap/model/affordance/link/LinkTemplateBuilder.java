package org.unbrokendome.siren.ap.model.affordance.link;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplateBuilder;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;

import javax.lang.model.element.VariableElement;
import java.util.Map;


public interface LinkTemplateBuilder extends AffordanceTemplateBuilder<LinkTemplate, LinkTemplateBuilder> {

    LinkTemplateBuilder setQueryParameters(Map<String, VariableElement> queryParameters);

    LinkTemplateBuilder addQueryParameter(String queryParameterName, VariableElement methodParameterName);

    LinkTemplateBuilder setTitleMap(Map<String, AffordanceTitle> titleMap);

    LinkTemplateBuilder addTitle(String rel, AffordanceTitle title);

    default LinkTemplateBuilder addTitle(String rel, String title) {
        return addTitle(rel, AffordanceTitle.create(title));
    }
}
