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

package org.sonar.plugins.j3c;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ccoca
 */
public class J3cMetrics implements Metrics {

  /**
   * The Toxicity Chart metric
   */
  public static final Metric J3C_DATA_SET = new Metric.Builder(
      "j3c_data_set", "Coverage - Complexity Chart Data set", Metric.ValueType.DATA)
      .setDescription("Coverage - Complexity Chart Data set encoded as JSON.")
      .setDirection(Metric.DIRECTION_NONE).setQualitative(Boolean.TRUE)
      .setDeleteHistoricalData(Boolean.FALSE)
      .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

  @Override
  public List<Metric> getMetrics() {
    return Arrays.asList(J3C_DATA_SET);
  }
}
