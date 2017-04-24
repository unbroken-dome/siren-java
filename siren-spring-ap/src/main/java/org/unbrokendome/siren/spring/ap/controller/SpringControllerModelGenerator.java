package org.unbrokendome.siren.spring.ap.controller;

import org.unbrokendome.siren.ap.AnnotationUtils;
import org.unbrokendome.siren.ap.model.controller.*;

import javax.annotation.Nonnull;
import javax.annotation.processing.RoundEnvironment;
import javax.inject.Inject;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;


public class SpringControllerModelGenerator implements ControllerModelGenerator {

    private final RoundEnvironment roundEnvironment;
    private final Elements elements;


    @Inject
    public SpringControllerModelGenerator(RoundEnvironment roundEnvironment, Elements elements) {
        this.roundEnvironment = roundEnvironment;
        this.elements = elements;
    }


    @Nonnull
    @Override
    public ControllerModel generate(Set<? extends TypeElement> requestMappingAnnotationTypes) {

        Collection<ControllerInfo> controllerInfos = findRequestMappingMethods(requestMappingAnnotationTypes)
                .map(handlerMethod -> (RequestHandlerMethodInfo) new SpringRequestHandlerMethodInfo(handlerMethod, elements))
                .collect(groupingBy(RequestHandlerMethodInfo::getControllerType))
                .values().stream()
                .map(it -> new DefaultControllerInfo(it.get(0).getControllerType(), it))
                .collect(toList());

        return new DefaultControllerModel(controllerInfos);
    }


    @Nonnull
    private Stream<ExecutableElement> findRequestMappingMethods(
            Set<? extends TypeElement> requestMappingAnnotationTypes) {

        Stream<ExecutableElement> methods = requestMappingAnnotationTypes.stream()
                .flatMap(annotationType -> roundEnvironment.getElementsAnnotatedWith(annotationType).stream())
                .filter(element -> element.getKind() == ElementKind.METHOD)
                .filter(element -> element.getEnclosingElement().getKind() == ElementKind.CLASS)
                .map(element -> (ExecutableElement) element);

        TypeElement controllerAdviceAnnotation = elements.getTypeElement(
                "org.springframework.web.bind.annotation.ControllerAdvice");
        if (controllerAdviceAnnotation != null) {
            methods = methods.filter(method -> !AnnotationUtils.hasAnnotation(method, controllerAdviceAnnotation));
        }

        return methods;
    }
}
