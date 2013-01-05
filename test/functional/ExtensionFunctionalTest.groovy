package functional

/**
 * Copyright: Daniel Kelleher Date: 13.12.12 Time: 11:46
 */
class ExtensionFunctionalTest extends SpecGenerationFunctionalTestFixture {
    public ExtensionFunctionalTest() {
        super("extension");
    }

    protected String getAdditionalClasspath() {";Accept4JExtension.jar"}
}
