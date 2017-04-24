package org.unbrokendome.siren.ap;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterators;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public final class ResourceUtils {

    public static Stream<String> collectLinesFromResources(String resourceName) {
        return collectLinesFromResources(Thread.currentThread().getContextClassLoader(), resourceName);
    }


    public static Stream<String> collectLinesFromResources(ClassLoader classLoader, String resourceName) {

        return collectResources(classLoader, resourceName)
                .flatMap(resourceURL -> {
                    try {
                        return Resources.readLines(resourceURL, Charsets.UTF_8).stream();
                    } catch (IOException ex) {
                        throw new RuntimeIOException(ex);
                    }
                })
                .map(String::trim)
                .filter(line -> !line.isEmpty());
    }


    private static Stream<URL> collectResources(ClassLoader classLoader, String resourceName) {
        try {
            Enumeration<URL> resourcesEnumeration = classLoader.getResources(resourceName);
            Iterator<URL> resourcesIterator = Iterators.forEnumeration(resourcesEnumeration);
            Spliterator<URL> resourcesSpliterator = Spliterators.spliteratorUnknownSize(resourcesIterator,
                    Spliterator.DISTINCT | Spliterator.IMMUTABLE | Spliterator.NONNULL);
            return StreamSupport.stream(resourcesSpliterator, false);

        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }
}
