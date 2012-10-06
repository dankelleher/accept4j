package org.accept4j.testpack

import org.junit.Before
import org.junit.Test

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 22:49
 */
class AcceptanceTestPackUnitTest {
    AcceptanceTestPack pack

    @Before void setUp() {
        pack = new AcceptanceTestPack();
    }

    @Test void emptyPackShouldReturnNullWhenLookingForTest() {
        assert pack.find("anytest") == null
    }

    @Test void findInGroupShouldReturnThePack() {
        def test = new AcceptanceTestItem(id: "tmp")
        pack.add(test)

        assert pack.find("tmp") == test
    }
}
