package org.accept4j.execution

import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.testpack.ExecutionData
import org.accept4j.apt.processor.AcceptanceTestProcessor
import org.accept4j.apt.processor.SpecFormatConverter

/**
 * Copyright: Daniel Kelleher Date: 14.12.12 Time: 17:42
 */
class TestExecutorImpl implements TestExecutor {
    AcceptanceTestSuite suite

    TestExecutorImpl() {
        this(AcceptanceTestSuite.loadFromXML(AcceptanceTestProcessor.XML_FILE))
    }

    TestExecutorImpl(AcceptanceTestSuite suite) {
        this.suite = suite
    }

    // Note - this does not support multiple tests with the same method name yet
    void testRunEvent(String testName, ExecutionData executionData) {
        def test = suite.find(methodName:testName)
        
        if (test) test.executionData = executionData
    }

    void complete() {
        new File(AcceptanceTestProcessor.XML_FILE).write suite.toXML()
        SpecFormatConverter.convertToHTML()
    }
}
