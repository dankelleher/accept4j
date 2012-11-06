package org.accept4j.specification.generator

/**
 * Copyright: Daniel Kelleher Date: 07.10.12 Time: 19:07
 */
class DeleteXMLPostProcessor implements SpecGenerationPostProcessor {
    @Override
    void postProcess() {
        new File("spec.xml").delete()
    }
}
