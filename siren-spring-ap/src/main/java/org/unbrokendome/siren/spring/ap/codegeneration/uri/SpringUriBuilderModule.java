package org.unbrokendome.siren.spring.ap.codegeneration.uri;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGeneratorFactory;
import org.unbrokendome.siren.spring.ap.util.ClassUtils;
import org.unbrokendome.siren.spring.ap.util.SpringDependencyUtils;

import javax.annotation.Nullable;


@AutoService(Module.class)
public class SpringUriBuilderModule extends AbstractModule {

    @Override
    protected void configure() {
        Class<? extends UriBuilderCodeGeneratorFactory> factoryClass = chooseFactoryClass();
        if (factoryClass != null) {
            bind(UriBuilderCodeGeneratorFactory.class).to(factoryClass);
        }
    }


    @Nullable
    private Class<? extends UriBuilderCodeGeneratorFactory> chooseFactoryClass() {
        if (SpringDependencyUtils.isSpringWebMvcPresent()) {
            return WebMvcUriBuilderCodeGeneratorFactory.class;

        } else if (SpringDependencyUtils.isSpringWebFluxPresent()) {
            return WebFluxUriBuilderCodeGeneratorFactory.class;

        } else {
            return null;
        }
    }
}
