package org.unbrokendome.siren.ap.model.affordance.link;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitleUtils;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;

import javax.annotation.Nonnull;
import javax.lang.model.element.VariableElement;
import java.util.Map;


public interface LinkTemplate extends AffordanceTemplate {

    @Nonnull
    Map<String, VariableElement> getQueryParameters();


    @Nonnull
    Map<String, AffordanceTitle> getTitleMap();


    default boolean hasTitle() {
        return getTitle() != null || !getTitleMap().isEmpty();
    }


    default boolean hasTitleMap() {
        return !getTitleMap().isEmpty();
    }


    default boolean hasResolvableTitles() {
        return (getTitle() != null && getTitle().isResolvable())
                || getTitleMap().values().stream().anyMatch(AffordanceTitle::isResolvable);
    }


    @Nonnull
    static LinkTemplateBuilder builder(String name, RequestHandlerMethodInfo sourceMethodInfo) {
        return new DefaultLinkTemplateBuilder(name, sourceMethodInfo);
    }
}
