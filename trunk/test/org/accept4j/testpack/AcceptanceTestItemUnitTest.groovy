package org.accept4j.testpack

import org.junit.Test
import groovy.xml.MarkupBuilder

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
    
    @Test void aTestWithNoExecutionDataShouldNotIncludeItInXML() {
        def stringWriter = new StringWriter()
        def builder = new MarkupBuilder(stringWriter)

        new AcceptanceTestItem(id : "hello").toXML(builder)

        assert !stringWriter.toString().contains("<executionData>")
    }

    @Test void aTestWithExecutionDataShouldIncludeItInXML() {
        def stringWriter = new StringWriter()
        def builder = new MarkupBuilder(stringWriter)

        new AcceptanceTestItem(id : "hello", executionData: new ExecutionData(status: ExecutionData.Status.PASS)).toXML(builder)

        def xml = stringWriter.toString().replaceAll("\\s+", "")

        assert xml.contains("<executionData>")
        assert xml.contains("<status>PASS</status>")
    }
    
    @Test void testMatchesArguments() {
        def test = new AcceptanceTestItem(id: "a", methodName: "b", name: "c", executionData: new ExecutionData(status: ExecutionData.Status.PASS))
        
        assert test.matches(id: "a")
        assert test.matches(id: "a", methodName: "b")
        assert !test.matches(id: "1", methodName: "b")
        assert test.matches(executionData: new ExecutionData(status: ExecutionData.Status.PASS))
        assert !test.matches(executionData: new ExecutionData(status: ExecutionData.Status.FAIL))
    }
}
