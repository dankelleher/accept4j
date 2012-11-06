package org.accept4j.specification.generator

import org.junit.Test

import static org.junit.Assert.*
import org.junit.Before
import org.accept4j.specification.SpecParseException
import org.junit.After

/**
 * Copyright: Daniel Kelleher Date: 12.10.12 Time: 18:25
 */
class DirectorySpecGeneratorUnitTest {
    
    def specFile = new File("spec.xml")
    def generator
    
    @Before void setUp() {
        generator = new DirectorySpecGenerator()
    }
    
    @After void tearDown() {
        new File(DirectorySpecGenerator.PATH).deleteDir()
        specFile.delete()
    }

    @Test(expected = SpecParseException)
    void testThrowErrorIfNoSpecDirectoryExists() {
        generator.generate()
    }
    
    @Test void testEmptySpec() {
        createEmptySuite()

        generator.generate()

        def suite = new XmlSlurper().parse specFile
        assert suite.group.size() == 0
    }
    
    @Test void testGenerateSpecFromFiles() {
        createSuiteWithTests()

        generator.generate()

        def suite = new XmlSlurper().parse specFile
        assert suite.@name == "suite"
        assert suite.group.pack.test.size() == 3
        assert suite.group.pack.test*.@id == ["1.1", "1.2", "1.3"]
        assert suite.group.pack.test*.@name == ["name with spaces", "name_with_underscores", "name without extension"]
    }

    void createEmptySuite() {
        new File(DirectorySpecGenerator.PATH).mkdir()
    }

    void createSuiteWithTests() {
        createEmptySuite()
        new File("${DirectorySpecGenerator.PATH}/suite").mkdir()
        new File("${DirectorySpecGenerator.PATH}/suite/dummy group").mkdir()
        new File("${DirectorySpecGenerator.PATH}/suite/dummy group/dummy pack").mkdir()
        new File("${DirectorySpecGenerator.PATH}/suite/dummy group/dummy pack/1.1_name with spaces.doc").createNewFile()
        new File("${DirectorySpecGenerator.PATH}/suite/dummy group/dummy pack/1.2_name_with_underscores.doc").createNewFile()
        new File("${DirectorySpecGenerator.PATH}/suite/dummy group/dummy pack/1.3_name without extension").createNewFile()
    }
}
