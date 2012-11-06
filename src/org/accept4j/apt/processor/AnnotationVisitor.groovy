package org.accept4j.apt.processor

import javax.lang.model.element.ExecutableElement

import javax.lang.model.util.ElementKindVisitor6
import org.accept4j.annotation.TestPack
import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.testpack.AcceptanceTestItem

@PackageScope class AnnotationVisitor extends ElementKindVisitor6<Void, AcceptanceTestSuite> {
    Void visitExecutableAsMethod(ExecutableElement e, AcceptanceTestSuite suite) {
        def annotation = e.getAnnotation(org.accept4j.annotation.AcceptanceTest.class)

        TestPack testPack = e.enclosingElement.getAnnotation(TestPack.class)
        String testPackName = testPack.name() ?: e.enclosingElement.simpleName
        String group = testPack.group()

        suite.findOrCreate(group).findOrCreate(testPackName).add(new AcceptanceTestItem(id : annotation.id(), methodName: e.simpleName))
        return
    }
}
