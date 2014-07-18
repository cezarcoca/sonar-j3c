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
import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ccoca
 */
public class DataPointTest {

  @Test
  public void shouldComputeCorrectAverageWhenCoveragesListIsNull() {

    DataPoint dataPoint = new DataPoint(10, null);

    assertEquals("Correct average is computed", 0, dataPoint.getCoverage());
    assertEquals("Computed flag is set accordingly", false, dataPoint.isComputed());
  }

  @Test
  public void shouldComputeCorrectAverageWhenCoveragesListIsEmpty() {

    DataPoint dataPoint = new DataPoint(10, Collections.emptyList());

    assertEquals("Correct average is computed", 0, dataPoint.getCoverage());
    assertEquals("Computed flag is set accordingly", false, dataPoint.isComputed());
  }

  @Test
  public void shouldComputeCorrectAverageWhenCoveragesListIsNotEmpty() {

    DataPoint dataPoint = new DataPoint(10, Lists.asList(22, new Integer[]{33, 42}));

    assertEquals("Correct complexity", 10, dataPoint.getComplexity());
    assertEquals("Correct average is computed", 32, dataPoint.getCoverage());
    assertEquals("Computed flag is set accordingly", true, dataPoint.isComputed());
  }

  @Test
  public void shouldComputeCorrectAverageWhenAllCoveragesAreZero() {

    DataPoint dataPoint = new DataPoint(10, Lists.asList(0, new Integer[]{0, 0}));

    assertEquals("Correct average is computed", 0, dataPoint.getCoverage());
    assertEquals("Computed flag is set accordingly", true, dataPoint.isComputed());
  }
}
