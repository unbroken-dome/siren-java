package org.unbrokendome.siren.ap.model.affordance.grouping;

import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;

import java.util.stream.Stream;


public interface AffordanceGroupingStrategy {

    Stream<AffordanceGroup> getGroups(Stream<AffordanceTemplate> affordanceTemplates);
}
