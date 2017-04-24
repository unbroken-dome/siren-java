package org.unbrokendome.siren.ap.codegeneration.title;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.multibindings.OptionalBinder;


@AutoService(Module.class)
public class ResolvableTitlesModule extends AbstractModule {

    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(binder(), ResolvableTitleSupplierGenerator.class);
    }
}
