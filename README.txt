Introduction
============
This document describes the test plan organization, of both functional and coverage, for testing StringSearch library.
Also how these test cases are invoked and description of the output.

Organization
============
There are two separate entry point for Functional and Coverage tests respectively. The entry point of functional test
is FunctionalTests class. And for coverage testing, it is CoverageTests. These class don't contain any test case. They
barely initiate and call the StringMatchTests implementations, which contain the test cases. The StringMatchTests has
no method or constant declaration. It just ensures entry point classes do not accidentally call not test classes.
The method name of all the test cases start with "test". The entry point classes take this into consideration and
called those methods using Java Reflection API. This enables easily add/remove test methods by only modifying
respective test case container classes. Also a human readable test name generated from the name of the test case
method name.

The TestHelper class contains some method that are needed by multiple functional test classes. It will also show
formatted output of inputs, output and expected behavior.

Functional Test
===============
The functional test cases are organized into classes: StringMatchTests, IgnoreCaseTests, and WildcardsTests. The entry
point class initiates those classes based on the options (ignore case & wildcards) they support and call all the test
methods defined in those classes. All assertion failure results in throwing custom exception AssertionFailureException.

The information functional test operation prints include - the class under observation, the type of function it is
testing, sample input, expected output, actual output, and whether passed or failed. In case of failure, it also shows
last three lines of the calling trace. If any uncaught exception occurs, it shows the full stack trace.

Coverage Test
=============
The coverage test cases are organized into classes: StringMatchCoverage, StringMismatchCoverage, and CharIntMapCoverage.
The entry point class, CoverageTests works very similar to functional test entry point FunctionalTests. But since
coverage testing doesn't care about assertion it will not print any actual or expected output. It will only say the
type of testing it is currently running.