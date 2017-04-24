package org.unbrokendome.siren.ap.model.affordance;

import org.unbrokendome.siren.ap.model.controller.ControllerModel;


public interface AffordanceModelGenerator {

    AffordanceModel generate(ControllerModel controllerModel);
}
