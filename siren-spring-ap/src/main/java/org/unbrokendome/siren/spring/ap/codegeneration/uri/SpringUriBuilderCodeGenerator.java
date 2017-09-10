package org.unbrokendome.siren.spring.ap.codegeneration.uri;

import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGenerator;
import org.unbrokendome.siren.spring.RequestUtils;


public class SpringUriBuilderCodeGenerator implements UriBuilderCodeGenerator {

    private final CodeBlock.Builder codeBlock;
    private String expandVariablesWith = null;


    private SpringUriBuilderCodeGenerator(CodeBlock.Builder codeBlock) {
        this.codeBlock = codeBlock;
    }


    public static SpringUriBuilderCodeGenerator fromCurrentRequest() {
        CodeBlock.Builder codeBlock = CodeBlock.builder()
                .add("$T.createUriBuilderFromCurrentRequest()\n", RequestUtils.class)
                .indent().indent();
        return new SpringUriBuilderCodeGenerator(codeBlock);
    }


    public static SpringUriBuilderCodeGenerator fromRequestVariable(String requestVariable) {
        CodeBlock.Builder codeBlock = CodeBlock.builder()
                .add("$T.createUriBuilderFromRequest($L)", RequestUtils.class, requestVariable)
                .indent().indent();
        return new SpringUriBuilderCodeGenerator(codeBlock);
    }


    @Override
    public UriBuilderCodeGenerator pathSegment(String pathSegment) {
        codeBlock.add(".pathSegment($S)\n", pathSegment);
        return this;
    }


    @Override
    public UriBuilderCodeGenerator path(String path) {
        codeBlock.add(".path($S)\n", path);
        return this;
    }


    @Override
    public UriBuilderCodeGenerator queryParam(String name, CharSequence variableName) {
        codeBlock.add(".queryParam($S, $L)\n", name, variableName);
        return this;
    }


    @Override
    public UriBuilderCodeGenerator expandVariables(String mapVariableName) {
        expandVariablesWith = mapVariableName;
        return this;
    }


    @Override
    public CodeBlock toCodeBlock() {
        if (expandVariablesWith != null) {
            codeBlock.add(".buildAndExpand($L)\n", expandVariablesWith);
        } else {
            codeBlock.add(".build()\n");
        }

        return codeBlock
                .add(".toUriString()")
                .unindent().unindent()
                .build();
    }
}
