package org.unbrokendome.siren.spring.ap.testsupport;

import org.spockframework.mock.IDefaultResponse;
import org.spockframework.mock.IMockInvocation;
import org.spockframework.mock.ZeroOrNullResponse;
import org.spockframework.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.List;


public class DelegatingDefaultResponse<T> implements IDefaultResponse {

    private final T delegate;
    private final Class<T> delegateClass;


    public DelegatingDefaultResponse(T delegate, Class<T> delegateClass) {
        this.delegate = delegate;
        this.delegateClass = delegateClass;
    }


    @Override
    public Object respond(IMockInvocation invocation) {

        List<Class<?>> parameterTypes = invocation.getMethod().getParameterTypes();

        try {
            Method method = delegateClass.getMethod(invocation.getMethod().getName(),
                    parameterTypes.toArray(new Class<?>[parameterTypes.size()]));

            return ReflectionUtil.invokeMethod(delegate, method, invocation.getArguments().toArray());

        } catch (NoSuchMethodException ex) {
            return ZeroOrNullResponse.INSTANCE.respond(invocation);
        }
    }
}
