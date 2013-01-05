package functional.execution

import org.junit.Test
import functional.runner.JavacRunner
import functional.FunctionalTestFixture
import functional.runner.AntRunner
import org.junit.Before

/**
 * Copyright: Daniel Kelleher Date: 18.12.12 Time: 16:43
 */
class AntJUnitExecutionFunctionalTest extends FunctionalTestFixture {

    public AntJUnitExecutionFunctionalTest() {
        super("xmlspec")
    }

    @Before public void setUp() {
        super.setUp()
        new File("$STAGE_DIR/reports").mkdir()
    }

    @Test void testTestExecutionStatusAddedToReport() {
        generateInitialReport()
        runJunitThroughAnt()
        checkReport()
    }

    void checkReport() {
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
