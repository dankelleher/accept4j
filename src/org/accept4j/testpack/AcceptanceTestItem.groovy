package org.accept4j.testpack

/**
 * User: Daniel Date: 23.09.12 Time: 21:18
 */
class AcceptanceTestItem {
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
}
