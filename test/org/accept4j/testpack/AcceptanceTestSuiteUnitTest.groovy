package org.accept4j.testpack

import org.junit.Before
import org.junit.Test
import groovy.xml.MarkupBuilder
import org.junit.After
import static org.accept4j.testpack.AcceptanceTestUnitUtils.makePackWithTests
import static org.accept4j.testpack.AcceptanceTestUnitUtils.makePackWithTests
import static org.accept4j.testpack.AcceptanceTestUnitUtils.makePackWithTests

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

        def existingTest = new AcceptanceTestItem(id:"1.1", methodName:"TestMethod")

        AcceptanceTestSuite suite = makeSuite(existingTest)

        suite.compareToSpec()

        assert suite.name == "dummy project"
        assert suite.datetime != null

        def missingTest = suite.groups.iterator()[0].testPacks.iterator()[0].tests.iterator()[1]
        assert existingTest.methodName
        assert existingTest.name
        assert existingTest.description
        assert !missingTest.methodName
        assert missingTest.id == "1.2"
        assert missingTest.name == "Some Other Test"
    }
    
    @Test void testSearchForTestIfSpecContainsNoPackOrGroup() {
        makeSparseSpecXML()

        def existingTest = new AcceptanceTestItem(id:"1.1", methodName:"TestMethod")

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
                        testPacks: [new AcceptanceTestPack(
                                name: "dummy pack",
                                tests: [existingTest] as SortedSet<AcceptanceTestItem>)]
                )]
        )
        return suite
    }

    @Test void addingGroupsOrdersThemByTheIdsOfTheFirstTests() {
        def packA = makePackWithTests("3.1")
        def packB = makePackWithTests("1.1", "1.2", "1.3")
        def packC = makePackWithTests("2.1", "2.2")

        def group1 = new AcceptanceTestPackGroup()
        group1 << packA

        def group2 = new AcceptanceTestPackGroup()
        group2 << packB << packC

        suite << group1 << group2

        assert suite.groups.toList() == [group2, group1]
    }
    
    @Test void recursiveSortingSuiteSortsAllLevels() {
        def packA = makePackWithTests("1.2")
        def packB = makePackWithTests("1.3")
        
        def group1 = new AcceptanceTestPackGroup()
        group1 << packA << packB // packA will be before packB as 1.2 < 1.3
        assert group1.testPacks.iterator()[0] == packA
        
        packB << new AcceptanceTestItem(id:"1.1")
        // packB should now be before packA in group1 as 1.1 < 1.2,
        // however group1 has not been resorted, so packA is still first
        assert group1.testPacks.iterator()[0] == packA
        
        def group2 = new AcceptanceTestPackGroup()
        suite << group1 << group2
        // group2 will be before group1 as it is empty
        assert suite.groups.iterator()[0] == group2

        def packC = makePackWithTests("1.4")
        group2 << packC
        // group1 should now be before group2 as 1.4 > 1.1
        // however, the suite has not been resorted, so group2 is still first
        assert suite.groups.iterator()[0] == group2

        assert suite.groups*.testPacks*.tests*.id.flatten() == ["1.4", "1.2", "1.1", "1.3"]

        suite.recursiveSort()

        assert suite.groups*.testPacks*.tests*.id.flatten() == ["1.1", "1.3", "1.2", "1.4"] // 1.1 and 1.3 are in the same pack
    }

    @After void tearDown() {
        new File("spec.xml").delete()
    }
}
