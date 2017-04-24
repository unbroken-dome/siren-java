package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.google.auto.service.AutoService;
import com.google.common.net.MediaType;
import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;

import javax.annotation.Nullable;


@AutoService(BuilderMethodContributor.class)
public class ContentTypeBuilderMethodContributor
        extends AbstractBuilderMethodContributor<AffordanceTemplate> {

    @Override
    public Class<AffordanceTemplate> getTargetTemplateType() {
        return AffordanceTemplate.class;
    }


    @Override
    @Nullable
    public CodeBlock doGenerateBuilderSetterStatement(AffordanceTemplate affordanceTemplate,
                                                      String builderVariableName) {
        MediaType type = affordanceTemplate.getType();
        if (type != null) {
            return CodeBlock.of(".setType($S)", type.toString());
        } else {
            return null;
        }
    }
}
