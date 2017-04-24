package org.unbrokendome.siren.spring.ap.controller;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.unbrokendome.siren.ap.model.controller.ControllerModelGenerator;


@AutoService(Module.class)
public class SpringControllerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ControllerModelGenerator.class).to(SpringControllerModelGenerator.class);
    }
}
