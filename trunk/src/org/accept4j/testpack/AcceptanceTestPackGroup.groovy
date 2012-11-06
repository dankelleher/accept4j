package org.accept4j.testpack

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:17
 */
class AcceptanceTestPackGroup {
    String name
    List<AcceptanceTestPack> testPacks = new ArrayList<AcceptanceTestPack>();

    public void add(AcceptanceTestPack testPack) {
        testPacks.add(testPack)
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

    private AcceptanceTestItem findTestById(testId) {
        testPacks.findResult  { it.findTestById(testId) }
    }
}
