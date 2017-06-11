package org.unbrokendome.siren.ap.codegeneration.type.contributors;

import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodCodeGenerator;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.codegeneration.type.TypeSpecContributor;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;

import java.lang.reflect.Type;
import java.util.Set;


public abstract class AbstractTypeSpecMethodContributor implements TypeSpecContributor {

    private final Set<? extends BuilderMethodContributor> methodContributors;


    protected AbstractTypeSpecMethodContributor(Set<? extends BuilderMethodContributor> methodContributors) {
        this.methodContributors = methodContributors;
    }


    @Override
    public final void contribute(AffordanceGroup affordanceGroup, TypeSpec.Builder typeSpec) {
        for (AffordanceTemplate affordanceTemplate : affordanceGroup.getAffordanceTemplates()) {

            String methodName = getMethodNameForAffordance(affordanceTemplate);
            Type specType = getSpecTypeForAffordance(affordanceTemplate);

            MethodSpec methodSpec =
                    new BuilderMethodCodeGenerator<>(affordanceTemplate, methodName, specType, methodContributors)
                            .generate();

            typeSpec.addMethod(methodSpec);
        }
    }


    protected String getMethodNameForAffordance(AffordanceTemplate affordance) {
        return affordance.getName();
    }


    protected abstract Type getSpecTypeForAffordance(AffordanceTemplate affordance);

}
