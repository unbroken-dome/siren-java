package org.unbrokendome.siren.ap.codegeneration.type.contributors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import org.unbrokendome.siren.ap.codegeneration.title.ResolvableTitleSupplierGenerator;
import org.unbrokendome.siren.ap.codegeneration.title.TitleCodeGenerationUtils;
import org.unbrokendome.siren.ap.codegeneration.type.TypeSpecContributor;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroupKind;
import org.unbrokendome.siren.ap.model.affordance.link.LinkTemplate;
import org.unbrokendome.siren.titles.LinkTitleLookup;

import javax.inject.Inject;
import javax.lang.model.element.Modifier;
import java.util.Map;
import java.util.Optional;


@AutoService(TypeSpecContributor.class)
public class LinkTitleLookupFieldContributor implements TypeSpecContributor {

    private final Optional<ResolvableTitleSupplierGenerator> resolvableTitleSupplierGenerator;


    @Inject
    public LinkTitleLookupFieldContributor(Optional<ResolvableTitleSupplierGenerator> resolvableTitleSupplierGenerator) {
        this.resolvableTitleSupplierGenerator = resolvableTitleSupplierGenerator;
    }


    @Override
    public void contribute(AffordanceGroup affordanceGroup, TypeSpec.Builder typeSpec) {
        for (AffordanceTemplate affordanceTemplate : affordanceGroup.getAffordanceTemplates()) {
            LinkTemplate linkTemplate = (LinkTemplate) affordanceTemplate;
            if (linkTemplate.hasTitleMap() || linkTemplate.hasResolvableTitles()) {
                FieldSpec titleLookupField = buildTitleLookupField((LinkTemplate) affordanceTemplate);
                typeSpec.addField(titleLookupField);
            }
        }
    }


    private FieldSpec buildTitleLookupField(LinkTemplate linkTemplate) {
        return FieldSpec.builder(LinkTitleLookup.class,
                TitleCodeGenerationUtils.getTitlesFieldName(linkTemplate),
                Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer(buildTitleLookupInitializer(linkTemplate))
                .build();
    }


    private CodeBlock buildTitleLookupInitializer(LinkTemplate linkTemplate) {
        return buildTitleLookupInitializer(linkTemplate.getTitle(), linkTemplate.getTitleMap());
    }


    private CodeBlock buildTitleLookupInitializer(AffordanceTitle defaultTitle, Map<String, AffordanceTitle> titleMap) {
        CodeBlock.Builder expression = CodeBlock.builder()
                .add("$T.builder()\n", LinkTitleLookup.class)
                .indent().indent();

        titleMap.forEach((key, title) -> {
            if (title.isResolvable() && resolvableTitleSupplierGenerator.isPresent()) {
                CodeBlock titleSupplierExpression =
                        resolvableTitleSupplierGenerator.get().buildTitleSupplierExpression(title);
                expression.add(".addTitleSupplier($S, $L)\n", key, titleSupplierExpression);
            } else {
                expression.add(".addTitle($S, $S)\n", key, title);
            }
        });

        if (defaultTitle != null) {
            if (defaultTitle.isResolvable() && resolvableTitleSupplierGenerator.isPresent()) {
                CodeBlock titleSupplierExpression =
                        resolvableTitleSupplierGenerator.get().buildTitleSupplierExpression(defaultTitle);
                expression.add(".setDefaultTitleSupplier($L)\n", titleSupplierExpression);
            } else {
                expression.add(".setDefaultTitle($S)\n", defaultTitle);
            }
        }

        return expression.add(".build()")
                .unindent().unindent()
                .build();
    }


    @Override
    public boolean appliesTo(AffordanceGroup affordanceGroup) {
        return affordanceGroup.getKind() == AffordanceGroupKind.LINK;
    }
}
