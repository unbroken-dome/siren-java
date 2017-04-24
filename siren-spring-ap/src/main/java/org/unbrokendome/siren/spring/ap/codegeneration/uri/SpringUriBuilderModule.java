package org.unbrokendome.siren.spring.ap.codegeneration.uri;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.unbrokendome.siren.ap.codegeneration.uri.UriBuilderCodeGeneratorFactory;


@AutoService(Module.class)
public class SpringUriBuilderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UriBuilderCodeGeneratorFactory.class).to(SpringUriBuilderCodeGeneratorFactory.class);
    }
}
