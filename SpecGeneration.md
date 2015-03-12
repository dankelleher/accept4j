# Introduction #

Accept4j takes a set of specifications, or test cases, from the business, and produces a report indicating which have been implemented in the code base. Specifications can come from a number of sources:

  * [Excel spreadsheet](SpecGeneration#Excel.md)
  * [Directory structure](SpecGeneration#Directory_structure.md)
  * [Atlassian JIRA issue tracking software](SpecGeneration#JIRA.md)
  * [XML file](SpecGeneration#XML.md)
  * [A custom specification generator](CustomSpecGenerator.md)


---


# Excel #

Include an excel file called `spec.xls` in the [working directory](SpecGeneration#Working_Directory.md), following [this template](http://accept4j.googlecode.com/svn/trunk/testData/functional/excelspec/spec.xls).

The tests should either be in a sheet called `spec` or the first sheet in the workbook.


---


# Directory structure #

Create a directory called `spec` in your [working directory](SpecGeneration#Working_Directory.md). The directories inside it will define your test structure. Use the template:

```
spec/<test suite name>/<group name>/<test pack name>/<test file>
```

![http://accept4j.googlecode.com/svn/wiki/directoryStructure.png](http://accept4j.googlecode.com/svn/wiki/directoryStructure.png)

The files in the test pack folders should be named:

_Test ID_`_`_Test Name_._extension_

for example: `1.1_the client should be billed.docx`

[See this example](http://code.google.com/p/accept4j/source/browse/#svn%2Ftrunk%2FtestData%2Ffunctional%2Fdirspec%2Fspec)

The spec files are not read by accept4j, so can be whatever format or file type you like. It is currently not possible to include test descriptions in the report using a directory structure spec.

NOTE: To cut down compile time, accept4j will create a cache of the directory structure in a file called `spec.xml`. It will only look through the directory if that file does not exist. To ensure accept4j picks up the latest changes, delete this file before compilation. **Coming Soon:** A setting to disable caching.


---


# JIRA #

For projects that use [Atlassian JIRA](http://www.atlassian.com/software/jira/overview/), you can easily configure accept4j to automatically retrieve the spec from JIRA.

_JIRA integration is currently experimental and does not provide all the fuctionality of the other spec options. Group and test pack naming is not yet supported._

  1. Include a copy of [jira.config](http://accept4j.googlecode.com/svn/trunk/testData/functional/jiraspec/jira.config) in your [working directory](SpecGeneration#Working_Directory.md).
  1. Edit jira.config with the URL of your own JIRA installation, and a username and password (note: encrypted passwords is not supported, so use a generic, read-only JIRA account here)
  1. Ensure the RPC plugin is enabled on your JIRA installation, by following instructions [here](https://developer.atlassian.com/display/JIRADEV/JIRA+XML-RPC+Overview#JIRAXML-RPCOverview-EnabletheRPCplugin).
  1. Create an issue filter in JIRA that selects all the issues you wish to include in your spec. Put the [filter ID](SpecGeneration#JIRA_filter_ID.md) in the `jira.config` file.

accept4j includes only subtask issue types in the spec. To ensure an issue is included, make sure it is set up as a subtask of a parent issue.

NOTE: To cut down compile time, accept4j will create a cache of the JIRA information in a file called `spec.xml`. It will only connect to JIRA if that file does not exist. To ensure accept4j picks up the latest changes from JIRA, delete this file before compilation. **Coming Soon:** A flag in `jira.config` to disable caching.


---


# XML #

Include an XML file called `spec.xml` in the [working directory](SpecGeneration#Working_Directory.md), following [this template](http://accept4j.googlecode.com/svn/trunk/testData/functional/xmlspec/spec.xml).


---


# Notes #

### Working Directory ###

The _working directory_ is whatever directory you run the java compiler from. Often this will be the root directory of your project.

### JIRA filter ID ###

Get the ID of the JIRA issues filter from the URL of the filter.

`https://jira.atlassian.com/issues/?filter=<filter ID>`

Select the filter from the Issues menu.

![http://accept4j.googlecode.com/svn/wiki/filterSelection.png](http://accept4j.googlecode.com/svn/wiki/filterSelection.png)

The URL of the resultant page will give you the filter ID.

![http://accept4j.googlecode.com/svn/wiki/filterURL.png](http://accept4j.googlecode.com/svn/wiki/filterURL.png)