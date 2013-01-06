package org.accept4j.execution

import org.accept4j.testpack.ExecutionData

/**
 * Copyright: Daniel Kelleher Date: 16.12.12 Time: 11:14
 */
public interface TestExecutor {
    void testRunEvent(String testName, ExecutionData executionData)
    void start()
    void complete()
}