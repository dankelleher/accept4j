package org.accept4j.specification.generator

import jxl.Workbook
import jxl.read.biff.BiffException
import org.accept4j.specification.SpecParseException
import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.apt.processor.AcceptanceTestProcessor

/**
 * Copyright: Daniel Kelleher Date: 01.10.12 Time: 21:02
 */
class ExcelSpecGenerator implements SpecGenerator{
    public static final String FILE = "spec.xls"

    @Override
    void generate() {
        ExcelSpecAdapter spec = new ExcelSpecAdapter(FILE)
        AcceptanceTestSuite suite = spec.suite

        def xmlFile = new File("spec.xml")
        xmlFile.withWriter() { it << suite.toXML() }
    }

    @Override
    void postProcess() {
        new DeleteXMLPostProcessor().postProcess()
    }
}
