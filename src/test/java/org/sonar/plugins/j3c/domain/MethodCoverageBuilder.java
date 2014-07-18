/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2009 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.j3c.domain;

import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.IMethodCoverage;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ccoca
 */
public class MethodCoverageBuilder {

  public static int DEFAULT_COMPLEXITY = 10;
  public static double DEFAULT_COVERAGE = 0.6666;

  private int complexity;
  private double coverageRatio;

  private MethodCoverageBuilder() {
    complexity = DEFAULT_COMPLEXITY;
    coverageRatio = DEFAULT_COVERAGE;
  }

  public static final MethodCoverageBuilder aMethodCoverage() {
    return new MethodCoverageBuilder();
  }

  public MethodCoverageBuilder withComplexity(int complexity) {
    this.complexity = complexity;
    return this;
  }

  public MethodCoverageBuilder withCoverageRatio(double ratio) {
    this.coverageRatio = ratio;
    return this;
  }

  public IMethodCoverage build() {
    IMethodCoverage methodMock = mock(IMethodCoverage.class);
    ICounter counterMock = mock(ICounter.class);
    when(methodMock.getComplexityCounter()).thenReturn(counterMock);
    when(counterMock.getTotalCount()).thenReturn(complexity);
    when(counterMock.getCoveredRatio()).thenReturn(coverageRatio);

    return methodMock;
  }
}
