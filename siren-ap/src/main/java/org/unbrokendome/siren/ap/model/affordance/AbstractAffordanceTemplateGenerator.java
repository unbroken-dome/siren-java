package org.unbrokendome.siren.ap.model.affordance;

import org.unbrokendome.siren.ap.model.controller.ControllerInfo;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.annotation.Nonnull;
import java.util.stream.Stream;


public abstract class AbstractAffordanceTemplateGenerator implements AffordanceTemplateGenerator {

    @Override
    @Nonnull
    public Stream<AffordanceTemplate> generate(Stream<ControllerInfo> controllerInfos) {
        return controllerInfos
                .flatMap(this::generate);
    }


    @Nonnull
    public Stream<AffordanceTemplate> generate(ControllerInfo controllerInfo) {
        return controllerInfo.getHandlerMethods().stream()
                .filter(this::appliesTo)
                .flatMap(this::generate);
    }


    protected boolean appliesTo(RequestHandlerMethodInfo handlerMethod) {
        return true;
    }


    @Nonnull
    protected Stream<AffordanceTemplate> generate(RequestHandlerMethodInfo handlerMethod) {
        return Stream.empty();
    }
}
