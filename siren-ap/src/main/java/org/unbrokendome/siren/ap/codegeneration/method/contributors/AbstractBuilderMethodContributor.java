package org.unbrokendome.siren.ap.codegeneration.method.contributors;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;
import org.unbrokendome.siren.ap.codegeneration.CodeGenerationContext;
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
    public final boolean appliesTo(AffordanceTemplate affordanceTemplate, CodeGenerationContext context) {
        return getTargetTemplateType().isInstance(affordanceTemplate)
                && appliesTo(context);
    }


    protected boolean appliesTo(CodeGenerationContext context) {
        return true;
    }


    @Nonnull
    @Override
    public final Stream<ParameterSpec> generateMethodParameters(
            AffordanceTemplate affordanceTemplate, CodeGenerationContext context) {
        return doGenerateMethodParameters((T) affordanceTemplate, context);
    }


    @Nonnull
    protected Stream<ParameterSpec> doGenerateMethodParameters(T affordanceTemplate, CodeGenerationContext context) {
        return Stream.empty();
    }


    @Nullable
    @Override
    public final CodeBlock generateCodeBefore(AffordanceTemplate affordanceTemplate, CodeGenerationContext context) {
        return doGenerateCodeBefore((T) affordanceTemplate, context);
    }


    @Nullable
    protected CodeBlock doGenerateCodeBefore(T affordanceTemplate, CodeGenerationContext context) {
        return null;
    }


    @Nullable
    @Override
    public final CodeBlock generateBuilderSetterStatement(AffordanceTemplate affordanceTemplate,
                                                          CodeGenerationContext context,
                                                          String builderVariableName) {
        return doGenerateBuilderSetterStatement((T) affordanceTemplate, builderVariableName);
    }


    protected CodeBlock doGenerateBuilderSetterStatement(T affordanceTemplate, String builderVariableName) {
        return null;
    }
}
