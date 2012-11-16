package functional.runner

import functional.FunctionalTestFixture
import org.apache.tools.ant.Project
import org.apache.tools.ant.ProjectHelper

/**
 * Copyright: Daniel Kelleher Date: 11.11.12 Time: 19:27
 */
class AntRunner implements Accept4jRunner {
    void run() {
        /*
        def antFile = new File("$FunctionalTestFixture.STAGE_DIR/build.xml")
        def project = new Project()
        project.setBasedir(FunctionalTestFixture.STAGE_DIR)
        project.init()
        ProjectHelper.projectHelper.parse(project, antFile)
        project.executeTarget(project.defaultTarget);
        */

        /*
        A few options were tried here to run ant from a test while making it non-OS-specific...
        1. Run Ant from Runtime.exec : This would be the best option as it most accurately models reality, but doesn't work, at least on Windows as exec does not find "ant"
        2. Run Ant from Runtime.exec, giving full path e.g. String[] arguments = ["C:\\apache-ant-1.8.4\\bin\\ant.bat"]. This is platform-dependent.
        2. Run Ant programmatically, via org.apache.tools.ant.Project : This works ok but you cannot set the javac root directory to STAGE_DIR so javac looks in the project root for the spec
        3. Run Ant via Java : This works, but to avoid having to bundle Ant with accept4j, you need to set the ANT_HOME environment variable. We don't want to bundle Ant with accept4j as it is not a dependency. Only tested here because many people build projects using ant
         */

        String antHome = System.getenv("ANT_HOME")

        if (!antHome) fail("Please set the ANT_HOME environment variable to your local ant install")

        new AntBuilder().copy(file: "testData/functional/antTestBuild.xml", tofile: "${FunctionalTestFixture.STAGE_DIR}/build.xml");

        String[] arguments = ["java", "-classpath", "$antHome/lib/ant-launcher.jar", "org.apache.tools.ant.launch.Launcher"]

        Process proc = Runtime.getRuntime().exec(arguments, null, new File(FunctionalTestFixture.STAGE_DIR));
        System.err << proc.errorStream

    }
}
