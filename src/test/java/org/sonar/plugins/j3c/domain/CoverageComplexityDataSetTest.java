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

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertThat;
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
  public void shouldHandleNoCoveragesCornerCase() {

    String json = sut.serializeAsJson();

    String expected = "{\"max_cc\":0,\"data\":[]}";
    assertThat(json, equalToIgnoringCase(expected));
  }

  @Test
  public void shouldComputeAverageCoverageForMethodsWithTheSameComplexity() {

    final int complexity = 10;
    sut.add(aMethodCoverage().withComplexity(complexity).withCoverageRatio(0.33).build());
    sut.add(aMethodCoverage().withComplexity(complexity).withCoverageRatio(0.66).build());

    String json = sut.serializeAsJson();

    String expected = "{\"max_cc\":" + complexity + ",\"data\":[{\"cc\":" + complexity + ",\"co\":" + 49 + "}]}";
    assertThat(json, equalToIgnoringCase(expected));
  }

  @Test
  public void shouldBeSortedByCyclomaticComplexity() {

    sut.add(aMethodCoverage().withComplexity(10).withCoverageRatio(0.33).build());
    sut.add(aMethodCoverage().withComplexity(1).withCoverageRatio(0.66).build());

    String json = sut.serializeAsJson();

    String expected = "{\"max_cc\":10,\"data\":[{\"cc\":1,\"co\":66},{\"cc\":10,\"co\":33}]}";
    assertThat(json, equalToIgnoringCase(expected));
  }
}
