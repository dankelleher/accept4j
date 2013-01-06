package org.accept4j.execution

import org.junit.Test
import org.junit.Before
import org.accept4j.testpack.AcceptanceTestItem
import org.accept4j.testpack.AcceptanceTestPack
import org.accept4j.testpack.ExecutionData
import org.junit.After
import org.accept4j.apt.processor.AcceptanceTestProcessor
import groovy.xml.MarkupBuilder
import org.accept4j.testpack.AcceptanceTestSuite

/**
 * Copyright: Daniel Kelleher Date: 15.12.12 Time: 10:03
 */
class TestExecutorUnitTest {
    static final AcceptanceTestItem SAMPLE_TEST = new AcceptanceTestItem(id: "someId", methodName: "someTest")
    
    TestExecutorImpl executor

    @Before void setUp() {
        new File(AcceptanceTestProcessor.PATH).mkdir()

        executor = new TestExecutorImpl(suite: new AcceptanceTestSuite())
        pack.add(SAMPLE_TEST)
    }
    
    @Test void aPassedTestShouldBeIndicatedOnTheSuite() {
        def executionData = new ExecutionData(status: ExecutionData.Status.PASS)
        executor.testRunEvent(SAMPLE_TEST.methodName, executionData)
        
        assert SAMPLE_TEST.executionData == executionData
    }
    
    @Test void executorShouldLoadSuiteFromXML() {
        executor = makeExecutorFromXML()
        assert executor.suite.find(id: "1.1")
    }

    private TestExecutorImpl makeExecutorFromXML() {
        new File(AcceptanceTestProcessor.XML_FILE).withWriter { writer ->
            def spec = new MarkupBuilder(writer)
            spec.suite {
                group(name: "dummy group") {
                    pack(name: "dummy pack") {
                        test(id: "1.1", name: "test method", methodName: "testMethod")
                    }
                }
            }
        }

        TestExecutor executor = new TestExecutorImpl()
        executor.start()
        return executor
    }

    @Test void completingTestExecutionGeneratesHTMLReport() {
        executor = makeExecutorFromXML()
        executor.testRunEvent("testMethod", new ExecutionData(status: ExecutionData.Status.PASS))
        
        executor.complete()
        
        assert new File(AcceptanceTestProcessor.HTML_FILE).exists()
    }

    @Test void generatedHTMLReportShouldContainExecutionInformation() {
        executor = makeExecutorFromXML()

        executor.testRunEvent("testMethod", new ExecutionData(status: ExecutionData.Status.PASS))
        executor.complete()
        assertHTMLContainsStatusClass("testPass")

        executor.testRunEvent("testMethod", new ExecutionData(status: ExecutionData.Status.FAIL))
        executor.complete()
        assertHTMLContainsStatusClass("testFail")

        executor.testRunEvent("testMethod", new ExecutionData(status: ExecutionData.Status.ERROR))
        executor.complete()
        assertHTMLContainsStatusClass("testError")
    }

    private assertHTMLContainsStatusClass(String statusHTMLClass) {
        def html = new File(AcceptanceTestProcessor.HTML_FILE).text
        assert html =~ /class="$statusHTMLClass"/
    }

    @After void tearDown() {
        new File(AcceptanceTestProcessor.PATH).deleteDir()
    }
    
    AcceptanceTestPack getPack() {
        return executor.suite.findOrCreate("").findOrCreate("")
    }
}
