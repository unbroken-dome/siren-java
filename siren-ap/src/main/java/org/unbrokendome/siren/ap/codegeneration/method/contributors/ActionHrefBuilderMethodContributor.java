package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGeneratorFactory;
import org.unbrokendome.siren.ap.model.affordance.action.ActionTemplate;
import com.google.auto.service.AutoService;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;

import javax.inject.Inject;


@AutoService(BuilderMethodContributor.class)
public class ActionHrefBuilderMethodContributor extends AbstractHrefBuilderMethodContributor<ActionTemplate> {

    @Inject
    public ActionHrefBuilderMethodContributor(UriBuilderCodeGeneratorFactory uriBuilderCodeGeneratorFactory) {
        super(uriBuilderCodeGeneratorFactory);
    }


    @Override
    public Class<ActionTemplate> getTargetTemplateType() {
        return ActionTemplate.class;
    }
}
