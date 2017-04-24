package org.unbrokendome.siren.ap.codegeneration.type;

import com.squareup.javapoet.TypeSpec;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;


public interface TypeSpecContributor {

    void contribute(AffordanceGroup affordanceGroup, TypeSpec.Builder typeSpec);

    default boolean appliesTo(AffordanceGroup affordanceGroup) {
        return true;
    }
}
