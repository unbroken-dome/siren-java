package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.google.auto.service.AutoService;
import com.google.common.base.Strings;
import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.model.affordance.action.ActionTemplate;
import org.unbrokendome.siren.model.ActionField;

import javax.annotation.Nullable;
import java.util.Collections;


@AutoService(BuilderMethodContributor.class)
public class ActionFieldBuilderMethodContributor extends AbstractActionBuilderMethodContributor {

    @Nullable
    @Override
    protected CodeBlock doGenerateBuilderSetterStatement(ActionTemplate actionTemplate, String builderVariableName) {
        CodeBlock.Builder codeBlock = CodeBlock.builder();

        boolean first = true;
        for (ActionField field : actionTemplate.getFields()) {

            if (first) {
                first = false;
            } else {
                codeBlock.add("\n");
            }

            codeBlock.add(".addField($S, f -> f", field.getName())
                    .indent().indent();

            if (!Strings.isNullOrEmpty(field.getTitle())) {
                codeBlock.add("\n.setTitle($S)", field.getTitle());
            }

            if (field.getType() != null) {
                codeBlock.add("\n.setType($T.$L)", ActionField.Type.class, field.getType().name());
            }

            if (field.getClasses().size() == 1) {
                codeBlock.add("\nsetClassName($S)", field.getClasses().get(0));
            } else if (!field.getClasses().isEmpty()) {
                codeBlock.add("\nsetClassNames("
                        + String.join(", ", Collections.nCopies(field.getClasses().size(), "$S"))
                        + ")",
                        field.getClasses().toArray());
            }

            codeBlock.add(")")
                    .unindent().unindent();
        }

        return codeBlock.build();
    }
}
