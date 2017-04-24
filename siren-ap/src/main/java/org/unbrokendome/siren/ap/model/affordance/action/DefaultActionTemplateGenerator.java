package org.unbrokendome.siren.ap.model.affordance.action;

import com.google.auto.service.AutoService;
import org.unbrokendome.siren.ap.model.affordance.AbstractTypedAffordanceTemplateGenerator;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplateGenerator;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;


@AutoService(AffordanceTemplateGenerator.class)
public class DefaultActionTemplateGenerator
        extends AbstractTypedAffordanceTemplateGenerator<ActionTemplate, ActionTemplateBuilder> {

    @Inject
    public DefaultActionTemplateGenerator(Set<ActionTemplateGeneratorContributor> contributors) {
        super(contributors.stream()
                .map(c -> c::contribute));
    }


    @Override
    protected boolean appliesTo(RequestHandlerMethodInfo handlerMethod) {
        return handlerMethod.handlesNonGETRequests() ||
                (handlerMethod.handlesGETRequests() && !handlerMethod.getQueryParameters().isEmpty());
    }


    @Override
    protected Stream<ActionTemplateBuilder> createBuilders(RequestHandlerMethodInfo handlerMethod) {
        Set<String> requestMethods = new HashSet<>(handlerMethod.getRequestMethods());
        if (handlerMethod.getQueryParameters().isEmpty()) {
            requestMethods.remove("GET");
        }

        Function<String, String> namingFunction;
        if (requestMethods.size() == 1) {
            namingFunction = (requestMethod) -> handlerMethod.getName();
        } else {
            namingFunction = (requestMethod) -> handlerMethod.getName() + requestMethod;
        }

        return requestMethods.stream()
                .map(requestMethod -> {
                    String name = namingFunction.apply(requestMethod);
                    return ActionTemplate.builder(name, handlerMethod)
                            .setMethod(requestMethod);
                });
    }
}
