package org.unbrokendome.siren.ap.model.affordance.link;

import com.google.auto.service.AutoService;
import org.unbrokendome.siren.ap.model.affordance.AbstractTypedAffordanceTemplateGenerator;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplateGenerator;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Stream;


@AutoService(AffordanceTemplateGenerator.class)
public class DefaultLinkTemplateGenerator
        extends AbstractTypedAffordanceTemplateGenerator<LinkTemplate, LinkTemplateBuilder> {

    @Inject
    public DefaultLinkTemplateGenerator(Set<LinkTemplateGeneratorContributor> contributors) {
        super(contributors.stream()
                .map(c -> c::contribute));
    }


    @Override
    protected boolean appliesTo(RequestHandlerMethodInfo handlerMethod) {
        return handlerMethod.handlesGETRequests();
    }


    @Override
    protected Stream<LinkTemplateBuilder> createBuilders(RequestHandlerMethodInfo handlerMethod) {
        return Stream.of(
                LinkTemplate.builder(handlerMethod.getName(), handlerMethod));
    }
}
