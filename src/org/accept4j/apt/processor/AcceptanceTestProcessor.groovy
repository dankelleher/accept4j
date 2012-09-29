package org.accept4j.apt.processor

import groovy.util.logging.Log4j
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import org.accept4j.testpack.AcceptanceTestSuite

@SupportedAnnotationTypes("org.accept4j.annotation.AcceptanceTest")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@Log4j
class AcceptanceTestProcessor extends AbstractProcessor {
    static AnnotationVisitor visitor = new AnnotationVisitor()

    static final String PATH = "accept4j"

    AcceptanceTestProcessor() {
        explodeJarResources()
    }

    @Override
    boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateXML(annotations, roundEnv)
        convertToHTML()

        return true;
    }

    protected void generateXML(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        AcceptanceTestSuite suite = new AcceptanceTestSuite()

        for (TypeElement typeElement in annotations) {
            roundEnv.getElementsAnnotatedWith(typeElement)*.accept(visitor, suite)
        }

        if (suite.isEmpty()) {
            return // in second round
        }

        suite.compareToSpec();

        new File("$PATH/test.xml").write suite.toXML()
    }

    protected void convertToHTML() {
        def factory = TransformerFactory.newInstance()
        def transformer = factory.newTransformer(new StreamSource(getClass().getClassLoader().getResourceAsStream("template/template.xsl")))
        transformer.transform(new StreamSource("$PATH/test.xml"), new StreamResult("$PATH/test.html"))
    }

    protected void explodeJarResources() {
        new File(PATH).delete()
        new File(PATH).mkdir()
        new File("$PATH/resources").mkdir()

        // I'd ideally like to have a loop here but the classloader doesn't make this easy with JARs
        extractResource("logo.png")
        extractResource("green.png")
        extractResource("red.png")
        extractResource("alert.png")
        extractResource("favicon.ico")
        extractResource("test.css")
    }

    private void extractResource(String file) {
        def oldName = "resources/$file"
        def newName = "$PATH/resources/$file"
        new File(newName).withOutputStream {stream ->
            stream << getClass().getClassLoader().getResourceAsStream(oldName)
        }
    }
}
