package org.unbrokendome.siren.ap.model.affordance.grouping;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import com.google.common.collect.ImmutableList;
import org.unbrokendome.siren.ap.model.affordance.QualifiedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import java.util.List;


public class DefaultAffordanceGroup implements AffordanceGroup {

    @Nullable
    private final Element source;
    private final AffordanceGroupKind kind;
    private final QualifiedName qualifiedName;
    private final List<? extends AffordanceTemplate> affordanceTemplates;


    public DefaultAffordanceGroup(@Nullable Element source, AffordanceGroupKind kind,
                                  QualifiedName qualifiedName,
                                  Iterable<? extends AffordanceTemplate> affordanceTemplates) {
        this.source = source;
        this.kind = kind;
        this.qualifiedName = qualifiedName;
        this.affordanceTemplates = ImmutableList.copyOf(affordanceTemplates);
    }


    @Override
    @Nullable
    public Element getSource() {
        return source;
    }


    @Nonnull
    @Override
    public AffordanceGroupKind getKind() {
        return kind;
    }


    @Nonnull
    @Override
    public QualifiedName getQualifiedName() {
        return qualifiedName;
    }


    @Nonnull
    @Override
    public List<? extends AffordanceTemplate> getAffordanceTemplates() {
        return affordanceTemplates;
    }
}
