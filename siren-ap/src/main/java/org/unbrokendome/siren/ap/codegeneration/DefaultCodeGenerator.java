package org.unbrokendome.siren.ap.codegeneration;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.unbrokendome.siren.ap.codegeneration.type.TypeSpecContributor;
import org.unbrokendome.siren.ap.RuntimeIOException;
import org.unbrokendome.siren.ap.model.affordance.grouping.AffordanceGroup;
import org.unbrokendome.siren.ap.model.affordance.AffordanceModel;
import org.unbrokendome.siren.ap.model.affordance.QualifiedName;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Filer;
import javax.inject.Inject;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Set;


public class DefaultCodeGenerator implements CodeGenerator {

    private final Filer filer;
    private final Set<TypeSpecContributor> typeSpecContributors;


    @Inject
    public DefaultCodeGenerator(Filer filer, Set<TypeSpecContributor> typeSpecContributors) {
        this.filer = filer;
        this.typeSpecContributors = typeSpecContributors;
    }


    @Override
    public void generateCode(AffordanceModel affordanceModel) {

        for (AffordanceGroup group : affordanceModel.getGroups()) {
            try {
                JavaFile javaFile = generateJavaFileForGroup(group);
                if (javaFile != null) {
                    javaFile.writeTo(filer);
                }
            } catch (IOException e) {
                throw new RuntimeIOException(e);
            }
        }
    }


    @Nullable
    private JavaFile generateJavaFileForGroup(AffordanceGroup group) {

        QualifiedName qualifiedName = group.getQualifiedName();

        TypeSpec typeSpec = generateTypeSpecForGroup(group, qualifiedName);

        if (!typeSpec.methodSpecs.isEmpty()) {
            return JavaFile.builder(qualifiedName.getPackageName(), typeSpec)
                    .build();
        } else {
            return null;
        }
    }


    @Nonnull
    private TypeSpec generateTypeSpecForGroup(AffordanceGroup group, QualifiedName qualifiedName) {
        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(qualifiedName.getSimpleName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(createGeneratedAnnotation());

        MethodSpec privateConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
        typeSpec.addMethod(privateConstructor);

        typeSpecContributors.stream()
                .filter(c -> c.appliesTo(group))
                .forEach(c -> c.contribute(group, typeSpec));

        return typeSpec.build();
    }


    private AnnotationSpec createGeneratedAnnotation() {
        return AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", getClass().getName())
                .build();
    }
}
