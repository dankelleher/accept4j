# What is accept4j? #

accept4j bridges the gap between automated tests and business requirements in Java applications, by providing an automatically-generated, easy-to-read report identifying which requirements are tested, and which are not.

![http://accept4j.googlecode.com/svn/wiki/screenshot3.png](http://accept4j.googlecode.com/svn/wiki/screenshot3.png)

# So it's like [jBehave](http://jbehave.org/)? #

It's similar, but more lightweight. Like jBehave, accept4j uses annotations to configure test code, and generates reports to be read by the business.

However, jBehave is a [BDD](http://en.wikipedia.org/wiki/Behavior-driven_development) framework that requires you to write tests in a BDD style. accept4j does not impose any restrictions on your tests.

In addition, accept4j focuses purely on linking external requirements from the business to automated acceptance tests written by the developers. In jBehave, the requirements are encoded in the form of BDD tests in the code itself. There is no connection to an external set of requirements.

# Why should I use accept4j? #

Use accept4j if:
  * you are a Business Analyst and...
    * you want to know that developers have delivered your specific requirements
    * you don't want to manually re-test every iteration
    * you are not interested in reading source code
  * you are a developer and...
    * you want to demonstrate that your acceptance tests tie back to specific requirements from the business
    * you want to encourage BAs to provide detailed requirements
    * you want to introduce BDD to your development team
  * you are a PM and...
    * you want visibility on your team's progress
    * you want to bridge the communication gap between IT and Business

# Can I see a demo? #

A number of example projects are available. To run them, download [examples.zip](http://accept4j.googlecode.com/files/examples.zip) and follow steps in [Examples](Examples.md).

# How do I use accept4j? #

  1. Pick a [spec generation option](SpecGeneration.md)
  1. Include the [accept4j.jar](http://code.google.com/p/accept4j/downloads/list) on the classpath during compilation
  1. Write acceptance test classes and methods (for example, in [JUnit](http://www.junit.org/))
  1. Add the following annotation to your test classes
```
@TestPack(
        group="Booking",
        name="When cancelling an order"
)
public class WhenCancellingAnOrder {

    @AcceptanceTest(id="2.1")
    public void testTheClientShouldBeRefundedIfTheOrderIsNotYetFilled() {
        // test code here
    }

    @AcceptanceTest(id="2.2")
    public void testTheCancellationShouldBeDeclinedIfTheOrderIsFilled() {
        // test code here
    }
}
```
  1. Compile your code! The report will be generated as accept4j/test.html

# So the tests exist, but do they pass? #

When run with JUnit, accept4j will indicate the pass/fail status of the tests. See [here](RunningTheTests.md) for instructions.

# System Requirements #

  * accept4j supports Java SE 6 +

# Coming Soon #

  * Friendlier error messages
  * Improved integration with JIRA
  * Ant & Maven instructions
  * More spec generation options
  * Hyperlinks in reports

# The Team #

The principal developer is Daniel Kelleher. Contributions, suggestions and testers are welcome!