package org.accept4j.execution

import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.testpack.ExecutionData
import org.accept4j.apt.processor.AcceptanceTestProcessor
import org.accept4j.apt.processor.SpecFormatConverter
import groovy.util.logging.Log4j

/**
 * Copyright: Daniel Kelleher Date: 14.12.12 Time: 17:42
 */
@Log4j
class TestExecutorImpl implements TestExecutor {
    AcceptanceTestSuite suite

    void start() {
        suite = AcceptanceTestSuite.loadFromXML(AcceptanceTestProcessor.XML_FILE)
        suite.recursiveSort()
    }

    // Note - this does not support multiple tests with the same method name yet
    void testRunEvent(String testName, ExecutionData executionData) {
        log.debug "Run test: $testName status: ${executionData.status}"
        def test = suite.find(methodName:testName)
        
        if (test) test.executionData = executionData
    }

    void complete() {
        log.debug "Tests complete"
        log.debug suite.toXML()
        new File(AcceptanceTestProcessor.XML_FILE).write suite.toXML()
        SpecFormatConverter.convertToHTML()
    }
}
