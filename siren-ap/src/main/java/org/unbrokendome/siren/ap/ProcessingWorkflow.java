package org.unbrokendome.siren.ap;

import javax.lang.model.element.TypeElement;
import java.util.Set;


public interface ProcessingWorkflow {

    void process(Set<? extends TypeElement> annotations);
}
