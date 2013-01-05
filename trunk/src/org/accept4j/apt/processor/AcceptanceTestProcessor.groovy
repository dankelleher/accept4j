package org.accept4j.apt.processor

import groovy.util.logging.Log4j
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.specification.SpecGeneratorFactory
import org.accept4j.specification.generator.SpecGenerator

@SupportedAnnotationTypes("org.accept4j.annotation.AcceptanceTest")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@Log4j
class AcceptanceTestProcessor extends AbstractProcessor {
    static AnnotationVisitor visitor = new AnnotationVisitor()

    static final String PATH = "accept4j"
    static final String XML_FILE = "$PATH/test.xml"
    static final String HTML_FILE = "$PATH/test.html"

    private SpecGenerator specGenerator

    AcceptanceTestProcessor() {
        explodeJarResources()
    }

    @Override
    boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateSpec()
        generateXML(annotations, roundEnv)
        SpecFormatConverter.convertToHTML()
        postProcess()

        return true;
    }

    void postProcess() {
        specGenerator.postProcess()
    }

    protected void generateSpec() {
        specGenerator = new SpecGeneratorFactory().generator
        specGenerator.generate()
    }

    protected void generateXML(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        AcceptanceTestSuite suite = new AcceptanceTestSuite()

        for (TypeElement typeElement in annotations) {
            roundEnv.getElementsAnnotatedWith(typeElement)*.accept(visitor, suite)
        }

        if (suite.isEmpty()) {
            return // in second round
        }

        suite.compareToSpec()
        suite.recursiveSort()

        new File(XML_FILE).write suite.toXML()
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
