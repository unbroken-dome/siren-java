package org.unbrokendome.siren.ap.model.affordance.link;

import com.google.auto.service.AutoService;
import com.google.common.net.MediaType;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import java.util.List;


@AutoService(LinkTemplateGeneratorContributor.class)
public class ProducedContentTypeContributor implements LinkTemplateGeneratorContributor {

    @Override
    public void contribute(RequestHandlerMethodInfo handlerMethod, LinkTemplateBuilder link) {
        List<MediaType> producedMediaTypes = handlerMethod.getProducedMediaTypes();
        if (!producedMediaTypes.isEmpty()) {
            link.setType(producedMediaTypes.get(0));
        }
    }
}
