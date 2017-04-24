package org.unbrokendome.siren.spring.ap.codegeneration.uri;

import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGenerator;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGeneratorFactory;


public class SpringUriBuilderCodeGeneratorFactory implements UriBuilderCodeGeneratorFactory {

    @Override
    public UriBuilderCodeGenerator fromCurrentRequest() {
        return SpringUriBuilderCodeGenerator.fromCurrentRequest();
    }
}
