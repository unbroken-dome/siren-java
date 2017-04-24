package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;


@SuppressWarnings("unchecked")
public abstract class AbstractBuilderMethodContributor<T extends AffordanceTemplate>
        implements BuilderMethodContributor {


    protected abstract Class<T> getTargetTemplateType();


    @Override
    public final boolean appliesTo(AffordanceTemplate affordanceTemplate) {
        return getTargetTemplateType().isInstance(affordanceTemplate);
    }


    @Nonnull
    @Override
    public final Stream<ParameterSpec> generateMethodParameters(AffordanceTemplate affordanceTemplate) {
        return doGenerateMethodParameters((T) affordanceTemplate);
    }


    @Nonnull
    protected Stream<ParameterSpec> doGenerateMethodParameters(T affordanceTemplate) {
        return Stream.empty();
    }


    @Nullable
    @Override
    public final CodeBlock generateCodeBefore(AffordanceTemplate affordanceTemplate) {
        return doGenerateCodeBefore((T) affordanceTemplate);
    }


    @Nullable
    protected CodeBlock doGenerateCodeBefore(T affordanceTemplate) {
        return null;
    }


    @Nullable
    @Override
    public final CodeBlock generateBuilderSetterStatement(AffordanceTemplate affordanceTemplate,
                                                          String builderVariableName) {
        return doGenerateBuilderSetterStatement((T) affordanceTemplate, builderVariableName);
    }


    protected CodeBlock doGenerateBuilderSetterStatement(T affordanceTemplate, String builderVariableName) {
        return null;
    }
}
