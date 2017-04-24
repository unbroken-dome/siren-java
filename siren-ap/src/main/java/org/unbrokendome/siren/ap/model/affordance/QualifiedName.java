package org.unbrokendome.siren.ap.model.affordance;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;


@Immutable
public final class QualifiedName {

    private final String packageName;
    private final String simpleName;


    public QualifiedName(String packageName, String simpleName) {
        this.packageName = packageName;
        this.simpleName = simpleName;
    }


    public String getPackageName() {
        return packageName;
    }


    public String getSimpleName() {
        return simpleName;
    }


    @Override
    public boolean equals(Object obj) {
        return (obj instanceof QualifiedName) && equals((QualifiedName) obj);
    }


    private boolean equals(QualifiedName other) {
        return packageName.equals(other.packageName) && simpleName.equals(other.simpleName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(packageName, simpleName);
    }


    @Override
    public String toString() {
        return packageName + "." + simpleName;
    }
}
