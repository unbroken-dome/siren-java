package org.unbrokendome.siren.ap.codegeneration.type.contributors;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.unbrokendome.siren.ap.codegeneration.CodeGenerationContext;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodCodeGenerator;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.codegeneration.method.contributors.BuilderMethodMode;
import org.unbrokendome.siren.ap.codegeneration.type.TypeSpecContributor;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;

import java.lang.reflect.Type;
import java.util.Set;


public abstract class AbstractTypeSpecMethodContributor implements TypeSpecContributor {

    private final Set<? extends BuilderMethodContributor> methodContributors;
    private final BuilderMethodMode mode;


    protected AbstractTypeSpecMethodContributor(Set<? extends BuilderMethodContributor> methodContributors,
                                                BuilderMethodMode mode) {
        this.methodContributors = methodContributors;
        this.mode = mode;
    }


    @Override
    public final void contribute(AffordanceGroup affordanceGroup, CodeGenerationContext context,
                                 TypeSpec.Builder typeSpec) {

        for (AffordanceTemplate affordanceTemplate : affordanceGroup.getAffordanceTemplates()) {

            String methodName = getMethodNameForAffordance(affordanceTemplate);
            Type specType = getSpecTypeForAffordance(affordanceTemplate);

            BuilderMethodCodeGenerator<AffordanceTemplate> generator = new BuilderMethodCodeGenerator<>(
                    affordanceTemplate, context, mode, methodName, specType, methodContributors);
            MethodSpec methodSpec = generator.generate();

            typeSpec.addMethod(methodSpec);
        }
    }


    protected String getMethodNameForAffordance(AffordanceTemplate affordance) {
        return affordance.getName();
    }


    protected abstract Type getSpecTypeForAffordance(AffordanceTemplate affordance);

}
