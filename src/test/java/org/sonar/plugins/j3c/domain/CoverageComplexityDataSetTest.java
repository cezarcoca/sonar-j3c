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

import com.google.common.collect.Lists;
import org.jacoco.core.analysis.IMethodCoverage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.sonar.plugins.j3c.domain.MethodCoverageBuilder.DEFAULT_COMPLEXITY;
import static org.sonar.plugins.j3c.domain.MethodCoverageBuilder.aMethodCoverage;

/**
 * Created by ccoca
 */
public class CoverageComplexityDataSetTest {

  private CoverageComplexityDataSet sut;

  @Before
  public void setUp() {
    sut = new CoverageComplexityDataSet();
  }

  @Test
  public void shouldReturnEmptyListWhenNoCoveragesWereAdded() {
    List<DataPoint> series = sut.getDataSet();

    Assert.assertTrue("Series is empty", series.isEmpty());
  }

  @Test
  public void shouldComputeCorrectTheCoveragePercentage() {

    DataPoint expected = new DataPoint(DEFAULT_COMPLEXITY, Lists.asList(66, new Integer[]{}));
    IMethodCoverage mc = aMethodCoverage().build();
    sut.add(mc);

    List<DataPoint> series = sut.getDataSet();
    DataPoint point = series.get(DEFAULT_COMPLEXITY);

    Assert.assertThat(point, DataPointMatcher.isSame(expected));
  }
}
