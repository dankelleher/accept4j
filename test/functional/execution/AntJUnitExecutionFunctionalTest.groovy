package functional.execution

import org.junit.Test
import functional.runner.JavacRunner
import functional.FunctionalTestFixture
import functional.runner.AntRunner
import org.junit.Before
import groovy.util.slurpersupport.GPathResult

/**
 * Copyright: Daniel Kelleher Date: 18.12.12 Time: 16:43
 */
class AntJUnitExecutionFunctionalTest extends FunctionalTestFixture {

    public AntJUnitExecutionFunctionalTest() {
        super("xmlspec")
    }

    @Test void testTestExecutionStatusAddedToReport() {
        generateInitialReport()
        runJunitThroughAnt()
        checkReport()
    }

    void checkReport() {
        def report = new XmlSlurper().parse new File("$STAGE_DIR/accept4j/test.html")

        assert getClassForTest(report, '1.1') == "testPass"
        assert getClassForTest(report, '1.2') == "testPass"
        assert getClassForTest(report, '1.3') == ""
        assert getClassForTest(report, '2.1') == "testFail"
        assert getClassForTest(report, '2.2') == "testError"
    }

    def getClassForTest(GPathResult report, String testId) {
        def testIdField = report.'**'.find {
            it.name() == 'td' && it.text() == testId
        }
        def testRow = testIdField.'..'
        def className = testRow.'@class'
        return className
    }

    void runJunitThroughAnt() {
        def runner = new AntRunner(target:"test")
        runner.run()
    }

    void generateInitialReport() {
        def runner = new JavacRunner("${FunctionalTestFixture.ROOT_DIR}/out/artifacts/accept4j.jar")
        runner.run()
    }
}
