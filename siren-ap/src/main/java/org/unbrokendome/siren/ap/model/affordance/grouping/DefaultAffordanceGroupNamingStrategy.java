package org.unbrokendome.siren.ap.model.affordance.grouping;

import org.unbrokendome.siren.ap.model.affordance.QualifiedName;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;


public class DefaultAffordanceGroupNamingStrategy implements AffordanceGroupNamingStrategy {

    @Override
    public QualifiedName getQualifiedName(TypeElement sourceType, AffordanceGroupKind kind) {

        String packageName = ((PackageElement) sourceType.getEnclosingElement()).getQualifiedName().toString();
        String simpleName = sourceType.getSimpleName().toString() + getNameSuffix(kind);

        return new QualifiedName(packageName, simpleName);
    }


    private String getNameSuffix(AffordanceGroupKind kind) {
        switch (kind) {
            case LINK:
                return "Links";
            case ACTION:
                return "Actions";
            default:
                return "Affordances";
        }
    }
}
