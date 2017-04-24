package org.unbrokendome.siren.ap.codegeneration.method;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;


public interface BuilderMethodContributor {

    default boolean appliesTo(AffordanceTemplate affordanceTemplate) {
        return true;
    }

    @Nonnull
    default Stream<ParameterSpec> generateMethodParameters(AffordanceTemplate affordanceTemplate) {
        return Stream.empty();
    }

    @Nullable
    default CodeBlock generateCodeBefore(AffordanceTemplate affordanceTemplate) {
        return null;
    }

    @Nullable
    default CodeBlock generateBuilderSetterStatement(AffordanceTemplate affordanceTemplate,
                                                     String builderVariableName) {
        return null;
    }
}
