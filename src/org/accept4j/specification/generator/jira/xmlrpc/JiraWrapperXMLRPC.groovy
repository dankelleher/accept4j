package org.accept4j.specification.generator.jira.xmlrpc

import org.accept4j.specification.generator.jira.JiraWrapper
import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.testpack.AcceptanceTestPackGroup
import org.accept4j.testpack.AcceptanceTestPack
import org.accept4j.testpack.AcceptanceTestItem

/**
 * Copyright: Daniel Kelleher Date: 03.11.12 Time: 00:43
 */
class JiraWrapperXMLRPC implements JiraWrapper {
    private def server
    private def loginToken
    private String filterId

    JiraWrapperXMLRPC(def server, String username, String password, String filterId) {
        this.server = server
        this.filterId = filterId
        loginToken = server.login(username, password)
    }

    AcceptanceTestSuite getSuite() {
        def issues = getIssuesFromJira()
        return convertIssuesToAcceptanceTests(issues)
    }

    private def getIssuesFromJira() {        
        def issues = server.getIssuesFromFilter(loginToken, filterId)

        issues.sort { it.key }
        filterIssuesByType(issues)

        return issues
    }

    private void filterIssuesByType(issues) {
        def nonSubTaskIssueTypes = server.getIssueTypes(loginToken)*.id
        issues.removeAll { it.type in nonSubTaskIssueTypes }
    }

    private def convertIssuesToAcceptanceTests(def issues) {
        AcceptanceTestSuite suite = new AcceptanceTestSuite(name: "JIRA")
        AcceptanceTestPackGroup group = suite.findOrCreate("")
        AcceptanceTestPack pack = group.findOrCreate("")

        issues.each {
            pack << new AcceptanceTestItem(
                    id: it.key,
                    name: it.summary,
                    description: it.description
            )
        }

        return suite
    }
}
