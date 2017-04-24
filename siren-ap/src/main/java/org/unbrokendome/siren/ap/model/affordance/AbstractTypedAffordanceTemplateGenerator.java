package org.unbrokendome.siren.ap.model.affordance;

import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public abstract class AbstractTypedAffordanceTemplateGenerator<T extends AffordanceTemplate, B extends AffordanceTemplateBuilder<T, B>>
        extends AbstractAffordanceTemplateGenerator {

    private final List<BiConsumer<RequestHandlerMethodInfo, B>> contributors;


    protected AbstractTypedAffordanceTemplateGenerator(List<BiConsumer<RequestHandlerMethodInfo, B>> contributors) {
        this.contributors = contributors;
    }


    protected AbstractTypedAffordanceTemplateGenerator(Stream<BiConsumer<RequestHandlerMethodInfo, B>> contributors) {
        this(contributors.collect(toList()));
    }


    @Nonnull
    @Override
    protected final Stream<AffordanceTemplate> generate(RequestHandlerMethodInfo handlerMethod) {
        return createBuilders(handlerMethod)
                .peek(builder -> {
                    builder.setPathSegments(handlerMethod.getPathSegments());
                    handlerMethod.getPathParameters()
                            .forEach(builder::addPathParameter);
                    applyContributors(handlerMethod, builder);
                })
                .map(AffordanceTemplateBuilder::build);
    }


    protected abstract Stream<B> createBuilders(RequestHandlerMethodInfo handlerMethod);


    private void applyContributors(RequestHandlerMethodInfo handlerMethod, B builder) {
        contributors.forEach(c -> c.accept(handlerMethod, builder));
    }
}
