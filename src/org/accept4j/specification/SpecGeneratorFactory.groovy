package org.accept4j.specification

import org.accept4j.specification.generator.SpecGenerator
import org.accept4j.specification.generator.ExcelSpecGenerator

/**
 * Copyright: Daniel Kelleher Date: 01.10.12 Time: 20:39
 */
class SpecGeneratorFactory {
    def rules = [
            [{new File("spec.xml").exists()}, {} as SpecGenerator], // do nothing
            [{new File("spec.xls").exists()}, new ExcelSpecGenerator()]
    ]

    public SpecGenerator getGenerator() {
        for (rule in rules) {
            if (rule[0]()) return rule[1]
        }

        throw new NoMatchingGeneratorException()
    }
}
