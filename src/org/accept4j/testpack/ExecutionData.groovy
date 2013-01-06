package org.accept4j.testpack

import static java.lang.Enum.valueOf

/**
 * Copyright: Daniel Kelleher Date: 15.12.12 Time: 09:42
 */
@Immutable
class ExecutionData {
    enum Status { PASS, FAIL, ERROR }

    Status status

    static ExecutionData buildFromXML(def xml) {
        def status = xml.status.text() as ExecutionData.Status
        return new ExecutionData(status: status)
    }
}
