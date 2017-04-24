package org.unbrokendome.siren.ap.model.affordance.action;

import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;


public interface ActionTemplateGeneratorContributor {

    void contribute(
            RequestHandlerMethodInfo handlerMethod,
            ActionTemplateBuilder action);
}
