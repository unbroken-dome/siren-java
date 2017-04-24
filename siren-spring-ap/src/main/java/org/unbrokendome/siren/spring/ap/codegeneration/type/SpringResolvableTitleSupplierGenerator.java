package org.unbrokendome.siren.spring.ap.codegeneration.type;

import com.squareup.javapoet.CodeBlock;
import org.unbrokendome.siren.ap.codegeneration.title.ResolvableTitleSupplierGenerator;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTitle;
import org.unbrokendome.siren.spring.MessageSourceTitleSupplier;


public class SpringResolvableTitleSupplierGenerator implements ResolvableTitleSupplierGenerator {

    @Override
    public CodeBlock buildTitleSupplierExpression(AffordanceTitle title) {
        return CodeBlock.of("new $T($S)", MessageSourceTitleSupplier.class, title.getResolvableTitleKey());
    }
}
