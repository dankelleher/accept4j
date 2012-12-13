package org.accept4j.testpack

import org.junit.Test

/**
 * Copyright: Daniel Kelleher Date: 09.12.12 Time: 15:43
 */
class AcceptanceTestItemUnitTest {
    
    @Test void comparingTestsShouldCompareIds() {
        assert new AcceptanceTestItem(id: "hell0") < new AcceptanceTestItem(id: "hello")
        assert new AcceptanceTestItem(id: "hell") < new AcceptanceTestItem(id: "hello")
        assert new AcceptanceTestItem(id: "hello") <=> new AcceptanceTestItem(id: "hello") == 0
        assert new AcceptanceTestItem(id: "help") > new AcceptanceTestItem(id: "hello")
    }
    
    @Test void aTestWithAnEmptyIdIsBeforeAnyOtherTestWhenComparing() {
        assert new AcceptanceTestItem() < new AcceptanceTestItem(id: "hello")
        assert new AcceptanceTestItem() < new AcceptanceTestItem(id: "")
        assert new AcceptanceTestItem() <=> new AcceptanceTestItem() == 0

        assert new AcceptanceTestItem(id: "")> new AcceptanceTestItem()
    }
    
}
