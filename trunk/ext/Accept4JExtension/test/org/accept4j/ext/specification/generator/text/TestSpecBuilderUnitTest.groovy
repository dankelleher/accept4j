package org.accept4j.ext.specification.generator.text

import org.junit.Test
import org.junit.Before
import org.accept4j.specification.SpecParseException
import org.accept4j.testpack.AcceptanceTestSuite

/**
 * Copyright: Daniel Kelleher Date: 09.12.12 Time: 19:38
 */
class TextSpecBuilderUnitTest {
    TextSpecBuilder builder
    AcceptanceTestSuite suite
    
    @Before void setUp() {
        suite = new AcceptanceTestSuite()
        builder = new TextSpecBuilder(suite:suite)
    }
    
    @Test(expected = SpecParseException)
    void lineBreaksInOtherThanDescriptionThrowsException() {
        builder.acceptLine("id:abc")
        builder.acceptLine("def")
    }
    
    @Test(expected = SpecParseException)
    void descriptionWithoutAnIDThrowsAnException() {
        builder.acceptLine("description:id must come before a description")
    }

    @Test(expected = SpecParseException)
    void nameWithoutAnIDThrowsAnException() {
        builder.acceptLine("name:id must come before a name")
    }
    
    @Test void dummySuiteGroupAndPackNamesAddedForTest() {
        builder.acceptLine("id:a")
        builder.acceptLine("name:b")
        builder.acceptLine("description:c")
        builder.complete()

        def group = suite.groups.iterator()[0]
        def pack = group.testPacks.iterator()[0]
        def test = pack.tests.iterator()[0]
        assert suite.name == ""
        assert group.name == ""
        assert pack.name == ""
        assert test.id == "a"
        assert test.name == "b"
        assert test.description == "c"
    }

    @Test void twoTestsShouldBeAddedToTheSamePackIfTheyDoNotDeclareANewPack() {
        builder.acceptLine("suite:s")
        builder.acceptLine("group:g")
        builder.acceptLine("pack:p")
        builder.acceptLine("id:a")
        builder.acceptLine("name:b")
        builder.acceptLine("description:c")
        builder.acceptLine("id:d")
        builder.acceptLine("name:e")
        builder.acceptLine("description:f")
        builder.complete()

        def group = suite.groups.iterator()[0]
        def pack = group.testPacks.iterator()[0]
        def test1 = pack.tests.iterator()[0]
        def test2 = pack.tests.iterator()[1]
        assert suite.name == "s"
        assert group.name == "g"
        assert pack.name == "p"
        assert test1.id == "a"
        assert test1.name == "b"
        assert test1.description == "c"
        assert test2.id == "d"
        assert test2.name == "e"
        assert test2.description == "f"
    }
    
    @Test void descriptionsCanHaveMultipleLines() {
        builder.acceptLine("id:a")
        builder.acceptLine("name:b")
        builder.acceptLine("description:line1")
        builder.acceptLine("line2")
        builder.complete()

        def group = suite.groups.iterator()[0]
        def pack = group.testPacks.iterator()[0]
        def test = pack.tests.iterator()[0]
        assert test.id == "a"
        assert test.name == "b"
        assert test.description == "line1\nline2"
    }
}
