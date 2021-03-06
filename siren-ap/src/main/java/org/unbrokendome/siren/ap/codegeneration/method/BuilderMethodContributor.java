package org.unbrokendome.siren.ap.codegeneration.method;

import org.unbrokendome.siren.ap.codegeneration.CodeGenerationContext;
import org.unbrokendome.siren.ap.codegeneration.method.contributors.BuilderMethodMode;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;


public interface BuilderMethodContributor {

    default boolean appliesTo(AffordanceTemplate affordanceTemplate,
                              CodeGenerationContext context, BuilderMethodMode mode) {
        return true;
    }

    @Nonnull
    default Stream<ParameterSpec> generateMethodParameters(
            AffordanceTemplate affordanceTemplate, CodeGenerationContext context,
            BuilderMethodMode mode) {
        return Stream.empty();
    }

    @Nullable
    default CodeBlock generateCodeBefore(
            AffordanceTemplate affordanceTemplate, CodeGenerationContext context,
            BuilderMethodMode mode) {
        return null;
    }

    @Nullable
    default CodeBlock generateBuilderSetterStatement(
            AffordanceTemplate affordanceTemplate, CodeGenerationContext context, BuilderMethodMode mode,
            String builderVariableName) {
        return null;
    }
}
