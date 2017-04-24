package org.unbrokendome.siren.ap.model.affordance.grouping;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.QualifiedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import java.util.List;


public interface AffordanceGroup {

    @Nullable
    Element getSource();

    @Nonnull
    AffordanceGroupKind getKind();

    @Nonnull
    QualifiedName getQualifiedName();

    @Nonnull
    List<? extends AffordanceTemplate> getAffordanceTemplates();
}
