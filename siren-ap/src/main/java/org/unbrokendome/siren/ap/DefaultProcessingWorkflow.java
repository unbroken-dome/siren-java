package org.unbrokendome.siren.ap;

import org.unbrokendome.siren.ap.codegeneration.CodeGenerator;
import org.unbrokendome.siren.ap.model.affordance.AffordanceModel;
import org.unbrokendome.siren.ap.model.affordance.AffordanceModelGenerator;
import org.unbrokendome.siren.ap.model.controller.ControllerModel;
import org.unbrokendome.siren.ap.model.controller.ControllerModelGenerator;

import javax.inject.Inject;
import javax.lang.model.element.TypeElement;
import java.util.Set;


public class DefaultProcessingWorkflow implements ProcessingWorkflow {

    private final ControllerModelGenerator controllerModelGenerator;
    private final AffordanceModelGenerator affordanceModelGenerator;
    private final Set<CodeGenerator> codeGenerators;


    @Inject
    public DefaultProcessingWorkflow(ControllerModelGenerator controllerModelGenerator,
                                     AffordanceModelGenerator affordanceModelGenerator,
                                     Set<CodeGenerator> codeGenerators) {
        this.controllerModelGenerator = controllerModelGenerator;
        this.affordanceModelGenerator = affordanceModelGenerator;
        this.codeGenerators = codeGenerators;
    }


    @Override
    public void process(Set<? extends TypeElement> annotations) {

        ControllerModel controllerModel = controllerModelGenerator.generate(annotations);

        AffordanceModel affordanceModel = affordanceModelGenerator.generate(controllerModel);

        codeGenerators.forEach(codeGenerator -> codeGenerator.generateCode(affordanceModel));
    }
}
