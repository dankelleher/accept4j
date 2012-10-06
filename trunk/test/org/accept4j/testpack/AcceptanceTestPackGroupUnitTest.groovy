package org.accept4j.testpack

import org.junit.Test
import org.junit.Before

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:45
 */
class AcceptanceTestPackGroupUnitTest {
    AcceptanceTestPackGroup group
    
    @Before void setUp() {
        group = new AcceptanceTestPackGroup();
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
}
