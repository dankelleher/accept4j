package functional

import groovy.util.slurpersupport.GPathResult
import org.junit.Test
import org.junit.Before
import org.junit.After

/**
 * Copyright: Daniel Kelleher Date: 04.11.12 Time: 13:49
 */
abstract class FunctionalTest {
    protected static final String STAGE_DIR = "testData/functional/stage"
    protected static final String ROOT_DIR = "../../.."   // relative to the stage dir

    protected String specDir;

    public FunctionalTest(String specDir) {
        this.specDir = specDir;
    }
    
    @Before public void setUp() {
        copySpec()
        compile()
    }

    @Test public void testReportIsGeneratedOnCodeCompile() {
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

    private void compile() {
        String[] arguments = ["javac", "-d", "out", "-classpath", "$ROOT_DIR/out/artifacts/accept4j/accept4j.jar;$ROOT_DIR/lib/junit-4.10.jar", "src/org/exampleprog/test/*.java"]

        Process proc = Runtime.getRuntime().exec(arguments, null, new File(STAGE_DIR));
        System.err << proc.errorStream
    }
}
