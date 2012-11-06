package org.accept4j.specification.generator.jira.xmlrpc

import groovy.net.xmlrpc.XMLRPCServerProxy

/**
 * Copyright: Daniel Kelleher Date: 03.11.12 Time: 12:18
 * See https://developer.atlassian.com/display/JIRADEV/JIRA+XML-RPC+Overview
 */
class JiraProxy extends XMLRPCServerProxy {
    JiraProxy(url) {
        super(url)
    }

    Object invokeMethod(String methodname, args) {
        super.invokeMethod('jira1.'+methodname, args)
    }
}