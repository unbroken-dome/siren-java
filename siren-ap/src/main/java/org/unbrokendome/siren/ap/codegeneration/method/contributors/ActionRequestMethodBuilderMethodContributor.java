package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.model.affordance.action.ActionTemplate;

import javax.annotation.Nullable;


@AutoService(BuilderMethodContributor.class)
public class ActionRequestMethodBuilderMethodContributor extends AbstractActionBuilderMethodContributor {

    @Nullable
    @Override
    protected CodeBlock doGenerateBuilderSetterStatement(ActionTemplate actionTemplate,
                                                         String builderVariableName) {
        return CodeBlock.of(".setMethod($S)", actionTemplate.getMethod());
    }
}
