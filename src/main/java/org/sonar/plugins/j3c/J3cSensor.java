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

import org.sonar.api.batch.DependsUpon;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Project;
import org.sonar.api.scan.filesystem.ModuleFileSystem;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.plugins.j3c.jacoco.JacocoAnalyzer;
import org.sonar.plugins.java.api.JavaResourceLocator;

/**
 * Created by ccoca
 */
public class J3cSensor implements Sensor {

  private final J3cConfiguration configuration;
  private final ModuleFileSystem fileSystem;
  private final PathResolver pathResolver;
  private final JavaResourceLocator javaResourceLocator;

  public J3cSensor(J3cConfiguration configuration, ModuleFileSystem fileSystem, PathResolver pathResolver, JavaResourceLocator javaResourceLocator) {
    this.configuration = configuration;
    this.fileSystem = fileSystem;
    this.pathResolver = pathResolver;
    this.javaResourceLocator = javaResourceLocator;
  }

  /**
   * Should be executed after Surefire, which imports details of the tests.
   */
  @DependsUpon
  public String dependsOnSurefireSensors() {
    return "surefire-java";
  }

  @Override
  public void analyse(Project module, SensorContext context) {
    JacocoAnalyzer analyzer = new JacocoAnalyzer(fileSystem, pathResolver, configuration, javaResourceLocator);
    analyzer.analyse(context);
  }

  @Override
  public boolean shouldExecuteOnProject(Project project) {
    return configuration.hasJavaFiles() && project.getAnalysisType().isDynamic(true);
  }

}
