package org.accept4j.testpack

import org.junit.Before
import org.junit.Test

import static AcceptanceTestUnitUtils.*

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 22:49
 */
class AcceptanceTestPackUnitTest {
    AcceptanceTestPack pack

    @Before void setUp() {
        pack = new AcceptanceTestPack();
    }

    @Test void emptyPackShouldReturnNullWhenLookingForTest() {
        assert pack.find(id: "anytest") == null
    }

    @Test void findWithArgsReturnTheTest() {
        def test1 = new AcceptanceTestItem(id: "test1", methodName: "method1", name: "name1")
        def test2 = new AcceptanceTestItem(id: "test2", methodName: "method2", name: "name2")
        pack << test1 << test2

        assert pack.find([id: "test1"]) == test1
        assert pack.find([methodName: "method1"]) == test1
        assert pack.find([id: "test1", methodName: "method1"]) == test1
        assert pack.find([id: "test1", methodName: "method2"]) == null
    }
    
    @Test void idOrderMaintainedWhenAdding() {
        pack << new AcceptanceTestItem(id: "3.2")
        pack << new AcceptanceTestItem(id: "3.1")
        assert pack.tests*.id == ["3.1", "3.2"]
        
        pack << new AcceptanceTestItem(id: "3.3")
        assert pack.tests*.id == ["3.1", "3.2", "3.3"]

        pack << new AcceptanceTestItem(id: "2.7")
        assert pack.tests*.id == ["2.7", "3.1", "3.2", "3.3"]

        pack << new AcceptanceTestItem(id: "Test 2.0")
        assert pack.tests*.id == ["2.7", "3.1", "3.2", "3.3", "Test 2.0"]

        pack << new AcceptanceTestItem(id: "Test 1.0")
        assert pack.tests*.id == ["2.7", "3.1", "3.2", "3.3", "Test 1.0", "Test 2.0"]
    }
    
    @Test void aPackWithNoTestsIsBeforeAnyOtherPackWhenComparing() {
        pack << new AcceptanceTestItem(id: "3.2")

        def emptyPack = new AcceptanceTestPack()
        assert emptyPack < pack
        assert emptyPack <=> emptyPack == 0
        assert pack > emptyPack               
    }
    
    @Test void comparingPacksShouldCompareTheFirstTests() {
        def pack1 = makePackWithTests("1.2", "1.3")
        def pack2 = makePackWithTests("2.1", "1.1") // second test is before first test in pack1. This pack goes first because 1.1 will be first in pack2

        assert pack1 > pack2
        assert pack2 < pack1
    }
}
