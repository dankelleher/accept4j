package org.accept4j.execution.listener

import org.junit.Test
import org.junit.Before
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import org.accept4j.execution.TestExecutor
import junit.framework.TestCase
import static org.mockito.Mockito.when
import org.accept4j.testpack.ExecutionData

/**
 * Copyright: Daniel Kelleher Date: 15.12.12 Time: 19:24
 */
class AntJUnitTestListenerUnitTest {
    static final String DUMMY_TEST_NAME = "dummyTest"
    
    AntJUnitTestListener listener
    TestExecutor mockExecutor
    TestCase stubTest
    
    @Before void setUp() {
        mockExecutor = mock TestExecutor
        listener = new AntJUnitTestListener(mockExecutor)

        stubTest = new TestCase(DUMMY_TEST_NAME){}
    }
    
    @Test void endingTheTestSuiteShouldCallCompleteOnTheExecutor() {
        listener.endTestSuite(null)
        
        verify(mockExecutor).complete()
    }
    
    @Test void aPassingTestShouldSendAPassToTheExecutor() {
        listener.endTest(stubTest)

        verify(mockExecutor).testRunEvent(DUMMY_TEST_NAME, new ExecutionData(status: ExecutionData.Status.PASS))
    }

    @Test void aFailingTestShouldSendAFailToTheExecutor() {
        listener.addFailure(stubTest, null)

        verify(mockExecutor).testRunEvent(DUMMY_TEST_NAME, new ExecutionData(status: ExecutionData.Status.FAIL))
    }

    @Test void aCrashingTestShouldSendAnErrorToTheExecutor() {
        listener.addError(stubTest, null)

        verify(mockExecutor).testRunEvent(DUMMY_TEST_NAME, new ExecutionData(status: ExecutionData.Status.ERROR))
    }
}
