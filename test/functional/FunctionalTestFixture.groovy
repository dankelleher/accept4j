package functional

import groovy.util.slurpersupport.GPathResult
import org.junit.Test
import org.junit.Before
import org.junit.After
import functional.runner.JavacRunner
import functional.runner.Accept4jRunner
import functional.runner.AntRunner

/**
 * Copyright: Daniel Kelleher Date: 04.11.12 Time: 13:49
 */
abstract class FunctionalTestFixture {
    protected static final String STAGE_DIR = "testData/functional/stage"
    protected static final String ROOT_DIR = "../../.."   // relative to the stage dir

    protected String specDir;

    public FunctionalTestFixture(String specDir) {
        this.specDir = specDir;
    }
    
    @Before public void setUp() {
        copySpec()
    }

    @Test public void testReportIsGeneratedOnJavacCompileWithStandardJar() {
        compile(new JavacRunner("${FunctionalTestFixture.ROOT_DIR}/out/artifacts/accept4j.jar"))
        checkReport()
    }

    @Test public void testReportIsGeneratedOnJavacCompileWithCompleteJar() {
        compile(new JavacRunner("${FunctionalTestFixture.ROOT_DIR}/out/artifacts/accept4j-all.jar"))
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
            "$cpRoot/lib/groovy-all-1.8.6.jar"

        compile(new JavacRunner(classpath))
        checkReport()
    }

    @Test public void testReportIsGeneratedOnAntCompile() {
        compile(new AntRunner())
        checkReport()
    }

    @After public void tearDown() {
        deleteFiles()
    }

    protected void checkReport() {
        def report = new XmlSlurper().parse new File("$STAGE_DIR/accept4j/test.html")

        assert getImageForTest(report, '1.1', true)
        assert getImageForTest(report, '1.2', true)
        assert getImageForTest(report, '1.3', false)

        assert getImageForTest(report, '2.1', true)
        assert getImageForTest(report, '2.2', true)
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

    private void deleteFiles() {
        new AntBuilder().delete(dir: STAGE_DIR)
    }

    private void copySpec() {
        new AntBuilder().copy(todir: STAGE_DIR) {
            fileset(dir : "testData/functional/$specDir")
        }
    }

    private void compile(Accept4jRunner runner) {
        runner.run()
    }
}
