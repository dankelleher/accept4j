package org.accept4j.testpack

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:18
 */
class AcceptanceTestItem implements Comparable<AcceptanceTestItem> {
    String id
    String name
    String methodName
    String description

    protected void toXML(def builder) {
        builder.test(id:id, name:name, methodName:methodName) {
            description(description)
        }
    }

    void addSpecDetails(def specTestXML) {
        name = specTestXML.@name.text()
        description = specTestXML.description.text()
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
}
