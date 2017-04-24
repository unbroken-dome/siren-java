package org.unbrokendome.siren.ap.model.affordance.action;

import com.google.common.collect.ImmutableList;
import org.unbrokendome.siren.model.ActionField;
import org.unbrokendome.siren.ap.model.affordance.AbstractAffordanceTemplateBuilder;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;


public final class DefaultActionTemplateBuilder
        extends AbstractAffordanceTemplateBuilder<ActionTemplate, ActionTemplateBuilder>
        implements ActionTemplateBuilder {

    private String method;
    private final ImmutableList.Builder<ActionField> fields = ImmutableList.builder();


    protected DefaultActionTemplateBuilder(String name, RequestHandlerMethodInfo sourceMethodInfo) {
        super(name, sourceMethodInfo);
    }


    @Override
    public ActionTemplateBuilder setMethod(String method) {
        this.method = method;
        return this;
    }


    @Override
    public ActionTemplateBuilder addField(ActionField field) {
        fields.add(field);
        return this;
    }


    @Override
    public ActionTemplate build() {
        return new DefaultActionTemplate(
                getName(),
                getSourceMethodInfo(),
                method,
                getPathSegments(),
                getPathParameters(),
                fields.build(),
                getType(),
                getClassNames(),
                getTitle());
    }
}
