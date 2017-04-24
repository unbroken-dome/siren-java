package org.unbrokendome.siren.ap.model.affordance.grouping;

import org.unbrokendome.siren.ap.model.affordance.action.ActionTemplate;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;
import org.unbrokendome.siren.ap.model.affordance.link.LinkTemplate;
import org.unbrokendome.siren.ap.model.affordance.QualifiedName;

import javax.inject.Inject;
import javax.lang.model.element.TypeElement;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;


public class DefaultAffordanceGroupingStrategy implements AffordanceGroupingStrategy {

    private final AffordanceGroupNamingStrategy namingStrategy;


    @Inject
    public DefaultAffordanceGroupingStrategy(AffordanceGroupNamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }


    @Override
    public Stream<AffordanceGroup> getGroups(Stream<AffordanceTemplate> affordanceTemplates) {
        return affordanceTemplates
                .collect(groupingBy((t) ->
                    new GroupingKey(t.getSourceMethodInfo().getControllerType(),
                            getGroupKindForTemplate(t))))
                .entrySet().stream()
                .filter(entry -> entry.getKey().getGroupKind() != AffordanceGroupKind.UNKNOWN)
                .map(entry -> createAffordanceGroup(entry.getKey(), entry.getValue()));
    }


    private AffordanceGroupKind getGroupKindForTemplate(AffordanceTemplate template) {
        if (template instanceof LinkTemplate) {
            return AffordanceGroupKind.LINK;
        } else if (template instanceof ActionTemplate) {
            return AffordanceGroupKind.ACTION;
        } else {
            return AffordanceGroupKind.UNKNOWN;
        }
    }


    private AffordanceGroup createAffordanceGroup(GroupingKey key,
                                                  Iterable<AffordanceTemplate> affordanceTemplates) {
        QualifiedName qualifiedName = namingStrategy.getQualifiedName(
                key.getControllerType(), key.getGroupKind());

        return new DefaultAffordanceGroup(key.getControllerType(), key.getGroupKind(),
                qualifiedName, affordanceTemplates);
    }


    private static final class GroupingKey {

        private final TypeElement controllerType;
        private final AffordanceGroupKind groupKind;


        private GroupingKey(TypeElement controllerType, AffordanceGroupKind groupKind) {
            this.controllerType = controllerType;
            this.groupKind = groupKind;
        }


        public TypeElement getControllerType() {
            return controllerType;
        }


        public AffordanceGroupKind getGroupKind() {
            return groupKind;
        }


        @Override
        public boolean equals(Object obj) {
            return obj instanceof GroupingKey && equals((GroupingKey) obj);
        }


        private boolean equals(GroupingKey other) {
            return Objects.equals(this.controllerType, other.controllerType)
                    && Objects.equals(this.groupKind, other.groupKind);
        }


        @Override
        public int hashCode() {
            return Objects.hash(controllerType, groupKind);
        }
    }
}
