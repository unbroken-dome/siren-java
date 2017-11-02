package org.unbrokendome.siren.ap.codegeneration;

import com.google.inject.multibindings.Multibinder;
import org.unbrokendome.siren.ap.codegeneration.feature.FeatureVoter;
import org.unbrokendome.siren.ap.codegeneration.method.BuilderMethodContributor;
import org.unbrokendome.siren.ap.codegeneration.type.TypeSpecContributor;
import org.unbrokendome.siren.ap.ExtensionBinder;
import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;


@AutoService(Module.class)
public class CodeGenerationModule extends AbstractModule {

    @Override
    protected void configure() {

        Multibinder<CodeGenerator> codeGeneratorBinder = Multibinder.newSetBinder(binder(), CodeGenerator.class);
        codeGeneratorBinder.addBinding().to(DefaultCodeGenerator.class);

        ExtensionBinder.registerExtensionPoints(binder(),
                TypeSpecContributor.class,
                BuilderMethodContributor.class,
                FeatureVoter.class);
    }
}
