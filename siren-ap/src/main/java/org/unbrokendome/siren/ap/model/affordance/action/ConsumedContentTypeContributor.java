package org.unbrokendome.siren.ap.model.affordance.action;

import com.google.auto.service.AutoService;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;


@AutoService(ActionTemplateGeneratorContributor.class)
public class ConsumedContentTypeContributor implements ActionTemplateGeneratorContributor {

    @Override
    public void contribute(RequestHandlerMethodInfo handlerMethod, ActionTemplateBuilder action) {

        if (handlerMethod.handlesNonGETRequests() && !handlerMethod.getConsumedMediaTypes().isEmpty()) {
            action.setType(handlerMethod.getConsumedMediaTypes().get(0));
        }
    }
}
