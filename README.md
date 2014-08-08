## sonar-j3c ##

**Coverage Complexity Chart** - Sonar Plugin

The **J3C Sonar Plugin** is a visualization technique that is useful to quickly get an idea of how difficult it is to change / maintain your code. It combines the *Cyclomatic Complexity* and code *Coverage* from automated tests and reveals if the code contains areas that are dangerous to change.

The *Cyclomatic Complexity* and *Coverage* metrics were chosen because they can spot the code that is associated with an increased risk of breaking an existing functionality:

- excessive code complexity increases probability of introducing defects during maintenance
- without automated tests we cannot be sure that the changes have not introduced regressions

This plugin is inspired from [crap4j](http://www.crap4j.org/faq.html "crap4j") project and uses the same heuristic Complexity / Coverage Thresholds in order to interpret and render the results.

| Complexity    | Coverage %  |
| ------------- |-------------|
| 0 – 5         | 0%          |
| 6 – 10        | 42%         |
| 11 – 15       | 57%         |
| 16 – 20       | 71%         |
| 21 – 25       | 80%         |
| 26 – 30       | 100%        |
| 31+           | Time to refactor |



![Coverage Complexity Chart](https://drive.google.com/uc?id=0B9tMA3RbZ5P_TXZaRTNUOFNybGM)

The **J3C Sonar Plugin** leverages [JaCoCo](http://www.eclemma.org/jacoco/trunk/index.html) library to analyze and extract methods coverage and complexity metrics (more details [here](http://www.eclemma.org/jacoco/trunk/doc/counters.html) and [here](http://www.eclemma.org/jacoco/trunk/doc/flow.html)), groups methods by complexity, computes the average coverage and aggregates the results as a bar chart.

The key elements in the chart above are:

- the green bars represent the average coverage for related complexity
- the red bars (if is the case) represent the difference between recommended coverage and the actual one
- the light yellow color is used to show the boundaries between desired and risky coverage areas

By analyzing the chart above we can see that even the total code line coverage reported by [JaCoCo](http://www.eclemma.org/jacoco/trunk/index.html) is 72%, there are code areas hard and risky to change.

In the chart below is presented another project (a smaller one) with a lower change code risk.

![Coverage Complexity Chart](https://drive.google.com/uc?id=0B9tMA3RbZ5P_ZkRrdkNHZFhyVzA)

By examining this chart, the red bar immediately draws the attention to the methods having *Cyclomatic Complexity* equals with **7**. Looking closer to the code, we found out **2** methods having this complexity. The first one has **45%** and the second has **15%** test coverage resulting in an average value equal to **30%**. By increasing the test coverage for the second method we can lower the risk of introducing defects when change is requested.

**How to build**

Clone a copy of the main **J3C Sonar Plugin** git repository by running:

    git clone https://github.com/cezarcoca/sonar-j3c.git

Enter the *sonar-j3c* directory

    cd sonar-j3c
 
Run the build script:

    mvn clean package

**Installation**

1. Copy the plugin into the *SONARQUBE_HOME/extensions/plugins directory*
2. Restart the **SonarQube** server (version 4.3+)







