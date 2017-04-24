package org.unbrokendome.siren.ap.codegeneration.title;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import org.unbrokendome.siren.ap.model.affordance.AffordanceTemplate;


public final class TitleCodeGenerationUtils {

    private static final Converter<String, String> CASE_CONVERTER =
            CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE);


    private TitleCodeGenerationUtils() { }


    public static String getTitlesFieldName(AffordanceTemplate affordanceTemplate) {
        return CASE_CONVERTER.convert(affordanceTemplate.getSourceMethodInfo().getName()) + "_TITLES";
    }
}
