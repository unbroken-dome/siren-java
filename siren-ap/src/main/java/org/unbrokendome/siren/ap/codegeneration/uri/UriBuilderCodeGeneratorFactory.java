package org.unbrokendome.siren.ap.codegeneration.uri;


import com.squareup.javapoet.TypeName;

import javax.annotation.Nonnull;


public interface UriBuilderCodeGeneratorFactory {

    TypeName getRequestVariableType();

    default String getRequestVariableName() {
        return "request";
    }

    default boolean supportsFromCurrentRequest() {
        return true;
    }

    @Nonnull
    UriBuilderCodeGenerator fromCurrentRequest();

    @Nonnull
    UriBuilderCodeGenerator fromRequestVariable(String requestVariable);
}
