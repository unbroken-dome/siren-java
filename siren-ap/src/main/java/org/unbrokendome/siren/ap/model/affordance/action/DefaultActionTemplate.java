package org.unbrokendome.siren.ap.model.affordance.action;

import org.unbrokendome.siren.ap.model.affordance.AbstractAffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;
import org.unbrokendome.siren.model.ActionField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Map;


public final class DefaultActionTemplate
        extends AbstractAffordanceTemplate
        implements ActionTemplate {

    private final String method;
    private final List<ActionField> fields;


    protected DefaultActionTemplate(String name,
                                    RequestHandlerMethodInfo sourceMethodInfo, String method,
                                    Iterable<String> pathSegments,
                                    Map<String, VariableElement> pathParameters,
                                    Iterable<ActionField> fields,
                                    @Nullable MediaType type,
                                    Iterable<String> classNames,
                                    @Nullable AffordanceTitle title) {
        super(name, sourceMethodInfo, pathSegments, pathParameters, type, classNames, title);
        this.method = method;
        this.fields = ImmutableList.copyOf(fields);
    }


    @Nonnull
    @Override
    public String getMethod() {
        return method;
    }


    @Nonnull
    @Override
    public List<ActionField> getFields() {
        return fields;
    }
}
