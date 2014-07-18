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

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by ccoca
 */
public class DataPoint {

  private int complexity;
  private int coverage;
  private boolean computed;

  public DataPoint(Integer complexity, List<Integer> coverages) {
    this.complexity = complexity;
    computeAverageCoverage(coverages);
  }

  private void computeAverageCoverage(List<Integer> coverages) {

    if(coverages == null || coverages.isEmpty()) {
      computed = false;
      coverage = 0;
      return;
    }

    int total = 0;
    for(Integer value : coverages) {
      total += value;
    }
    coverage = total / coverages.size();
    computed = true;
  }

  public int getComplexity() {
    return complexity;
  }

  public int getCoverage() {
    return coverage;
  }

  public boolean isComputed() {
    return computed;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("complexity", complexity)
        .append("coverage", coverage).append("computed", computed).toString();
  }
}
