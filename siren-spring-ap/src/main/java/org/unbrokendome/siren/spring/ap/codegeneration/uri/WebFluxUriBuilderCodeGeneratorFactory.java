package org.unbrokendome.siren.spring.ap.codegeneration.uri;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGenerator;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGeneratorFactory;

import javax.annotation.Nonnull;


public class WebFluxUriBuilderCodeGeneratorFactory implements UriBuilderCodeGeneratorFactory {

    @Override
    public TypeName getRequestVariableType() {
        return ClassName.get("org.springframework.web.reactive.function.server", "ServerRequest");
    }


    @Override
    public boolean supportsFromCurrentRequest() {
        return false;
    }


    @Nonnull
    @Override
    public UriBuilderCodeGenerator fromCurrentRequest() {
        throw new UnsupportedOperationException();
    }


    @Nonnull
    @Override
    public UriBuilderCodeGenerator fromRequestVariable(String requestVariable) {
        return WebFluxUriBuilderCodeGenerator.fromRequestVariable(requestVariable);
    }
}
