package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import org.unbrokendome.siren.ap.model.affordance.action.ActionTemplate;


public abstract class AbstractActionBuilderMethodContributor
        extends AbstractBuilderMethodContributor<ActionTemplate> {

    @Override
    protected final Class<ActionTemplate> getTargetTemplateType() {
        return ActionTemplate.class;
    }
}
