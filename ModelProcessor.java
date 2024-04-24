
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("GenerateImpl")
public class ModelProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {

            for (var annotation : annotations) {
                var annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
                for (var annotated : annotatedElements) {
                    JavaFileObject object = processingEnv.getFiler()
                            .createSourceFile(annotated.getSimpleName() + "Impl");
                    PrintWriter out = new PrintWriter(object.openWriter());
                    out.println("""
                    public class %sImpl implements %s {
                        public void testMethod() {
                            System.out.println("Hello world");
                        }
                    }""".formatted(annotated.getSimpleName(), annotated.getSimpleName()));
                    out.close();
                }
            }
        } catch (IOException ex) {
        }
        return false;
    }

}