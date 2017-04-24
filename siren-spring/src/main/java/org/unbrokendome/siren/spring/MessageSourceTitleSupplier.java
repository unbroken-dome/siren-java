package org.unbrokendome.siren.spring;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;


public class MessageSourceTitleSupplier implements Supplier<String> {

    private final MessageSourceResolvable resolvableTitle;


    public MessageSourceTitleSupplier(MessageSourceResolvable resolvableTitle) {
        this.resolvableTitle = resolvableTitle;
    }


    public MessageSourceTitleSupplier(String titleKey) {
        this(new DefaultMessageSourceResolvable(titleKey));
    }


    @Override
    public String get() {
        HttpServletRequest request = RequestUtils.getCurrentRequest();
        WebApplicationContext applicationContext = RequestContextUtils.findWebApplicationContext(request);
        return applicationContext.getMessage(resolvableTitle, request.getLocale());
    }
}
