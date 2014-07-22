## sonar-j3c ##

**Coverage Complexity Chart** - Sonar Plugin

The **J3C Sonar Plugin** is a visualization technique that is useful to quick get an idea of how difficult is it to change / maintain your code. It combines the *Cyclomatic Complexity* and cod *Coverage* from automated tests and reveals if the code contains high risk to change methods. This plugin is inspired from [crap4j](http://www.crap4j.org/faq.html "crap4j") project and use the same heuristic Complexity / Coverage Thresholds in order to interpret and render the results.

| Complexity    | Coverage %  |
| ------------- |-------------|
| 0 – 5         | 0%          |
| 6 – 10        | 42%         |
| 11 – 15       | 57%         |
| 16 – 20       | 71%         |
| 21 – 25       | 80%         |
| 26 – 30       | 100%        |
| 31+           | Time to refactor |

The both Coverage and Complexity metrics can spot the code that is associated with an increased risk of breaking an existing piece of code when it’s being modified:

- excessive code complexity increase probability of introducing defects during maintenance
- without automated tests we cannot be sure that the changes have not introduced regressions


![Coverage Complexity Chart](https://drive.google.com/uc?id=0B9tMA3RbZ5P_TXZaRTNUOFNybGM)

The **J3C Sonar Plugin** leverages [JaCoCo](http://www.eclemma.org/jacoco/trunk/index.html) library to analyze and extract methods coverage and complexity metrics (more details [here](http://www.eclemma.org/jacoco/trunk/doc/counters.html)), groups methods by complexity, computes the average coverage and aggregate the results as an bar chart.

The key elements in the chart above are:

- the green bars represent the average coverage for related complexity
- the red bars (if is the case) represent the difference between recommended coverage and the actual one
- the light yellow color is used to show the boundaries between desired / risky coverage areas

By analysing the chart above we can see that even the code coverage is 72%, there are code areas hard and risky to change.

In the chart below is presented another project (a smaller one) with a lower change code risk.

![Coverage Complexity Chart](https://drive.google.com/uc?id=0B9tMA3RbZ5P_cG9jM0hJLUhmcmM)






