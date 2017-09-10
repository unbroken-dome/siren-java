package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.unbrokendome.siren.ap.codegeneration.CodeGenerationContext;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGenerator;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGeneratorFactory;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public abstract class AbstractHrefBuilderMethodContributor<T extends AffordanceTemplate>
        extends AbstractBuilderMethodContributor<T> {

    private static final String HREF_VARIABLE_NAME = "href";
    private static final String URIVARIABLES_VARIABLE_NAME = "uriVariables";
    private static final String CURRENT_REQUEST_PARAMETER_NAME = "request";

    private final UriBuilderCodeGeneratorFactory uriBuilderCodeGeneratorFactory;


    protected AbstractHrefBuilderMethodContributor(UriBuilderCodeGeneratorFactory uriBuilderCodeGeneratorFactory) {
        this.uriBuilderCodeGeneratorFactory = uriBuilderCodeGeneratorFactory;
    }


    @Override
    @Nonnull
    protected final Stream<ParameterSpec> doGenerateMethodParameters(T affordanceTemplate,
                                                                     CodeGenerationContext context) {
        Stream<ParameterSpec> forCurrentRequest;
        if (context == CodeGenerationContext.EXPLICIT_REQUEST) {
            forCurrentRequest = createMethodParameterForCurrentRequest();
        } else {
            forCurrentRequest = Stream.empty();
        }

        return Stream.concat(
                Stream.concat(forCurrentRequest,
                        createMethodParametersForRequestParameters(affordanceTemplate.getPathParameters())),
                generateAdditionalMethodParameters(affordanceTemplate));
    }


    private Stream<ParameterSpec> createMethodParameterForCurrentRequest() {
        ParameterSpec parameter = ParameterSpec.builder(
                ClassName.get("javax.servlet.http", "HttpServletRequest"),
                CURRENT_REQUEST_PARAMETER_NAME)
                .build();
        return Stream.of(parameter);
    }


    protected final Stream<ParameterSpec> createMethodParametersForRequestParameters(
            Map<String, VariableElement> requestParameters) {

        return requestParameters.entrySet().stream()
                .map(entry -> {
                    VariableElement originalParam = entry.getValue();
                    return ParameterSpec.builder(
                            TypeName.get(originalParam.asType()),
                            originalParam.getSimpleName().toString(),
                            originalParam.getModifiers().toArray(new Modifier[0]))
                            .build();
                });
    }


    protected Stream<ParameterSpec> generateAdditionalMethodParameters(T affordanceTemplate) {
        return Stream.empty();
    }


    @Override
    @Nonnull
    protected final CodeBlock doGenerateCodeBefore(T affordanceTemplate, CodeGenerationContext context) {
        return CodeBlock.builder()
                .add(constructCodeForUriVariablesMap(affordanceTemplate))
                .add(constructHrefCodeBlock(affordanceTemplate, context))
                .build();
    }


    @Nonnull
    private CodeBlock constructCodeForUriVariablesMap(T affordanceTemplate) {

        Map<String, VariableElement> pathParameters = affordanceTemplate.getPathParameters();

        CodeBlock.Builder builder = CodeBlock.builder();

        if (pathParameters.size() == 1) {
            pathParameters.forEach((name, param) -> builder
                    .addStatement("$T $L = $T.singletonMap($S, $L)",
                            ParameterizedTypeName.get(Map.class, String.class, Object.class),
                            URIVARIABLES_VARIABLE_NAME,
                            Collections.class,
                            name, param));

        } else if (!pathParameters.isEmpty()) {
            builder.addStatement("$T $L = new $T<>($L)",
                    ParameterizedTypeName.get(Map.class, String.class, Object.class),
                    URIVARIABLES_VARIABLE_NAME,
                    HashMap.class,
                    pathParameters.size());
            pathParameters.forEach((name, param) ->
                    builder.addStatement("$L.put($S, $L)",
                            URIVARIABLES_VARIABLE_NAME,
                            name, param));
        }

        return builder.build();
    }


    @Nonnull
    private CodeBlock constructHrefCodeBlock(T affordanceTemplate, CodeGenerationContext context) {

        UriBuilderCodeGenerator uriBuilderCodeGenerator;
        if (context == CodeGenerationContext.EXPLICIT_REQUEST) {
            uriBuilderCodeGenerator = uriBuilderCodeGeneratorFactory.fromRequestVariable(CURRENT_REQUEST_PARAMETER_NAME);
        } else {
            uriBuilderCodeGenerator = uriBuilderCodeGeneratorFactory.fromCurrentRequest();
        }

        if (!affordanceTemplate.getPathSegments().isEmpty()) {
            affordanceTemplate.getPathSegments()
                    .forEach(uriBuilderCodeGenerator::pathSegment);
        } else {
            uriBuilderCodeGenerator.path("/");
        }

        modifyUriBuilder(affordanceTemplate, uriBuilderCodeGenerator);

        if (!affordanceTemplate.getPathParameters().isEmpty()) {
            uriBuilderCodeGenerator.expandVariables(URIVARIABLES_VARIABLE_NAME);
        }

        return CodeBlock.builder()
                .add("$[$T $L = ", String.class, HREF_VARIABLE_NAME)
                .add(uriBuilderCodeGenerator.toCodeBlock())
                .add(";\n$]")
                .build();
    }


    protected void modifyUriBuilder(T affordanceTemplate, UriBuilderCodeGenerator uriBuilderCodeGenerator) {
    }


    @Override
    @Nullable
    protected final CodeBlock doGenerateBuilderSetterStatement(T affordanceTemplate, String builderVariableName) {
        return CodeBlock.builder()
                .add(".setHref($L)", HREF_VARIABLE_NAME)
                .build();
    }
}
