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

import com.google.common.collect.ImmutableList;
import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccoca
 */
public class J3cPlugin extends SonarPlugin {

  @Override
  public List<Class<? extends Extension>> getExtensions() {

    ImmutableList.Builder<Class<? extends Extension>> extensions = ImmutableList.builder();
    extensions.add(J3cConfiguration.class);
    extensions.add(J3cSensor.class);

    return extensions.build();
  }
}
