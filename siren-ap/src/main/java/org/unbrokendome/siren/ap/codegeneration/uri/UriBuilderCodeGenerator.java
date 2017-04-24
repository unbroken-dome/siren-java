package org.unbrokendome.siren.ap.codegeneration.uri;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.VariableElement;


public interface UriBuilderCodeGenerator {

    UriBuilderCodeGenerator pathSegment(String pathSegment);

    UriBuilderCodeGenerator path(String path);

    UriBuilderCodeGenerator queryParam(String name, CharSequence variableName);

    default UriBuilderCodeGenerator queryParam(String name, VariableElement variable) {
        return this.queryParam(name, variable.getSimpleName());
    }

    UriBuilderCodeGenerator expandVariables(String mapVariableName);

    CodeBlock toCodeBlock();
}
