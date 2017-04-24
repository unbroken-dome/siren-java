package org.unbrokendome.siren.ap.model.affordance;

import org.unbrokendome.siren.ap.model.affordance.action.ActionTemplateGeneratorContributor;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroupingStrategy;
import org.unbrokendome.siren.ap.model.affordance.grouping.DefaultAffordanceGroupingStrategy;
import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.unbrokendome.siren.ap.model.affordance.link.LinkTemplateGeneratorContributor;
import org.unbrokendome.siren.ap.ExtensionBinder;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroupNamingStrategy;
import org.unbrokendome.siren.ap.model.affordance.grouping.DefaultAffordanceGroupNamingStrategy;


@AutoService(Module.class)
public class AffordancesModule extends AbstractModule {

    @Override
    protected void configure() {
        ExtensionBinder.registerExtensionPoints(binder(),
                AffordanceTemplateGenerator.class,
                ActionTemplateGeneratorContributor.class,
                LinkTemplateGeneratorContributor.class);

        bind(AffordanceModelGenerator.class)
                .to(DefaultAffordanceModelGenerator.class);
        bind(AffordanceGroupingStrategy.class)
                .to(DefaultAffordanceGroupingStrategy.class);
        bind(AffordanceGroupNamingStrategy.class)
                .to(DefaultAffordanceGroupNamingStrategy.class);
    }
}
