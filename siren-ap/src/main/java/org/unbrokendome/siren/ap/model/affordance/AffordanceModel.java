package org.unbrokendome.siren.ap.model.affordance;

import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;

import java.util.Collection;


public interface AffordanceModel {

    Collection<AffordanceGroup> getGroups();
}
