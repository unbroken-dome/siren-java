package org.unbrokendome.siren.ap;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SirenProcessor extends AbstractProcessor {

    private Module processingEnvironmentModule;


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ResourceUtils.collectLinesFromResources(
                getClass().getClassLoader(),
                "META-INF/siren.annotation.types")
                .collect(Collectors.toSet());
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        processingEnvironmentModule = new ProcessingEnvironmentModule(processingEnv);
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        ServiceLoader<Module> moduleServiceLoader = ServiceLoader.load(Module.class, getClass().getClassLoader());

        List<Module> modules = ImmutableList.<Module>builder()
                .add(processingEnvironmentModule)
                .add(binder -> binder.bind(RoundEnvironment.class).toInstance(roundEnv))
                .add(binder -> binder.bind(ProcessingWorkflow.class).to(DefaultProcessingWorkflow.class))
                .addAll(moduleServiceLoader)
                .build();

        Injector injector = Guice.createInjector(modules);

        ProcessingWorkflow processingWorkflow = injector.getInstance(ProcessingWorkflow.class);
        processingWorkflow.process(annotations);

        return false;
    }


    private static class ProcessingEnvironmentModule extends AbstractModule {

        private final ProcessingEnvironment processingEnvironment;

        private ProcessingEnvironmentModule(ProcessingEnvironment processingEnvironment) {
            this.processingEnvironment = processingEnvironment;
        }

        @Override
        protected void configure() {
            bind(ProcessingEnvironment.class).toInstance(processingEnvironment);
        }

        @Provides
        public Elements getElements() {
            return processingEnvironment.getElementUtils();
        }

        @Provides
        public Types getTypes() {
            return processingEnvironment.getTypeUtils();
        }

        @Provides
        public Messager getMessager() {
            return processingEnvironment.getMessager();
        }

        @Provides
        public Filer getFiler() {
            return processingEnvironment.getFiler();
        }
    }
}
