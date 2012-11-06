package org.accept4j.specification.generator.jira

import org.accept4j.specification.generator.DeleteXMLPostProcessor
import org.accept4j.specification.generator.ExcelSpecAdapter
import org.accept4j.specification.generator.SpecGenerator
import org.accept4j.testpack.AcceptanceTestSuite

/**
 * Copyright: Daniel Kelleher Date: 01.10.12 Time: 21:02
 */
class JiraSpecGenerator implements SpecGenerator{
    public static final String FILE = "spec.xls"

    @Override
    void generate() {
        def wrapper = new JiraWrapperFactory().getInstance();
        AcceptanceTestSuite suite = wrapper.getSuite()

        def xmlFile = new File("spec.xml")
        xmlFile.withWriter() { it << suite.toXML() }
    }

    @Override
    void postProcess() {
        // do nothing here - we might change this later using a config file to indicate whether we want to recreate the spec on every compile
        // default is not to do so, as it may be slow
    }

    static void main(args) {
        new JiraSpecGenerator().generate()
    }
}
