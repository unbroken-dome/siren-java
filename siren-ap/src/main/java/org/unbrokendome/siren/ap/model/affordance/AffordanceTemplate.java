package org.unbrokendome.siren.ap.model.affordance;

import com.google.common.net.MediaType;
import org.unbrokendome.siren.ap.model.controller.RequestHandlerMethodInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Map;


public interface AffordanceTemplate {

    @Nonnull
    String getName();

    @Nonnull
    RequestHandlerMethodInfo getSourceMethodInfo();

    @Nonnull
    List<String> getPathSegments();

    @Nonnull
    Map<String, VariableElement> getPathParameters();

    @Nullable
    MediaType getType();

    @Nonnull
    Iterable<String> getClassNames();

    @Nullable
    AffordanceTitle getTitle();

    @Nonnull
    default TypeElement getSourceControllerType() {
        return getSourceMethodInfo().getControllerType();
    }

    @Nonnull
    default ExecutableElement getSourceHandlerMethod() {
        return getSourceMethodInfo().getHandlerMethod();
    }
}
