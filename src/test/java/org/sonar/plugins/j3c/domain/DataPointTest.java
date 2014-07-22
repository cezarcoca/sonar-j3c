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

import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.Test;

import static edu.emory.mathcs.backport.java.util.Arrays.asList;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by ccoca
 */
public class DataPointTest {

  @Test
  public void shouldComputeCorrectAverageWhenCoveragesListIsNull() {

    DataPoint dataPoint = new DataPoint(10, null);

    assertEquals("Correct average is computed", 0, dataPoint.getCoverage());
  }

  @Test
  public void shouldComputeCorrectAverageWhenCoveragesListIsEmpty() {

    DataPoint dataPoint = new DataPoint(10, Collections.emptyList());

    assertEquals("Correct average is computed", 0, dataPoint.getCoverage());
  }

  @Test
  public void shouldComputeCorrectAverageWhenCoveragesListIsNotEmpty() {

    DataPoint dataPoint = new DataPoint(10, asList(new Integer[]{22, 33, 42}));

    assertEquals("Correct complexity", 10, dataPoint.getComplexity());
    assertEquals("Correct average is computed", 32, dataPoint.getCoverage());
  }

  @Test
  public void shouldComputeCorrectAverageWhenAllCoveragesAreZero() {

    DataPoint dataPoint = new DataPoint(10, asList(new Integer[]{0, 0, 0}));

    assertEquals("Correct average is computed", 0, dataPoint.getCoverage());
  }

  @Test
  public void shouldBeWellFormedAsJSONRepresentations() {

    DataPoint dataPoint = new DataPoint(10, asList(new Integer[]{75}));
    String actual = dataPoint.serializeAsJson();

    String expected = "{\"cc\":10,\"co\":75}";
    assertThat(actual, equalToIgnoringCase(expected));
  }
}
