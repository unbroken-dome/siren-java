package org.unbrokendome.siren.spring.ap.codegeneration.uri;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGenerator;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGeneratorFactory;

import javax.annotation.Nonnull;


public class WebMvcUriBuilderCodeGeneratorFactory implements UriBuilderCodeGeneratorFactory {

    @Override
    public TypeName getRequestVariableType() {
        return ClassName.get("javax.servlet.http", "HttpServletRequest");
    }


    @Nonnull
    @Override
    public UriBuilderCodeGenerator fromCurrentRequest() {
        return WebMvcUriBuilderCodeGenerator.fromCurrentRequest();
    }


    @Nonnull
    @Override
    public UriBuilderCodeGenerator fromRequestVariable(String requestVariable) {
        return WebMvcUriBuilderCodeGenerator.fromRequestVariable(requestVariable);
    }
}
