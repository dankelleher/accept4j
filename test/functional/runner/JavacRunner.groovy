package functional.runner

import functional.FunctionalTestFixture

/**
 * Copyright: Daniel Kelleher Date: 11.11.12 Time: 17:39
 */
class JavacRunner implements Accept4jRunner {

    private String classpath
    
    JavacRunner(String classpath) {
        this.classpath = classpath
    }

    void run() {
        String[] arguments = ["javac", "-d", "out", "-classpath", "$classpath;${FunctionalTestFixture.ROOT_DIR}/lib/junit-4.10.jar", "src/org/exampleprog/test/*.java"]

        Process proc = Runtime.getRuntime().exec(arguments, null, new File(FunctionalTestFixture.STAGE_DIR));
        System.err << proc.errorStream
    }
}
