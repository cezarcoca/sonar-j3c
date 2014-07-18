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
import org.sonar.api.BatchExtension;
import org.sonar.api.CoreProperties;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.scan.filesystem.PathResolver;

import java.io.File;
import java.util.List;

/**
 * Created by ccoca
 */
public class J3cConfiguration implements BatchExtension {

  public static final String REPORT_PATH_PROPERTY = "sonar.jacoco.reportPath";
  public static final String INCLUDES_PROPERTY = "sonar.jacoco.includes";
  public static final String EXCLUDES_PROPERTY = "sonar.jacoco.excludes";

  private final Settings settings;
  private final FileSystem fileSystem;

  public J3cConfiguration(Settings settings, FileSystem fileSystem) {
    this.settings = settings;
    this.fileSystem = fileSystem;
  }

  boolean hasJavaFiles() {
    return fileSystem.hasFiles(fileSystem.predicates().hasLanguage("java"));
  }

  public String getReportPath() {
    return settings.getString(REPORT_PATH_PROPERTY);
  }

  public String getExcludes() {
    return this.settings.getString(EXCLUDES_PROPERTY);
  }

  public String getIncludes() {
    return this.settings.getString(INCLUDES_PROPERTY);
  }
}
