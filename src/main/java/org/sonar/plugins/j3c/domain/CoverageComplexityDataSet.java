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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jacoco.core.analysis.IMethodCoverage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by ccoca
 */
public class CoverageComplexityDataSet {

  private Map<Integer, List<Integer>> dataSet;

  public CoverageComplexityDataSet() {
    this.dataSet = Maps.newTreeMap();
  }

  public void add(IMethodCoverage coverage) {

    Integer complexity = getComplexity(coverage);
    List<Integer> coverages = dataSet.get(complexity);
    if(coverages == null) {
      coverages = Lists.newArrayList();
      dataSet.put(complexity, coverages);
    }
    coverages.add(getCoveragePercentage(coverage));
  }

  private Integer getComplexity(IMethodCoverage coverage) {
    return Integer.valueOf(coverage.getComplexityCounter().getTotalCount());
  }

  private Integer getCoveragePercentage(IMethodCoverage coverage) {
    return Integer.valueOf((int)(coverage.getComplexityCounter().getCoveredRatio() * 100));
  }

  private List<DataPoint> getDataSet() {

    if(dataSet.isEmpty()) {
      return Collections.emptyList();
    }

    ImmutableList.Builder<DataPoint> series = ImmutableList.builder();

    for(Map.Entry<Integer, List<Integer>> entry : dataSet.entrySet()) {
      series.add(new DataPoint(entry.getKey(), entry.getValue()));
    }

    return series.build();
  }

  private Integer getMaxCyclomaticComplexity() {
    if(dataSet.isEmpty()) {
      return 0;
    }

    return Collections.max(dataSet.keySet());
  }

  public String serializeAsJson() {
    StringBuilder json = new StringBuilder();
    json.append("{");
    json.append("\"max_cc\":").append(getMaxCyclomaticComplexity());
    json.append(",\"data\":[");
    List<DataPoint> points = getDataSet();
    for(DataPoint dataPoint : points) {
      json.append(dataPoint.serializeAsJson()).append(",");
    }
    if(!points.isEmpty()) {
      json.deleteCharAt(json.length() - 1);
    }
    json.append("]");
    json.append("}");
    return json.toString();
  }
}
