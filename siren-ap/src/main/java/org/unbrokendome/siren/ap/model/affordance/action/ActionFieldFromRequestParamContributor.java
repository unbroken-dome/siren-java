package org.unbrokendome.siren.ap.model.affordance.action;


import com.google.auto.service.AutoService;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;
import org.unbrokendome.siren.model.ActionField;


@AutoService(ActionTemplateGeneratorContributor.class)
public class ActionFieldFromRequestParamContributor implements ActionTemplateGeneratorContributor {

    @Override
    public void contribute(RequestHandlerMethodInfo handlerMethod, ActionTemplateBuilder action) {
        handlerMethod.getQueryParameters().forEach((name, parameter) -> {
            ActionField field = ActionFields.fromElement(parameter, name);
            action.addField(field);
        });
    }
}
