package org.accept4j.testpack

/**
 * Copyright: Daniel Kelleher Date: 15.12.12 Time: 09:42
 */
@Immutable
class ExecutionData {
    enum Status { PASS, FAIL, ERROR }

    Status status
}
