package org.accept4j.testpack

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:17
 */
class AcceptanceTestPackGroup implements Comparable<AcceptanceTestPackGroup> {
    String name
    Set<AcceptanceTestPack> testPacks = new TreeSet<AcceptanceTestPack>();

    public void add(AcceptanceTestPack testPack) {
        testPacks.add(testPack)
    }

    public AcceptanceTestPackGroup leftShift(AcceptanceTestPack testPack) {
        add(testPack)
        return this
    }

    public AcceptanceTestPack findOrCreate(def name) {
        def pack = testPacks.find { it.name == name }
        if (!pack) {
            pack = new AcceptanceTestPack(name: name)
            add(pack)
        }

        return pack
    }

    protected void toXML(def builder) {
        builder.group(name:name) {
            testPacks.each { it.toXML(builder)}
        }
    }

    private AcceptanceTestItem find(args) {
        testPacks.findResult  { it.find(args) }
    }

    @Override
    int compareTo(AcceptanceTestPackGroup o) {
        if (testPacks.isEmpty()) {
            return o.testPacks.isEmpty() ? 0 : -1
        }
        if (o.testPacks.isEmpty()) return 1
        
        return testPacks.iterator()[0].compareTo(o.testPacks.iterator()[0])
    }

    void recursiveSort() {
        testPacks*.recursiveSort()
        resort()
    }
    
    private void resort() {
        def newPacks = new TreeSet<AcceptanceTestPack>()
        testPacks.each {
            newPacks << it
        }
        // newPacks.addAll(testPacks)   // This doesn't work... I think addAll must optimise if the input is a sorted set and not bother resorting
        testPacks = newPacks
    }
}
