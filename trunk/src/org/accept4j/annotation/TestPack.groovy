package org.accept4j.annotation

import java.lang.annotation.*

@Target(ElementType.TYPE)
public @interface TestPack {
    String group()
    String name() default ""
}