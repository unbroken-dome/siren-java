package org.unbrokendome.siren.ap.model.affordance.grouping;

import org.unbrokendome.siren.ap.model.affordance.QualifiedName;

import javax.lang.model.element.TypeElement;


public interface AffordanceGroupNamingStrategy {

    QualifiedName getQualifiedName(TypeElement sourceType, AffordanceGroupKind kind);
}
