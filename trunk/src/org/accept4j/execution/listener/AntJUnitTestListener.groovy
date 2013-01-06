package org.accept4j.execution.listener

import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest
import org.accept4j.execution.TestExecutor
import junit.framework.*
import org.apache.tools.ant.taskdefs.optional.junit.JUnitVersionHelper
import org.accept4j.testpack.ExecutionData
import org.accept4j.execution.TestExecutorImpl

/**
 * Copyright: Daniel Kelleher Date: 15.12.12 Time: 19:10
 */
class AntJUnitTestListener implements JUnitResultFormatter {
    TestExecutor executor

    AntJUnitTestListener() {
        executor = new TestExecutorImpl()
    }

    AntJUnitTestListener(TestExecutor executor) {
        this.executor = executor
    }

    void startTestSuite(JUnitTest jUnitTest) {
        executor.start()
    }

    void endTestSuite(JUnitTest jUnitTest) {
        executor.complete()
    }

    void addError(Test test, Throwable throwable) {
        sendEvent(test, ExecutionData.Status.ERROR)
    }

    void addFailure(Test test, AssertionFailedError assertionFailedError) {
        sendEvent(test, ExecutionData.Status.FAIL)
    }

    void startTest(Test test) {
        sendEvent(test, ExecutionData.Status.PASS)
    }

    private sendEvent(Test test, ExecutionData.Status status) {
        String name = JUnitVersionHelper.getTestCaseName(test);
        executor.testRunEvent(name, new ExecutionData(status: status))
    }

    void endTest(Test test) {}
    void setOutput(OutputStream outputStream) {}
    void setSystemOutput(String s) {}
    void setSystemError(String s) {}
}
