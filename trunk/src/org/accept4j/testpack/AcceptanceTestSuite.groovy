package org.accept4j.testpack

import groovy.xml.MarkupBuilder
import groovy.util.logging.Log4j

/**
 * Copyright: Daniel Kelleher Date: 23.09.12 Time: 21:34
 */
@Log4j
class AcceptanceTestSuite {
    String name
    Date datetime

    Set<AcceptanceTestPackGroup> groups = new TreeSet<AcceptanceTestPackGroup>();

    public void add(AcceptanceTestPackGroup group) {
        groups.add(group)
    }

    public AcceptanceTestSuite leftShift(AcceptanceTestPackGroup group) {
        add(group)
        return this
    }

    public boolean isEmpty() {
        groups.isEmpty()
    }
    
    public AcceptanceTestPackGroup findOrCreate(def name) {
        def group = groups.find { it.name == name }
        if (!group) {
            group = new AcceptanceTestPackGroup(name: name)
            add(group)
        }

        return group
    }

    public String toXML() {
        def stringWriter = new StringWriter()
        def builder = new MarkupBuilder(stringWriter)

        builder.suite(name:name, datetime:datetime) {
            groups.each { it.toXML(builder)}
        }

        return stringWriter.toString()
    }

    protected void compareToSpec() {
        if (!new File("spec.xml").exists()) {
            log.error "No spec found"
            return
        }

        def spec = new XmlSlurper().parse("spec.xml")

        name = spec.@name.text()
        datetime = new Date()

        for (def group in spec.group) {
            for (def pack in group.pack) {
                for (def test in pack.test) {
                    createOrUpdateTestDetails(group, pack, test)
                }
            }
        }
    }

    private createOrUpdateTestDetails(group, pack, test) {
        def testId = test.@id.text()
        def groupName = group.@name.text()
        def packName = pack.@name.text()
        def implementedTest = findImplementedTest(groupName, packName, testId)

        if (implementedTest) {
            implementedTest.addSpecDetails(test)
        } else {
            def implementedPack = findOrCreate(groupName).findOrCreate(packName)

            implementedTest = new AcceptanceTestItem(id: testId)
            implementedTest.addSpecDetails(test)
            implementedPack << implementedTest
        }
    }

    private AcceptanceTestItem findImplementedTest(groupName, packName, testId) {
        if ((groupName == "") && (packName == "")) {
            // just search by test id
            return findTestById(testId)
        } else {
            def implementedPack = findOrCreate(groupName).findOrCreate(packName)
            return implementedPack.find(testId)
        }
    }

    private AcceptanceTestItem findTestById(testId) {
        groups.findResult  { it.findTestById(testId) }
    }

    void recursiveSort() {
        groups*.recursiveSort()  // sort the groups
        resort()
    }

    private void resort() {
        def newGroups = new TreeSet<AcceptanceTestPackGroup>()
        groups.each {
            newGroups << it
        }
        //newGroups.addAll(groups) // This doesn't work... I think addAll must optimise if the input is a sorted set and not bother resorting
        groups = newGroups
    }
}
