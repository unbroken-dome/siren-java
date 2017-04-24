package org.unbrokendome.siren.ap.model.affordance;

import com.google.common.net.MediaType;

import javax.lang.model.element.VariableElement;
import java.util.Map;


public interface AffordanceTemplateBuilder<T extends AffordanceTemplate, B extends AffordanceTemplateBuilder<T, B>> {

    B setPathSegments(Iterable<String> pathSegments);

    B setPathParameters(Map<String, VariableElement> pathParameters);

    B addPathParameter(String pathParameterName, VariableElement methodParameter);

    B setType(MediaType type);

    B setClassNames(Iterable<String> classNames);

    B setTitle(AffordanceTitle title);

    default B setTitle(String title) {
        return setTitle(AffordanceTitle.create(title));
    }

    T build();
}
