package org.unbrokendome.siren.ap.codegeneration;

public interface CodeGenerationContext {

    CodeGenerationContext DEFAULT = new CodeGenerationContext() { };

    CodeGenerationContext EXPLICIT_REQUEST = new CodeGenerationContext() { };
}
