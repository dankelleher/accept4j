package org.accept4j.apt.processor

import javax.lang.model.element.Name
import javax.lang.model.element.TypeElement
import org.accept4j.annotation.AcceptanceTest
import org.accept4j.annotation.TestPack
import org.junit.Before
import org.junit.Test
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import javax.lang.model.element.ExecutableElement
import org.accept4j.testpack.AcceptanceTestSuite

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 18:18
 */
class AnnotationVisitorUnitTest {
    AnnotationVisitor visitor
    AcceptanceTestSuite suite
    
    @Before void setUp() {
        visitor = new AnnotationVisitor()
        suite = new AcceptanceTestSuite()
    }

    @Test void testVisitAMethodOutputsIdAndMethodName() {
        def dummyElement = executableElement

        visitor.visitExecutableAsMethod(dummyElement, suite)
        
        assert suite.groups[0].testPacks[0].find("a test") != null
    }
    
    private ExecutableElement getExecutableElement() {
        def acceptanceTest = acceptanceTest
        def typeElement = typeElement
        def name = name
        
        def dummyElement = mock ExecutableElement
        when dummyElement.getAnnotation(AcceptanceTest.class) thenReturn acceptanceTest
        when dummyElement.enclosingElement thenReturn typeElement
        when dummyElement.simpleName thenReturn name

        return dummyElement
    }

    private AcceptanceTest getAcceptanceTest() {
        def acceptanceTestAnnotation = mock AcceptanceTest
        when acceptanceTestAnnotation.id() thenReturn "a test"
        return acceptanceTestAnnotation
    }

    private TypeElement getTypeElement() {
        def name = name
        def testPack = testPack

        def dummyElement = mock TypeElement
        when dummyElement.getAnnotation(TestPack.class) thenReturn testPack
        when dummyElement.qualifiedName thenReturn name
        return dummyElement
    }

    def getName() {
        when mock(Name).toString() thenReturn "dummy name" getMock()
    }

    def getTestPack() {
        def testPackAnnotation = mock TestPack
        when testPackAnnotation.name() thenReturn "a pack"
        when testPackAnnotation.group() thenReturn "a group"

        return testPackAnnotation
    }
}
