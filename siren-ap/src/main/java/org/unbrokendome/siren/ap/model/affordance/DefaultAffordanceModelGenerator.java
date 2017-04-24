package org.unbrokendome.siren.ap.model.affordance;

import com.google.inject.Inject;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroupingStrategy;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;
import org.unbrokendome.siren.ap.model.controller.ControllerModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DefaultAffordanceModelGenerator implements AffordanceModelGenerator {

    private final Set<AffordanceTemplateGenerator> affordanceTemplateGenerators;
    private final AffordanceGroupingStrategy groupingStrategy;


    @Inject
    public DefaultAffordanceModelGenerator(Set<AffordanceTemplateGenerator> affordanceTemplateGenerators,
                                           AffordanceGroupingStrategy groupingStrategy) {
        this.affordanceTemplateGenerators = affordanceTemplateGenerators;
        this.groupingStrategy = groupingStrategy;
    }


    @Override
    public AffordanceModel generate(ControllerModel controllerModel) {

        Stream<AffordanceTemplate> affordanceTemplates = affordanceTemplateGenerators.stream()
                .flatMap(generator -> generator.generate(controllerModel.getControllerInfos().stream()));

        List<AffordanceGroup> groups = groupingStrategy.getGroups(affordanceTemplates)
                .collect(Collectors.toList());

        return new DefaultAffordanceModel(groups);
    }
}
