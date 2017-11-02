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
    public final boolean appliesTo(AffordanceTemplate affordanceTemplate, CodeGenerationContext context,
                                   BuilderMethodMode mode) {
        return getTargetTemplateType().isInstance(affordanceTemplate)
                && appliesTo(context, mode);
    }


    protected boolean appliesTo(CodeGenerationContext context, BuilderMethodMode mode) {
        return true;
    }


    @Nonnull
    @Override
    public final Stream<ParameterSpec> generateMethodParameters(
            AffordanceTemplate affordanceTemplate, CodeGenerationContext context, BuilderMethodMode mode) {
        return doGenerateMethodParameters((T) affordanceTemplate, context, mode);
    }


    @Nonnull
    protected Stream<ParameterSpec> doGenerateMethodParameters(T affordanceTemplate, CodeGenerationContext context,
                                                               BuilderMethodMode mode) {
        return Stream.empty();
    }


    @Nullable
    @Override
    public final CodeBlock generateCodeBefore(AffordanceTemplate affordanceTemplate,
                                              CodeGenerationContext context, BuilderMethodMode mode) {
        return doGenerateCodeBefore((T) affordanceTemplate, context, mode);
    }


    @Nullable
    protected CodeBlock doGenerateCodeBefore(T affordanceTemplate, CodeGenerationContext context,
                                             BuilderMethodMode mode) {
        return null;
    }


    @Nullable
    @Override
    public final CodeBlock generateBuilderSetterStatement(
            AffordanceTemplate affordanceTemplate, CodeGenerationContext context,
            BuilderMethodMode mode, String builderVariableName) {
        return doGenerateBuilderSetterStatement((T) affordanceTemplate, builderVariableName);
    }


    protected CodeBlock doGenerateBuilderSetterStatement(T affordanceTemplate, String builderVariableName) {
        return null;
    }
}
