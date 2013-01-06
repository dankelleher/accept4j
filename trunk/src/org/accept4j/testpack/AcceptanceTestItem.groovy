package org.accept4j.testpack

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:18
 */
class AcceptanceTestItem implements Comparable<AcceptanceTestItem> {
    String id
    String name
    String methodName
    String description
    ExecutionData executionData

    protected void toXML(def builder) {
        builder.test(id:id, name:name, methodName:methodName) {
            description(description)
            if (executionData) {
                executionData {
                    status(executionData.status)
                }
            }
        }
    }

    void addXMLDetails(def testXML) {
        name = testXML.@name.text()
        description = testXML.description.text()

        if (testXML.@methodName != "") methodName = testXML.@methodName.text()
        if (testXML.executionData != "") executionData = ExecutionData.buildFromXML(testXML.executionData)
    }

    @Override
    int compareTo(AcceptanceTestItem o) {
        if (id == null) {
            if (o.id == null) return 0
            return -1
        }
        if (o.id == null) return 1

        return id.compareTo(o.id)
    }

    boolean matches(args) {
        for (el in args) {
            if (this[el.key] != el.value) {
                return false
            }
        }

        return true
    }
}
