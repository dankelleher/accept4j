# Introduction #

If you do not like any of the built-in spec generators, this page will show you how to write your own.


# Details #

## 1. Writing the generator ##

Implement the `org.accept4j.specification.generator.SpecGenerator` interface.

The purpose of this class is to generate an `AcceptanceTestSuite` object and then to write this to `spec.xml` using the `toXML()` method.

For an example, check out the code at http://code.google.com/p/accept4j/source/browse/trunk/ext/Accept4JExtension/.

## 2. Creating the Jar ##

Create a [Jar file](http://docs.oracle.com/javase/tutorial/deployment/jar/build.html) of the spec generator.

In the `META-INF` directory, create a directory called `services`. Inside `services`, create a file called `org.accept4j.specification.generator.SpecGenerator`. This file should contain a single line - the fully-qualified name of your spec generator implementation.

For example, the line in the example project above is:

```
org.accept4j.ext.specification.generator.text.TextSpecGenerator
```

## 3. Include the Jar ##

Add the Jar to the compile-time classpath of your project (along with the accept4j jar). accept4j will automatically pick up the custom spec generator.