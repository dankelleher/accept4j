package org.accept4j.specification

import org.junit.Test
import org.accept4j.specification.generator.ExcelSpecGenerator
import org.junit.After
import org.junit.Before
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import org.accept4j.specification.generator.SpecGenerator

/**
 * Copyright: Daniel Kelleher Date: 01.10.12 Time: 20:53
 */
class SpecGeneratorFactoryUnitTest extends GroovyTestCase {
    
    SpecGeneratorFactory factory

    def specFiles = [
            xml : new File("spec.xml"),
            xsl : new File("spec.xls")
    ]
    
    @Before void setUp() {
        factory = new SpecGeneratorFactory()
    }

    @Test
    void testProduceCorrectGenerator() {
        // no spec files
        shouldFail {
            factory.generator
        }

        specFiles.xsl.createNewFile()

        assert factory.generator instanceof ExcelSpecGenerator

        specFiles.xml.createNewFile()

        assert !(factory.generator instanceof ExcelSpecGenerator)
    }
    
    @Test testFindExtensionGenerator() {
        def stubExtensionGenerator = mock SpecGenerator
        def stubServiceLoader = mock ServiceLoader
        def generatorIt = [stubExtensionGenerator].iterator()
        
        when stubServiceLoader.iterator() thenReturn generatorIt
        
        factory = new SpecGeneratorFactory(stubServiceLoader)
        
        assert factory.generator == stubExtensionGenerator
    }
    
    @After void tearDown() {
        specFiles.values()*.delete()
    } 
}