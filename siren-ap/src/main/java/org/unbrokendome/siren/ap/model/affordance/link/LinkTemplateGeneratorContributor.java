package org.unbrokendome.siren.ap.model.affordance.link;

import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;


public interface LinkTemplateGeneratorContributor {

    void contribute(
            RequestHandlerMethodInfo handlerMethod,
            LinkTemplateBuilder link);
}
