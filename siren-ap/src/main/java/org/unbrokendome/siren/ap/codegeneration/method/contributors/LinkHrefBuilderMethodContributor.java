package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ParameterSpec;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGenerator;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGeneratorFactory;
import org.unbrokendome.siren.ap.model.affordance.link.LinkTemplate;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;

import javax.inject.Inject;
import java.util.stream.Stream;


@AutoService(BuilderMethodContributor.class)
public class LinkHrefBuilderMethodContributor extends AbstractHrefBuilderMethodContributor<LinkTemplate> {

    @Inject
    public LinkHrefBuilderMethodContributor(UriBuilderCodeGeneratorFactory uriBuilderCodeGeneratorFactory) {
        super(uriBuilderCodeGeneratorFactory);
    }


    @Override
    public Class<LinkTemplate> getTargetTemplateType() {
        return LinkTemplate.class;
    }


    @Override
    protected Stream<ParameterSpec> generateAdditionalMethodParameters(LinkTemplate affordanceTemplate) {
        return createMethodParametersForRequestParameters(
                affordanceTemplate.getQueryParameters());
    }


    @Override
    protected void modifyUriBuilder(LinkTemplate affordanceTemplate, UriBuilderCodeGenerator uriBuilderCodeGenerator) {
        affordanceTemplate.getQueryParameters()
                .forEach(uriBuilderCodeGenerator::queryParam);
    }
}
