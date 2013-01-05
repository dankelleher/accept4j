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
    
    protected String getAdditionalClasspath() {""}

    @After public void tearDown() {
        deleteFiles()
    }

    private void deleteFiles() {
        new AntBuilder().delete(dir: STAGE_DIR)
    }

    private void copySpec() {
        new AntBuilder().copy(todir: STAGE_DIR) {
            fileset(dir : "testData/functional/$specDir")
        }
    }

    void compile(Accept4jRunner runner) {
        runner.run()
    }
}