package functional

/**
 * Copyright: Daniel Kelleher Date: 04.11.12 Time: 13:49
 */
class JiraSpecFunctionalTest extends FunctionalTest {

    public JiraSpecFunctionalTest() {
        super("jiraspec");
    }

    protected void checkReport() {
        def report = new XmlSlurper().parse new File("$STAGE_DIR/accept4j/test.html")

        assert getImageForTest(report, 'TST-48730', true)
        assert getImageForTest(report, 'TST-48731', true)
        assert getImageForTest(report, 'TST-48732', false)

        assert getImageForTest(report, 'TST-48701', true)
        assert getImageForTest(report, 'TST-48702', true)
    }
}
