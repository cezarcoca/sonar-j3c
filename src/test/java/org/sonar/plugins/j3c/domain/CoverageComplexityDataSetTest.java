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

import org.jacoco.core.analysis.IMethodCoverage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertThat;
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

    DataPoint expected = new DataPoint(DEFAULT_COMPLEXITY, asList(new Integer[]{66}));
    IMethodCoverage mc = aMethodCoverage().build();
    sut.add(mc);

    List<DataPoint> series = sut.getDataSet();
    DataPoint point = series.get(DEFAULT_COMPLEXITY);

    Assert.assertThat(point, DataPointMatcher.isSame(expected));
  }

  @Test
  public void shouldBeWellFormedAsJSONRepresentationIfDataSetIsEmpty() {

    String json = sut.serializeAsJson();

    String expected = "[]";
    assertThat(json, equalToIgnoringCase(expected));
  }

  @Test
  public void shouldBeWellFormedAsJSONRepresentationIfCoveragesAreAdded() {

    IMethodCoverage mc = aMethodCoverage().withComplexity(1).withCoverageRatio(0.667).build();
    sut.add(mc);

    String json = sut.serializeAsJson();

    String expected = "[{\"cc\":0,\"co\":0,\"comp\":false},{\"cc\":1,\"co\":66,\"comp\":true}]";
    assertThat(json, equalToIgnoringCase(expected));
  }
}
