package org.accept4j.ext.specification.generator.text

import org.accept4j.specification.generator.SpecGenerator
import org.accept4j.specification.generator.DeleteXMLPostProcessor
import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.specification.SpecParseException

/**
 * Copyright: Daniel Kelleher Date: 09.12.12 Time: 19:21
 */
class TextSpecGenerator implements SpecGenerator {
    public static final String FILE = "spec.txt"

    @Override
    void generate() {
        AcceptanceTestSuite suite = new AcceptanceTestSuite()
        TextSpecBuilder builder = new TextSpecBuilder(suite: suite)
        new File(FILE).eachLine {
            builder.acceptLine it
        }
        builder.complete()
        
        if (suite.groups.isEmpty()) throw new SpecParseException("No tests found in " + FILE)

        def xmlFile = new File("spec.xml")
        xmlFile.withWriter() { it << suite.toXML() }
    }

    @Override
    void postProcess() {
        new DeleteXMLPostProcessor().postProcess()
    }
}
