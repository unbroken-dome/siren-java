package org.unbrokendome.siren.ap.codegeneration.method;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import com.google.common.collect.Ordering;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class BuilderMethodCodeGenerator<T extends AffordanceTemplate> {

    private static final String BUILDER_VAR_NAME = "b";

    private final T affordanceTemplate;
    private final String methodName;
    private final Type specType;
    private final List<BuilderMethodContributor> contributors;


    @SuppressWarnings("unchecked")
    public BuilderMethodCodeGenerator(
            T affordanceTemplate,
            String methodName,
            Type specType,
            Collection<? extends BuilderMethodContributor> contributors) {
        this.affordanceTemplate = affordanceTemplate;
        this.methodName = methodName;
        this.specType = specType;

        this.contributors = contributors.stream()
                .filter(c -> c.appliesTo(affordanceTemplate))
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
                .flatMap(c -> c.generateMethodParameters(affordanceTemplate))
                .sorted(parameterOrdering())
                .collect(Collectors.toList());
    }


    @Nonnull
    private CodeBlock generateCodeBefore() {
        CodeBlock.Builder codeBlock = CodeBlock.builder();
        contributors.stream()
                .map(c -> c.generateCodeBefore(affordanceTemplate))
                .filter(code -> code != null && !code.isEmpty())
                .forEach(codeBlock::add);
        return codeBlock.build();
    }


    @Nonnull
    private CodeBlock generateReturnCodeBlock() {
        CodeBlock.Builder returnCodeBlock = CodeBlock.builder()
                .add("$[return $1L -> $1L", BUILDER_VAR_NAME)
                .indent().indent();
        contributors.stream()
                .map(c -> c.generateBuilderSetterStatement(affordanceTemplate, BUILDER_VAR_NAME))
                .filter(code -> code != null && !code.isEmpty())
                .forEach(code -> returnCodeBlock.add("\n").add(code));
        returnCodeBlock.add(";\n$]")
                .unindent().unindent();
        return returnCodeBlock.build();
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
