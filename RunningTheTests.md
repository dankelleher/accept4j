# Introduction #

The accept4j report generated on compilation will tell you whether tests exist for each of the requirements in the spec, but not if these tests have passed or failed.

These instructions show how to annotate the report with the test results.

# Details #

At present, accept4j supports JUnit only.

The easiest way to see this in action is to try the [examples](Examples.md). Run the steps to compile the example code, and then run the included ant target `test` to run the junits.

Open `test.html`. Some of the example tests have been coded to fail, and are indicated with a red background in the report.

Open the ant script `build.xml`. The relevant line is:

```
<formatter classname="org.accept4j.execution.listener.AntJUnitTestListener"/>
```

This JUnit formatter takes the results of the junit tests and updates the accept4j report accordingly. To add accept4j support to your junit build, just include this formatter.

The accept4j report must already exist, so ensure the accept4j jar is also included on the compile classpath.

Note, junit tests are currently matched to the spec by method name only. This does not support multiple tests with the same method name yet.