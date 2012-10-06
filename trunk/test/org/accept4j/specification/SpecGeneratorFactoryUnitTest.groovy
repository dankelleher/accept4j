package org.accept4j.specification

import org.junit.Test
import org.accept4j.specification.generator.ExcelSpecGenerator
import org.junit.After
import org.junit.Before

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
    void testProduceCorrectFactory() {
        // no spec files
        shouldFail {
            factory.generator
        }

        specFiles.xsl.createNewFile()

        assert factory.generator instanceof ExcelSpecGenerator

        specFiles.xml.createNewFile()

        assert !(factory.generator instanceof ExcelSpecGenerator)
    }
    
    @After void tearDown() {
        specFiles.values()*.delete()
    } 
}