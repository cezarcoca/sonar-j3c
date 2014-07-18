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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by ccoca
 */
public class DataPointMatcher extends TypeSafeMatcher<DataPoint> {

  private DataPoint expected;

  private DataPointMatcher(DataPoint expected) {
    this.expected = expected;
  }

  @Override
  public boolean matchesSafely(DataPoint actual) {
    return actual.getComplexity() == expected.getComplexity()
        && actual.getCoverage() == expected.getCoverage()
        && actual.isComputed() == expected.isComputed();
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("complexity: " + expected.getComplexity()
        + " coverage: " + expected.getCoverage()
        + " computed: " + expected.isComputed());
  }

  public static Matcher<DataPoint> isSame(DataPoint expected) {
    return new DataPointMatcher(expected);
  }
}
