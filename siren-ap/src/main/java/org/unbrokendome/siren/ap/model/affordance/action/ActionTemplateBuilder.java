package org.unbrokendome.siren.ap.model.affordance.action;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplateBuilder;
import org.unbrokendome.siren.model.ActionField;


public interface ActionTemplateBuilder extends AffordanceTemplateBuilder<ActionTemplate, ActionTemplateBuilder> {

    ActionTemplateBuilder setMethod(String method);

    ActionTemplateBuilder addField(ActionField field);
}
