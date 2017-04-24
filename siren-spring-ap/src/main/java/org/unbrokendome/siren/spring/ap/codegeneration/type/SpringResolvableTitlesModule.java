package org.unbrokendome.siren.spring.ap.codegeneration.type;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.unbrokendome.siren.ap.codegeneration.title.ResolvableTitleSupplierGenerator;


@AutoService(Module.class)
public class SpringResolvableTitlesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ResolvableTitleSupplierGenerator.class).to(SpringResolvableTitleSupplierGenerator.class);
    }
}
