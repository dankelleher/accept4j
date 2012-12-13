package org.accept4j.testpack

import org.junit.Test
import org.junit.Before

import static AcceptanceTestUnitUtils.*

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:45
 */
class AcceptanceTestPackGroupUnitTest {
    AcceptanceTestPackGroup group
    
    @Before void setUp() {
        group = new AcceptanceTestPackGroup()
    }
    
    @Test void emptyGroupShouldReturnNullWhenLookingForPack() {
        def pack = group.findOrCreate("anypack")
        assert pack.name == "anypack"
    }
    
    @Test void findInGroupShouldReturnThePack() {
        def pack = new AcceptanceTestPack(name: "tmp")
        group.add(pack)
        
        assert group.findOrCreate("tmp") == pack
    }
    
    @Test void idOrderOfFirstTestsMaintainedWhenAdding() {
        def pack1 = makePackWithTests("1.2", "1.3")
        def pack2 = makePackWithTests("2.1", "1.1") // second test is before first test in pack1. This pack goes first because 1.1 will be first in pack2

        group << pack1
        group << pack2

        assert group.testPacks.toList() == [pack2, pack1]
    }

    @Test void aGroupWithNoPacksIsBeforeAnyOtherPackWhenComparing() {
        group << makePackWithTests("1.1")
        AcceptanceTestPackGroup emptyGroup = new AcceptanceTestPackGroup() 

        assert emptyGroup < group
        assert emptyGroup <=> emptyGroup == 0
        assert group > emptyGroup
    }

    @Test void comparingGroupsShouldCompareTheFirstPacks() {
        def packA = makePackWithTests("1.1")
        def packB = makePackWithTests("1.2")
        def packC = makePackWithTests("1.3")
        def packD = makePackWithTests("1.4")

        AcceptanceTestPackGroup other = new AcceptanceTestPackGroup()

        group << packA << packD
        other << packB << packC

        assert group < other
        assert other > group
    }
}

