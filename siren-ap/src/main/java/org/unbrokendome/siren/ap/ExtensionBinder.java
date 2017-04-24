package org.unbrokendome.siren.ap;

import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.ServiceConfigurationError;


public class ExtensionBinder<S> {

    private final Class<S> extensionPoint;
    private final ClassLoader classLoader;
    private final Binder binder;


    private ExtensionBinder(Binder binder, Class<S> extensionPoint, ClassLoader classLoader) {
        this.binder = binder;
        this.extensionPoint = extensionPoint;
        this.classLoader = classLoader;
    }


    public static <S> void registerExtensionPoint(Binder binder, Class<S> extensionPoint, ClassLoader classLoader) {
        new ExtensionBinder<>(binder, extensionPoint, classLoader)
                .registerExtensionPoint();
    }


    public static <S> void registerExtensionPoint(Binder binder, Class<S> extensionPoint) {
        registerExtensionPoint(binder, extensionPoint, ExtensionBinder.class.getClassLoader());
    }


    public static void registerExtensionPoints(Binder binder, Class<?>... extensionPoints) {
        for (Class<?> extensionPoint : extensionPoints) {
            registerExtensionPoint(binder, extensionPoint);
        }
    }


    private void registerExtensionPoint() {
        Enumeration<URL> resources = getConfigurationResources();
        while (resources.hasMoreElements()) {
            getServiceClassNames(resources.nextElement())
                    .forEach(line -> {
                        String className = line.trim();
                        Class<? extends S> providerClass = loadProviderClassChecked(className);

                        TypeLiteral<S> implementedExtensionPointType =
                                getImplementedExtensionPointType(providerClass);

                        Multibinder.newSetBinder(binder, implementedExtensionPointType)
                                .addBinding().to(providerClass);
                    });
        }
    }


    @SuppressWarnings("unchecked")
    private TypeLiteral<S> getImplementedExtensionPointType(Class<?> providerClass) {

        TypeToken<?> typeToken = TypeToken.of(providerClass).getTypes().stream()
                .filter(type -> getRawType(type.getType()) == extensionPoint)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Provider " + providerClass +
                        " does not implement extension point " + extensionPoint.getName()));

        return (TypeLiteral<S>) TypeLiteral.get(typeToken.getType());
    }


    private static Type getRawType(Type type) {
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getRawType();
        } else {
            return type;
        }
    }


    private Class<? extends S> loadProviderClassChecked(String className) {
        Class<?> providerClass = loadProviderClass(className);
        try {
            return providerClass.asSubclass(extensionPoint);
        } catch (ClassCastException e) {
            throw fail("Provider " + className + " not a subtype", e);
        }
    }


    private Class<?> loadProviderClass(String className) {

        Class<?> providerClass;
        try {
            providerClass = Class.forName(className, false, classLoader);

        } catch (ClassNotFoundException e) {
            throw fail("Provider " + className + " not found", e);
        }
        return providerClass;
    }


    private Enumeration<URL> getConfigurationResources() {
        try {
            return classLoader.getResources("META-INF/services/" + extensionPoint.getName());

        } catch (IOException e) {
            throw fail("Error locating configuration files", e);
        }
    }


    private List<String> getServiceClassNames(URL configResource) {
        try {
            return Resources.readLines(configResource, Charset.defaultCharset());

        } catch (IOException e) {
            throw fail("Error reading configuration file", e);
        }
    }


    private ServiceConfigurationError fail(String msg, Throwable cause) {
        return new ServiceConfigurationError(extensionPoint.getName() + ": " + msg, cause);
    }
}
