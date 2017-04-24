package org.unbrokendome.siren.ap.model.affordance;

import org.unbrokendome.siren.ap.model.controller.ControllerInfo;

import javax.annotation.Nonnull;
import java.util.stream.Stream;


public interface AffordanceTemplateGenerator {

    @Nonnull
    Stream<AffordanceTemplate> generate(Stream<ControllerInfo> controllerInfos);
}
