package functional

import org.junit.Test
import functional.runner.JavacRunner
import functional.runner.AntRunner
import groovy.util.slurpersupport.GPathResult

/**
 * Copyright: Daniel Kelleher Date: 02.01.13 Time: 23:35
 */
abstract class SpecGenerationFunctionalTestFixture extends FunctionalTestFixture {

    public SpecGenerationFunctionalTestFixture(String specDir) {
        super(specDir);
    }

    @Test public void testReportIsGeneratedOnJavacCompileWithStandardJar() {
        compile(new JavacRunner("${FunctionalTestFixture.ROOT_DIR}/out/artifacts/accept4j.jar$additionalClasspath"))
        checkReport()
    }

    @Test public void testReportIsGeneratedOnJavacCompileWithCompleteJar() {
        compile(new JavacRunner("${FunctionalTestFixture.ROOT_DIR}/out/artifacts/accept4j-all.jar$additionalClasspath"))
        checkReport()
    }

    @Test public void testReportIsGeneratedOnJavacCompileWithSkinnyJar() {
        def cpRoot = "${FunctionalTestFixture.ROOT_DIR}/out/artifacts/accept4j"
        def classpath =
            "$cpRoot/accept4j-skinny.jar;" +
                    "$cpRoot/lib/tools.jar;" +
                    "$cpRoot/lib/log4j-1.2.17.jar;" +
                    "$cpRoot/lib/jxl.jar;" +
                    "$cpRoot/lib/groovy-xmlrpc-0.8.jar;" +
                    "$cpRoot/lib/groovy-all-1.8.6.jar" +
                    additionalClasspath

        compile(new JavacRunner(classpath))
        checkReport()
    }

    @Test public void testReportIsGeneratedOnAntCompile() {
        compile(new AntRunner())
        checkReport()
    }

    protected void checkReport() {
        def report = new XmlSlurper().parse new File("$STAGE_DIR/accept4j/test.html")

        assert getImageForTest(report, '1.1', true)
        assert getImageForTest(report, '1.2', true)
        assert getImageForTest(report, '1.3', false)

        assert getImageForTest(report, '2.1', true)
        assert getImageForTest(report, '2.2', true)

        assert getNameForTest(report, '1.1') == 'the client should be billed'
        assert getNameForTest(report, '1.2') == 'the stock should be reduced'
        assert getNameForTest(report, '1.3') == 'the order is rejected if the stock is unavailable'

        assert getNameForTest(report, '2.1') == 'the client should be refunded if the order is not yet filled'
        assert getNameForTest(report, '2.2') == 'the cancellation should be declined if the order is filled'

        assertTestsInOrder(report, ['1.1', '1.2', '1.3', '2.1', '2.2'])
    }

    protected def assertTestsInOrder(GPathResult report, def testList) {
        def foundTests = report.'**'.findAll {
            it.name() == 'td' && it.text() in testList
        }

        assert foundTests*.text() == testList
    }

    protected def getImageForTest(GPathResult report, String testId, boolean exists) {
        def title = exists ? 'A test exists for this specification' : 'No test exists for this specification'

        def testIdField = report.'**'.find {
            it.name() == 'td' && it.text() == testId
        }
        def testRow = testIdField.'..'
        def img = testRow.'**'.find {
            it.name() == 'img' && it.@title == title
        }
        return img
    }

    protected def getNameForTest(GPathResult report, String testId) {
        def testIdField = report.'**'.find {
            it.name() == 'td' && it.text() == testId
        }
        def testRow = testIdField.'..'
        def name = testRow.td[2].text()
        return name
    }
}
