package org.accept4j.specification.generator.jira

import org.accept4j.specification.generator.jira.xmlrpc.JiraWrapperXMLRPC
import org.accept4j.specification.generator.jira.xmlrpc.JiraProxy

/**
 * Copyright: Daniel Kelleher Date: 03.11.12 Time: 12:14
 */
class JiraWrapperFactory {
    public static final String CONFIG_FILE = "jira.config"

    def xmlRpcServerProxy
    def config

    JiraWrapperFactory() {
        config = new Properties()
        new File(CONFIG_FILE).withInputStream {
            stream -> config.load(stream)
        }
        xmlRpcServerProxy = new JiraProxy(config.url)
    }

    JiraWrapper getInstance() {
        return new JiraWrapperXMLRPC(xmlRpcServerProxy, config.username, config.password, config.filterId)
    }
}