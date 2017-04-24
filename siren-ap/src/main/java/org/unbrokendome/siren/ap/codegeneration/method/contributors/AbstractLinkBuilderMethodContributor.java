package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import org.unbrokendome.siren.ap.model.affordance.link.LinkTemplate;


public abstract class AbstractLinkBuilderMethodContributor
        extends AbstractBuilderMethodContributor<LinkTemplate> {

    @Override
    protected final Class<LinkTemplate> getTargetTemplateType() {
        return LinkTemplate.class;
    }
}
