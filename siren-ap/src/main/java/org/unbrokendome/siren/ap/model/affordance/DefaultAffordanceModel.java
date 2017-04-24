package org.unbrokendome.siren.ap.model.affordance;

import com.google.common.collect.ImmutableList;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;

import java.util.Collection;


public class DefaultAffordanceModel implements AffordanceModel {

    private final Collection<AffordanceGroup> groups;


    public DefaultAffordanceModel(Collection<AffordanceGroup> groups) {
        this.groups = ImmutableList.copyOf(groups);
    }


    @Override
    public Collection<AffordanceGroup> getGroups() {
        return groups;
    }
}
