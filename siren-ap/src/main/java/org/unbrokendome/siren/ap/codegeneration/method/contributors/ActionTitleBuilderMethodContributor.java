package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.codegeneration.title.ResolvableTitleSupplierGenerator;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;
import org.unbrokendome.siren.ap.model.affordance.action.ActionTemplate;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Optional;


@AutoService(BuilderMethodContributor.class)
public class ActionTitleBuilderMethodContributor extends AbstractActionBuilderMethodContributor {

    private final Optional<ResolvableTitleSupplierGenerator> resolvableTitleSupplierGenerator;


    @Inject
    public ActionTitleBuilderMethodContributor(Optional<ResolvableTitleSupplierGenerator> resolvableTitleSupplierGenerator) {
        this.resolvableTitleSupplierGenerator = resolvableTitleSupplierGenerator;
    }


    @Nullable
    @Override
    protected CodeBlock doGenerateBuilderSetterStatement(ActionTemplate actionTemplate,
                                                         String builderVariableName) {
        AffordanceTitle title = actionTemplate.getTitle();
        if (title != null) {
            return CodeBlock.of(".setTitle($L)", buildTitleExpression(title));

        } else {
            return null;
        }
    }


    private CodeBlock buildTitleExpression(AffordanceTitle title) {
        if (title.isResolvable() && resolvableTitleSupplierGenerator.isPresent()) {
            CodeBlock titleSupplierExpression =
                    resolvableTitleSupplierGenerator.get().buildTitleSupplierExpression(title);
            return CodeBlock.of("$L.get()", titleSupplierExpression);

        } else {
            return CodeBlock.of("$S", title.getValue());
        }
    }
}
