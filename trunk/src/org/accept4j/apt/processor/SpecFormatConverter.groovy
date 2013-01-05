package org.accept4j.apt.processor

import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.stream.StreamResult

/**
 * Copyright: Daniel Kelleher Date: 15.12.12 Time: 19:18
 */
class SpecFormatConverter {
    static void convertToHTML() {
        def factory = TransformerFactory.newInstance()
        def transformer = factory.newTransformer(new StreamSource(SpecFormatConverter.getClassLoader().getResourceAsStream("template/template.xsl")))
        transformer.transform(new StreamSource(org.accept4j.apt.processor.AcceptanceTestProcessor.XML_FILE), new StreamResult(org.accept4j.apt.processor.AcceptanceTestProcessor.HTML_FILE))
    }
}
