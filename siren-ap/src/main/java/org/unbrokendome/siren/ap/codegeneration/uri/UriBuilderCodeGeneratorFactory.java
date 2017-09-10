package org.unbrokendome.siren.ap.codegeneration.uri;


public interface UriBuilderCodeGeneratorFactory {

    UriBuilderCodeGenerator fromCurrentRequest();

    UriBuilderCodeGenerator fromRequestVariable(String requestVariable);
}
