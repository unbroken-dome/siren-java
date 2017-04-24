package org.unbrokendome.siren.ap.model.affordance.action;

import com.google.auto.service.AutoService;
import org.unbrokendome.siren.ap.PropertyScanner;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;
import org.unbrokendome.siren.model.ActionField;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;


@AutoService(ActionTemplateGeneratorContributor.class)
public class RequestBodyClassFieldsContributor implements ActionTemplateGeneratorContributor {

    private final Types types;


    @Inject
    public RequestBodyClassFieldsContributor(Types types) {
        this.types = types;
    }


    @Override
    public void contribute(RequestHandlerMethodInfo handlerMethod, ActionTemplateBuilder action) {

        VariableElement requestBodyParameter = handlerMethod.getRequestBodyParameter();
        if (requestBodyParameter != null) {
            TypeMirror requestBodyTypeMirror = requestBodyParameter.asType();
            if (requestBodyTypeMirror.getKind() == TypeKind.DECLARED) {
                TypeElement requestBodyType = (TypeElement) types.asElement(requestBodyTypeMirror);
                requestBodyType.accept(new Scanner(), action);
            }
        }
    }


    private static class Scanner extends PropertyScanner<Void, ActionTemplateBuilder> {

        @Nullable
        @Override
        public Void visitProperty(String propertyName, TypeMirror propertyType,
                                  @Nullable ExecutableElement getter, @Nullable ExecutableElement setter,
                                  ActionTemplateBuilder action) {
            if (getter != null) {
                ActionField actionField = ActionFields.fromElement(getter, propertyName);
                action.addField(actionField);
            }
            return null;
        }
    }
}
