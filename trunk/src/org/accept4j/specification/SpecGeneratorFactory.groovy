package org.accept4j.specification

import org.accept4j.specification.generator.SpecGenerator
import org.accept4j.specification.generator.ExcelSpecGenerator

import org.accept4j.specification.generator.DirectorySpecGenerator

import org.accept4j.specification.generator.jira.JiraWrapperFactory
import org.accept4j.specification.generator.jira.JiraSpecGenerator
import groovy.util.logging.Log4j

/**
 * Copyright: Daniel Kelleher Date: 01.10.12 Time: 20:39
 */
@Log4j
class SpecGeneratorFactory {
    SpecGenerator extensionGenerator
    def rules

    public SpecGeneratorFactory() {
        findExtension()
        setUpRules()
    }

    private void findExtension() {
        def cl = getClass().classLoader
        ServiceLoader<SpecGenerator> serviceLoader = ServiceLoader.load(SpecGenerator, cl)

        def iterator = serviceLoader.iterator()
        if (iterator.hasNext()) extensionGenerator = iterator.next()

        if (extensionGenerator != null) {
            log.info "Found spec generator: ${extensionGenerator.class.simpleName}"
        }
    }

    private setUpRules() {
        rules =
            [
                    [{new File("spec.xml").exists()}, {} as SpecGenerator], // do nothing
                    [{new File(ExcelSpecGenerator.FILE).exists()}, new ExcelSpecGenerator()],
                    [{new File(DirectorySpecGenerator.PATH).exists()}, new DirectorySpecGenerator()],
                    [{new File(JiraWrapperFactory.CONFIG_FILE).exists()}, new JiraSpecGenerator()],
                    [{extensionGenerator != null}, extensionGenerator]
            ]
    }

    public SpecGenerator getGenerator() {
        for (rule in rules) {
            if (rule[0]()) return rule[1]
        }

        throw new NoMatchingGeneratorException()
    }
}
