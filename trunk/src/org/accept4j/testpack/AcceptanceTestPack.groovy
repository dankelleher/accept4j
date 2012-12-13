package org.accept4j.testpack

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:17
 */
class AcceptanceTestPack implements Comparable<AcceptanceTestPack> {
    String name
    Set<AcceptanceTestItem> tests = new TreeSet<AcceptanceTestItem>();
    
    public void add(AcceptanceTestItem test) {
        tests.add(test)
    }
    
    public AcceptanceTestPack leftShift(AcceptanceTestItem test) {
        add(test)
        return this
    }
    
    public AcceptanceTestItem find(String id) {
        return tests.find { it.id == id }
    }

    protected void toXML(def builder) {
        builder.pack(name:name) {
            tests.each { it.toXML(builder)}
        }
    }

    private AcceptanceTestItem findTestById(testId) {
        tests.find { it.id == testId }
    }

    @Override
    int compareTo(AcceptanceTestPack o) {
        if (tests.isEmpty()) return -1
        if (o.tests.isEmpty()) return 1
        
        return tests.iterator()[0].compareTo(o.tests.iterator()[0])
    }
    
    void recursiveSort() {
        // not sorting in-place as treeset does not allow re-sorting
        def newTests = new TreeSet<AcceptanceTestItem>()        
        tests.each {
            newTests << it
        }
        //newTests.addAll(tests)  // This doesn't work... I think addAll must optimise if the input is a sorted set and not bother resorting
        tests = newTests
    }
}
