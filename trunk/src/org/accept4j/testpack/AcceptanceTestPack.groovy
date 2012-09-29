package org.accept4j.testpack

/**
 * User: Daniel Date: 23.09.12 Time: 21:17
 */
class AcceptanceTestPack {
    String name
    List<AcceptanceTestItem> tests = new ArrayList<AcceptanceTestItem>();
    
    public void add(AcceptanceTestItem test) {
        tests.add(test)
    }
    
    public AcceptanceTestItem find(String id) {
        return tests.find { it.id == id }
    }

    protected void toXML(def builder) {
        builder.pack(name:name) {
            tests.each { it.toXML(builder)}
        }
    }
}