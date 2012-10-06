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

    List<AcceptanceTestPackGroup> groups = new ArrayList<AcceptanceTestPackGroup>();

    public void add(AcceptanceTestPackGroup group) {
        groups.add(group)
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
        def implementedPack = findOrCreate(group.@name.text()).findOrCreate(pack.@name.text())
        AcceptanceTestItem implementedTest = implementedPack.find(test.@id.text())

        if (implementedTest) {
            implementedTest.addSpecDetails(test)
        } else {
            implementedTest = new AcceptanceTestItem(id: test.@id.text())
            implementedTest.addSpecDetails(test)
            implementedPack.add(implementedTest)
        }
    }
}
