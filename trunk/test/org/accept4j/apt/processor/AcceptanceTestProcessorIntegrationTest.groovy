package org.accept4j.apt.processor

import org.junit.Test
import org.junit.Before
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

import static org.mockito.Mockito.*
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Element
import org.accept4j.annotation.TestPack
import org.mockito.stubbing.Answer
import org.accept4j.annotation.AcceptanceTest
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import javax.lang.model.element.Name
import org.junit.AfterClass
import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.testpack.AcceptanceTestPackGroup
import org.accept4j.testpack.AcceptanceTestPack
import org.accept4j.testpack.AcceptanceTestItem
import groovy.xml.MarkupBuilder
import org.junit.After

/**
 * User: Daniel Date: 23.09.12 Time: 18:17
 */
class AcceptanceTestProcessorIntegrationTest {

    AcceptanceTestProcessor processor

    RoundEnvironment roundEnv
    Set<TypeElement> testSet

    @Before
    void setUp() {
        processor = new AcceptanceTestProcessor()

        def testAnnotation = mock TypeElement
        testSet = [testAnnotation] as Set<TypeElement>

        def packAnnotation = mock TestPack
        when packAnnotation.name() thenReturn "dummy pack"
        when packAnnotation.group() thenReturn "dummy group"

        def testClass = mock Element
        when testClass.getAnnotation(TestPack) thenReturn packAnnotation

        ExecutableElement testMethod = makeDummyTestMethod(testClass)
        def annotatedMethods = [testMethod] as Set<Element>

        roundEnv = mock RoundEnvironment
        when roundEnv.getElementsAnnotatedWith(testAnnotation) thenReturn annotatedMethods
    }

    private def makeDummyTestMethod(Element testClass) {
        def dummyName = mock Name
        when dummyName.toString() thenReturn "TestMethod"
        
        def testMethod = mock ExecutableElement
        when testMethod.enclosingElement thenReturn testClass
        when testMethod.simpleName thenReturn dummyName

        def answer = { invocation ->
            def suite = invocation.arguments[1];
            def mock = invocation.mock;

            processor.visitor.visitExecutableAsMethod(mock, suite)
        } as Answer<Void>
        when testMethod.accept(any(), any()) thenAnswer answer

        def testAnnotation = mock AcceptanceTest
        when testAnnotation.id() thenReturn "1.1"
        when testMethod.getAnnotation(AcceptanceTest) thenReturn testAnnotation

        return testMethod
    }

    @Test
    void testProcessorGeneratesXML() {
        processor.generateXML(testSet, roundEnv)

        def suite = new XmlSlurper().parse new File("${AcceptanceTestProcessor.PATH}/test.xml")
        assert suite.group.pack.test.'@id' == "1.1"
        assert suite.group.pack.test.'@methodName' == "TestMethod"
        assert suite.group.pack.'@name' == "dummy pack"
        assert suite.group.'@name' == "dummy group"
    }

    @Test void testHTMLGeneratedFromXML() {
        new File("${AcceptanceTestProcessor.PATH}/test.xml").withWriter { writer ->
            def spec = new MarkupBuilder(writer)
            spec.suite {
                group(name: "dummy group") {
                    pack(name: "dummy pack") {
                        test(id: "1.1", name: "TestMethod")
                    }
                }
            }
        }

        processor.convertToHTML()

        def html = new File("${AcceptanceTestProcessor.PATH}/test.html").text
        assert html =~ /1.1/
        assert html =~ /TestMethod/
    }
    
    @After void tearDown() {
        new File("${AcceptanceTestProcessor.PATH}/resources").deleteDir()
        new File(AcceptanceTestProcessor.PATH).deleteDir()
        new File("spec.xml").delete()
    }
}
