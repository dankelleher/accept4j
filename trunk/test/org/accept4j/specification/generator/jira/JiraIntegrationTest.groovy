package org.accept4j.specification.generator.jira

import org.junit.Before
import org.junit.Test
import org.junit.After

/**
 * Copyright: Daniel Kelleher Date: 03.11.12 Time: 01:03
 */

class JiraIntegrationTest {

    @Before public void setUp() {
        new File('jira.config').withDataOutputStream {
            os ->
            new File('testData/jira.config').withDataInputStream { is ->
                os << is
            }
        }
    }

    @After public void tearDown() {
        new File('jira.config').delete()
    }

    @Test public void testConnectToExampleJiraSite() {
        def wrapper = new JiraWrapperFactory().getInstance();
        def xml = wrapper.getSuite().toXML()
        def suite = new XmlSlurper().parseText(xml)
        assert suite.group.pack.test.size() == 5
        assert suite.group.pack.test*.@id == ["TST-48701", "TST-48702", "TST-48730", "TST-48731", "TST-48732"]
        assert suite.group.pack.test*.@name == [
                "When cancelling an order, the client should be refunded if the order is not yet filled",
                "When cancelling an order, the cancellation should be declined if the order is filled",
                "When making an order, the client should be billed",
                "When making an order, the stock shoud be reduced",
                "When making an order, the order is rejected if the stock is unavailable"
        ]
    }
}