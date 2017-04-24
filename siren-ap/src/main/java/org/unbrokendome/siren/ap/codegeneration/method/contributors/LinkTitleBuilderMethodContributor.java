package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.codegeneration.title.TitleCodeGenerationUtils;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.model.affordance.link.LinkTemplate;

import javax.annotation.Nullable;


@AutoService(BuilderMethodContributor.class)
public class LinkTitleBuilderMethodContributor extends AbstractLinkBuilderMethodContributor {

    @Nullable
    @Override
    protected CodeBlock doGenerateBuilderSetterStatement(LinkTemplate linkTemplate, String builderVariableName) {
        CodeBlock titleCode = getTitleCode(linkTemplate, builderVariableName);
        if (titleCode != null) {
            return CodeBlock.of(".setTitle($L)", titleCode);
        } else {
            return null;
        }
    }


    @Nullable
    private CodeBlock getTitleCode(LinkTemplate linkTemplate, String builderVariableName) {
        if (linkTemplate.hasTitleMap() || linkTemplate.hasResolvableTitles()) {
            String titleMapFieldName = TitleCodeGenerationUtils.getTitlesFieldName(linkTemplate);
            return CodeBlock.of("$L.get($L.getRels())", titleMapFieldName, builderVariableName);

        } else if (linkTemplate.getTitle() != null) {
            return CodeBlock.of("$S", linkTemplate.getTitle());

        } else {
            return null;
        }
    }
}
