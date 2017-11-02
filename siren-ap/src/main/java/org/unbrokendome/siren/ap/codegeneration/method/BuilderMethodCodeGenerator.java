package org.unbrokendome.siren.ap.codegeneration.method;

import com.google.common.collect.Ordering;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import org.unbrokendome.siren.ap.codegeneration.CodeGenerationContext;
import org.unbrokendome.siren.ap.codegeneration.method.contributors.BuilderMethodMode;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;

import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class BuilderMethodCodeGenerator<T extends AffordanceTemplate> {

    private static final String BUILDER_VAR_NAME = "b";

    private final T affordanceTemplate;
    private final CodeGenerationContext context;
    private final BuilderMethodMode mode;
    private final String methodName;
    private final Type specType;
    private final List<BuilderMethodContributor> contributors;


    @SuppressWarnings("unchecked")
    public BuilderMethodCodeGenerator(
            T affordanceTemplate,
            CodeGenerationContext context,
            BuilderMethodMode mode, String methodName,
            Type specType,
            Collection<? extends BuilderMethodContributor> contributors) {
        this.affordanceTemplate = affordanceTemplate;
        this.context = context;
        this.mode = mode;
        this.methodName = methodName;
        this.specType = specType;

        this.contributors = contributors.stream()
                .filter(c -> c.appliesTo(affordanceTemplate, context, mode))
                .collect(Collectors.toList());
    }


    @Nonnull
    public MethodSpec generate() {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(specType)
                .addParameters(generateParameters())
                .addCode(generateCodeBefore())
                .addCode(generateReturnCodeBlock())
                .build();
    }


    @Nonnull
    private Iterable<ParameterSpec> generateParameters() {
        return contributors.stream()
                .flatMap(c -> c.generateMethodParameters(affordanceTemplate, context, mode))
                .collect(Collectors.toList());
    }


    @Nonnull
    private CodeBlock generateCodeBefore() {
        CodeBlock.Builder codeBlock = CodeBlock.builder();
        contributors.stream()
                .map(c -> c.generateCodeBefore(affordanceTemplate, context, mode))
                .filter(code -> code != null && !code.isEmpty())
                .forEach(codeBlock::add);
        return codeBlock.build();
    }


    @Nonnull
    private CodeBlock generateReturnCodeBlock() {

        CodeBlock.Builder contributedCodeBuilder = CodeBlock.builder();
        contributors.stream()
                .map(c -> c.generateBuilderSetterStatement(affordanceTemplate, context, mode, BUILDER_VAR_NAME))
                .filter(code -> code != null && !code.isEmpty())
                .forEach(code -> contributedCodeBuilder.add("\n").add(code));

        CodeBlock contributedCode = contributedCodeBuilder.build();
        if (contributedCode.isEmpty()) {
            return CodeBlock.builder()
                    .add("$[return $1L -> { };\n$]", BUILDER_VAR_NAME)
                    .build();
        } else {
            return CodeBlock.builder()
                    .add("$[return $1L -> $1L", BUILDER_VAR_NAME)
                    .indent().indent()
                    .add(contributedCode)
                    .add(";\n$]")
                    .unindent().unindent()
                    .build();
        }
    }


    @Nonnull
    private Comparator<ParameterSpec> parameterOrdering() {
        return Comparator.comparing(p -> p.name,
                Ordering.explicit(getHandlerMethodParameterNames()));
    }


    @Nonnull
    private List<String> getHandlerMethodParameterNames() {
        return affordanceTemplate.getSourceHandlerMethod().getParameters().stream()
                .map(param -> param.getSimpleName().toString())
                .collect(Collectors.toList());
    }
}
