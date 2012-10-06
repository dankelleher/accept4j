package org.accept4j.specification.generator

import org.junit.Before
import org.junit.Test
import org.accept4j.specification.SpecParseException
import org.junit.After
import org.accept4j.apt.processor.AcceptanceTestProcessor
import org.junit.BeforeClass
import org.junit.AfterClass

/**
 * Copyright: Daniel Kelleher Date: 04.10.12 Time: 23:13
 */
class ExcelSpecGeneratorUnitTest {
    ExcelSpecGenerator generator
    File spec = new File("spec.xls")
    
    @BeforeClass static void setUpBeforeClass() {
        new File(AcceptanceTestProcessor.PATH).mkdir()
    }

    @Before void setUp() {
        generator = new ExcelSpecGenerator()
    }

    @Test(expected = SpecParseException)
    void testExceptionThrownOnEmptySpec() {
        spec.createNewFile()
        generator.generate()
    }

    @Test(expected = SpecParseException)
    void testExceptionThrownOnInvalidHeaderSpec() {
        spec << new File("testData/invalidHeaderSpec.xls").bytes
        generator.generate()
    }

    @Test void testNumberOfTestsInXMLMatchesSpec() {
        spec << new File("testData/spec.xls").bytes
        generator.generate()

        def suite = new XmlSlurper().parse new File("spec.xml")
        assert suite.group.pack.test.size() == 5
    }

    @Test void tesTestDetailsInXMLMatchesSpec() {
        spec << new File("testData/spec.xls").bytes
        generator.generate()

        def suite = new XmlSlurper().parse new File("spec.xml")
        assert suite.group.pack.test.'@id' == "1.11.21.32.12.2"
        assert suite.group.pack.test.'@name'[0] == "TestMethod"
        assert suite.group.pack.'@name' == "dummy pack"
        assert suite.group.'@name' == "dummy group"
    }
    
    @After void tearDown() {
        new File("spec.xls").delete()
        new File("spec.xml").delete()
    }

    @AfterClass static void tearDownAfterClass() {
        new File(AcceptanceTestProcessor.PATH).delete()
    }
}
