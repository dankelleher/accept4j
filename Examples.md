[examples.zip](http://accept4j.googlecode.com/files/examples.zip) contains set of directories, each containing an example project using a different spec type.

To run an example:

  1. Download and extract [examples.zip](http://accept4j.googlecode.com/files/examples.zip)
  1. Pick a spec type and open the associated directory
  1. Open a terminal at the root of this directory
  1. Run the ant build script included (just type `ant`, and the default build task will run)
> OR
If you don't want to use ant, just use javac, including the jars in the lib/ directory on your classpath:

```
javac -d out -classpath lib/accept4j.jar;lib/junit-4.10.jar src\org\exampleprog\test\*.java
```

The accept4j report will be generated in accept4j/test.html