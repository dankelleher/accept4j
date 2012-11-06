package org.accept4j.specification

import org.accept4j.specification.generator.SpecGenerator
import org.accept4j.specification.generator.ExcelSpecGenerator

import org.accept4j.specification.generator.DirectorySpecGenerator

import org.accept4j.specification.generator.jira.JiraWrapperFactory
import org.accept4j.specification.generator.jira.JiraSpecGenerator

/**
 * Copyright: Daniel Kelleher Date: 01.10.12 Time: 20:39
 */
class SpecGeneratorFactory {
    def rules = [
            [{new File("spec.xml").exists()}, {} as SpecGenerator], // do nothing
            [{new File(ExcelSpecGenerator.FILE).exists()}, new ExcelSpecGenerator()],
            [{new File(DirectorySpecGenerator.PATH).exists()}, new DirectorySpecGenerator()],
            [{new File(JiraWrapperFactory.CONFIG_FILE).exists()}, new JiraSpecGenerator()]
    ]

    public SpecGenerator getGenerator() {
        for (rule in rules) {
            if (rule[0]()) return rule[1]
        }

        throw new NoMatchingGeneratorException()
    }
}
