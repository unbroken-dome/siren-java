package org.unbrokendome.siren.ap.model.affordance.action;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitleUtils;
import org.unbrokendome.siren.model.ActionField;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.annotation.Nonnull;
import java.util.List;


public interface ActionTemplate extends AffordanceTemplate {

    @Nonnull
    String getMethod();


    @Nonnull
    List<ActionField> getFields();


    @Nonnull
    static ActionTemplateBuilder builder(String name, RequestHandlerMethodInfo sourceMethodInfo) {
        return new DefaultActionTemplateBuilder(name, sourceMethodInfo);
    }
}
