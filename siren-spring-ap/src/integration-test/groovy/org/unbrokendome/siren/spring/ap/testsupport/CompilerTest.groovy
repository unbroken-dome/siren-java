package org.unbrokendome.siren.spring.ap.testsupport

import com.google.testing.compile.Compilation
import com.google.testing.compile.Compiler
import com.google.testing.compile.JavaFileObjects
import groovy.util.logging.Slf4j
import org.junit.Assume
import org.unbrokendome.siren.ap.SirenProcessor
import spock.lang.Shared

import javax.tools.JavaFileObject


trait CompilerTest {

    @Shared
    SirenProcessor processor = new SirenProcessor()

    @Shared
    Compiler compiler = Compiler.javac().withProcessors(processor)

    private Compilation compilation = null
    private CompilationClassLoader compilationClassLoader


    void dumpGeneratedSources() {

        println()
        println '===== GENERATED SOURCES ====='
        println()

        compilation.generatedSourceFiles().each { sourceFile ->
            println "=== FILE: ${sourceFile.name} ==="
            println sourceFile.getCharContent(true)
            println()
        }
    }


    void compile() {
        compilation = compiler.compile(collectSources())
        dumpGeneratedSources()

        if (!compilation.errors().empty) {
            throw new IllegalStateException('Compilation failed with errors:\n'
                    + compilation.errors().collect { it.toString() }.join('\n'))
        }

        compilationClassLoader = new CompilationClassLoader(Thread.currentThread().contextClassLoader, compilation)
    }


    Compilation getCompilation() {
        if (compilation == null) {
            try {
                compile()
            } catch (Exception e) {
                Assume.assumeNoException('Compilation failed', e)
            }
        }
        compilation
    }


    ClassLoader getCompilationClassLoader() {
        if (compilationClassLoader == null) {
            try {
                compile()
            } catch (Exception e) {
                Assume.assumeNoException('Compilation failed', e)
            }
        }
        compilationClassLoader
    }


    private Iterable<JavaFileObject> collectSources() {
        this.class.declaredFields
                .collect { field ->
                    field.getAnnotation(SourceCode)?.with { annotation ->
                        field.accessible = true
                        JavaFileObjects.forSourceString(value(), (String) field.get(this))
                    }
                }
                .findAll()
    }


    Class<?> generatedClass(String qualifiedName) {
        return Class.forName(qualifiedName, true, getCompilationClassLoader())
    }


    Class<?> assumeClassPresent(String qualifiedName) {
        try {
            return generatedClass(qualifiedName)
        } catch (ClassNotFoundException e) {
            Assume.assumeNoException("Class was assumed to be present but not found", e)
            null
        }
    }


    boolean classPresent(String qualifiedName) {
        try {
            generatedClass(qualifiedName)
            return true
        } catch (ClassNotFoundException ignored) {
            return false
        }
    }
}
