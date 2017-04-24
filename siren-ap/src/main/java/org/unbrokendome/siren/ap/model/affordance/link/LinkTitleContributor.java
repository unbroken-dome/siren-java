package org.unbrokendome.siren.ap.model.affordance.link;

import com.google.auto.service.AutoService;
import org.unbrokendome.siren.annotation.LinkTitle;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.lang.model.element.ExecutableElement;


@AutoService(LinkTemplateGeneratorContributor.class)
public class LinkTitleContributor implements LinkTemplateGeneratorContributor {

    @Override
    public void contribute(RequestHandlerMethodInfo handlerMethod, LinkTemplateBuilder link) {

        ExecutableElement method = handlerMethod.getHandlerMethod();

        for (LinkTitle titleAnnotation : method.getAnnotationsByType(LinkTitle.class)) {
            if (!titleAnnotation.rel().isEmpty()) {
                link.addTitle(titleAnnotation.rel(), titleAnnotation.value());
            } else {
                link.setTitle(titleAnnotation.value());
            }
        }
    }
}
