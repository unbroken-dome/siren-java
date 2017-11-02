package org.unbrokendome.siren.ap.codegeneration.type.contributors;


import com.google.auto.service.AutoService;
import org.unbrokendome.siren.ap.codegeneration.CodeGenerationContext;
import org.unbrokendome.siren.ap.codegeneration.feature.Feature;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.codegeneration.method.contributors.BuilderMethodMode;
import org.unbrokendome.siren.ap.codegeneration.type.TypeSpecContributor;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroupKind;
import org.unbrokendome.siren.model.LinkSpec;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.Set;


@AutoService(TypeSpecContributor.class)
public class ExplicitRequestLinkMethodContributor extends AbstractTypeSpecMethodContributor {

    @Inject
    public ExplicitRequestLinkMethodContributor(Set<BuilderMethodContributor> methodContributors) {
        super(methodContributors, BuilderMethodMode.EXPLICIT_REQUEST);
    }


    @Override
    protected Type getSpecTypeForAffordance(AffordanceTemplate affordance) {
        return LinkSpec.class;
    }


    @Override
    public boolean appliesTo(AffordanceGroup affordanceGroup, CodeGenerationContext context) {
        return affordanceGroup.getKind() == AffordanceGroupKind.LINK &&
                context.isEnabled(Feature.EXPLICIT_REQUEST);
    }
}
