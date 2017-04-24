package org.unbrokendome.siren.ap.codegeneration.title;

import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;


public interface ResolvableTitleSupplierGenerator {

    CodeBlock buildTitleSupplierExpression(AffordanceTitle title);
}
