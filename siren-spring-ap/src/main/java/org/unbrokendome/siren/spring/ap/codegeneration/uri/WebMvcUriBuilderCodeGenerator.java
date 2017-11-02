package org.unbrokendome.siren.spring.ap.codegeneration.uri;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGenerator;


public class WebMvcUriBuilderCodeGenerator implements UriBuilderCodeGenerator {

    private static final ClassName SERVLET_URI_COMPONENTS_BUILDER =
            ClassName.get("org.springframework.web.servlet.support", "ServletUriComponentsBuilder");

    private final CodeBlock.Builder codeBlock;
    private String expandVariablesWith = null;


    private WebMvcUriBuilderCodeGenerator(CodeBlock.Builder codeBlock) {
        this.codeBlock = codeBlock;
    }


    public static WebMvcUriBuilderCodeGenerator fromCurrentRequest() {
        CodeBlock.Builder codeBlock = CodeBlock.builder()
                .add("$T.fromCurrentServletMapping()\n", SERVLET_URI_COMPONENTS_BUILDER)
                .indent().indent();
        return new WebMvcUriBuilderCodeGenerator(codeBlock);
    }


    public static WebMvcUriBuilderCodeGenerator fromRequestVariable(String requestVariable) {
        CodeBlock.Builder codeBlock = CodeBlock.builder()
                .add("$T.fromServletMapping($L)", SERVLET_URI_COMPONENTS_BUILDER, requestVariable)
                .indent().indent();
        return new WebMvcUriBuilderCodeGenerator(codeBlock);
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
