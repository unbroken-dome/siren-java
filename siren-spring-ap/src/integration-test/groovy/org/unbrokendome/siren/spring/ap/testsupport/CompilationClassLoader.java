package org.unbrokendome.siren.spring.ap.testsupport;

import com.google.common.io.ByteStreams;
import com.google.testing.compile.Compilation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CompilationClassLoader extends ClassLoader {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Compilation compilation;


    public CompilationClassLoader(ClassLoader parent, Compilation compilation) {
        super(parent);
        this.compilation = compilation;
    }


    public CompilationClassLoader(Compilation compilation) {
        this.compilation = compilation;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String packageName, simpleClassName;

        int lastDotIndex = name.lastIndexOf('.');
        if (lastDotIndex == -1) {
            packageName = "";
            simpleClassName = name;
        } else {
            packageName = name.substring(0, lastDotIndex);
            simpleClassName = name.substring(lastDotIndex + 1);
        }

        JavaFileObject javaFileObject = compilation.generatedFile(StandardLocation.CLASS_OUTPUT,
                packageName, simpleClassName + ".class")
                .orElseThrow(() -> {
                    logger.debug("Classes present in classloader: [ {} ]",
                            getAllClassNames().collect(Collectors.joining(", ")));
                    return new ClassNotFoundException(name);
                });

        try {
            try (InputStream input = javaFileObject.openInputStream()) {
                byte[] classBytes = ByteStreams.toByteArray(input);
                return defineClass(name, classBytes, 0, classBytes.length, null);
            }
        } catch (IOException e) {
            throw new ClassNotFoundException("Cannot read class: " + name, e);
        }
    }


    private Stream<String> getAllClassNames() {
        return compilation.generatedFiles().stream()
                .filter(f -> f.getKind() == JavaFileObject.Kind.CLASS)
                .map(FileObject::getName)
                .map(name -> name.replaceFirst("^/CLASS_OUTPUT/", ""))
                .map(name -> name.replaceFirst("\\.class$", ""))
                .map(name -> name.replace('/', '.'));
    }
}
