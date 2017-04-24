package org.unbrokendome.siren.ap.model.affordance.action;

import com.google.auto.service.AutoService;
import org.unbrokendome.siren.annotation.Title;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;


@AutoService(ActionTemplateGeneratorContributor.class)
public class ActionTitleContributor implements ActionTemplateGeneratorContributor {

    @Override
    public void contribute(RequestHandlerMethodInfo handlerMethod, ActionTemplateBuilder action) {
        Title annotation = handlerMethod.getHandlerMethod().getAnnotation(Title.class);
        if (annotation != null) {
            action.setTitle(annotation.value());
        }
    }
}
