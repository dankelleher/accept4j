package org.accept4j.testpack

import org.junit.Before
import org.junit.Test
import groovy.xml.MarkupBuilder
import org.junit.After

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:45
 */
class AcceptanceTestSuiteUnitTest {
    AcceptanceTestSuite suite
    
    @Before void setUp() {
        suite = new AcceptanceTestSuite();
    }
    
    @Test void findInEmptySuiteShouldReturnNewGroup() {
        def group = suite.findOrCreate("anygroup")
        assert group.name == "anygroup"
    }
    
    @Test void findInSuiteShouldReturnThePack() {
        def group = new AcceptanceTestPackGroup(name: "tmp")
        suite.add(group)
        
        assert suite.findOrCreate("tmp") == group
    }

    @Test void testSuiteComparedToSpecifications() {
        makeSpecXML()

        def existingTest = [
                id: "1.1",
                methodName: "TestMethod"
        ] as AcceptanceTestItem

        AcceptanceTestSuite suite = makeSuite(existingTest)

        suite.compareToSpec()

        assert suite.name == "dummy project"
        assert suite.datetime != null

        def missingTest = suite.groups[0].testPacks[0].tests[1]
        assert existingTest.methodName
        assert existingTest.name
        assert existingTest.description
        assert !missingTest.methodName
        assert missingTest.id == "1.2"
        assert missingTest.name == "Some Other Test"
    }
    
    @Test void testSearchForTestIfSpecContainsNoPackOrGroup() {
        makeSparseSpecXML()

        def existingTest = [
                id: "1.1",
                methodName: "TestMethod"
        ] as AcceptanceTestItem

        AcceptanceTestSuite suite = makeSuite(existingTest)

        suite.compareToSpec()
        assert existingTest.name
    }

    private makeSpecXML() {
        new File("spec.xml").withWriter { writer ->
            def spec = new MarkupBuilder(writer)
            spec.suite(name: "dummy project") {
                group(name: "dummy group") {
                    pack(name: "dummy pack") {
                        test(id: "1.1", name: "Test 1") {
                            description("A test to test a thing")
                        }
                        test(id: "1.2", name: "Some Other Test") {
                            description("Some other test to test some other thing")
                        }
                    }
                }
            }
        }
    }

    private makeSparseSpecXML() {
        new File("spec.xml").withWriter { writer ->
            def spec = new MarkupBuilder(writer)
            spec.suite(name: "") {
                group(name: "") {
                    pack(name: "") {
                        test(id: "1.1", name: "Test 1") {
                            description("A test to test a thing")
                        }
                    }
                }
            }
        }
    }

    private AcceptanceTestSuite makeSuite(AcceptanceTestItem existingTest) {
        def suite = new AcceptanceTestSuite(
                groups: [new AcceptanceTestPackGroup(
                        name: "dummy group",
                        testPacks: [[
                                name: "dummy pack",
                                tests: [existingTest]
                        ] as AcceptanceTestPack]
                )]
        )
        return suite
    }

    @After void tearDown() {
        new File("spec.xml").delete()
    }
}
