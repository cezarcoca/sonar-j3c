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

package org.sonar.plugins.j3c.jacoco;

import com.google.common.base.Strings;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.IMethodCoverage;
import org.jacoco.core.data.ExecutionDataReader;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.runtime.WildcardMatcher;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.api.scan.filesystem.ModuleFileSystem;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.SonarException;
import org.sonar.plugins.j3c.J3cConfiguration;
import org.sonar.plugins.j3c.J3cLogger;
import org.sonar.plugins.j3c.J3cMetrics;
import org.sonar.plugins.j3c.domain.CoverageComplexityDataSet;
import org.sonar.plugins.java.api.JavaResourceLocator;

import java.io.File;
import java.io.FileInputStream;

import static org.sonar.plugins.j3c.J3cMetrics.J3C_DATA_SET;

/**
 * Created by ccoca
 */
public class JacocoAnalyzer {

  private final ResourcePerspectives perspectives;
  private final ModuleFileSystem fileSystem;
  private final PathResolver pathResolver;
  private final J3cConfiguration configuration;
  private final JavaResourceLocator javaResourceLocator;
  private final WildcardMatcher excludesMatcher;

  public JacocoAnalyzer(ResourcePerspectives perspectives, ModuleFileSystem fileSystem, PathResolver pathResolver, J3cConfiguration configuration, JavaResourceLocator javaResourceLocator) {
    this.perspectives = perspectives;
    this.fileSystem = fileSystem;
    this.pathResolver = pathResolver;
    this.configuration = configuration;
    this.javaResourceLocator = javaResourceLocator;
    this.excludesMatcher = new WildcardMatcher(Strings.nullToEmpty(configuration.getExcludes()));
  }

  public final void analyse(Project project, SensorContext context) {

    if (!atLeastOneBinaryDirectoryExists()) {
      J3cLogger.LOGGER.info("No JaCoCo analysis of project coverage can be done since there is no directories with classes.");
      return;
    }

    File jacocoExecutionData = pathResolver.relativeFile(fileSystem.baseDir(), getReportPath(project));

    try {
      ExecutionDataReader reader = new ExecutionDataReader(new FileInputStream(jacocoExecutionData));
      ExecutionDataVisitor visitor = new ExecutionDataVisitor();
      reader.setSessionInfoVisitor(visitor);
      reader.setExecutionDataVisitor(visitor);
      reader.read();

      CoverageComplexityDataSet coverageComplexityDataSet = new CoverageComplexityDataSet();
      CoverageBuilder coverageBuilder = analyze(visitor.getMerged());

      for (IClassCoverage classCoverage : coverageBuilder.getClasses()) {
        if (isExcluded(classCoverage) || isNotInScope(classCoverage, context)) {
          continue;
        }
        for (IMethodCoverage methodCoverage : classCoverage.getMethods()) {
          coverageComplexityDataSet.add(methodCoverage);
        }
      }
      saveMeasures(context, coverageComplexityDataSet);

    } catch (Exception e) {
      throw new SonarException(e);
    }
  }

  private boolean isExcluded(IClassCoverage coverage) {
    return excludesMatcher.matches(coverage.getName());
  }

  private boolean isNotInScope(IClassCoverage coverage, SensorContext context) {

    Resource resourceInContext = context.getResource(javaResourceLocator.findResourceByClassName(coverage.getName()));

    if (resourceInContext == null || ResourceUtils.isUnitTestClass(resourceInContext)) {
      J3cLogger.LOGGER.info("Not in scope: " + coverage.getName());
      return true;
    }

    return false;
  }

  protected String getReportPath(Project project) {
    return configuration.getReportPath();
  }

  private boolean atLeastOneBinaryDirectoryExists() {
    for (File binaryDir : fileSystem.binaryDirs()) {
      if (binaryDir.exists()) {
        return true;
      }
    }
    return false;
  }

  private CoverageBuilder analyze(ExecutionDataStore executionDataStore) {
    CoverageBuilder coverageBuilder = new CoverageBuilder();
    Analyzer analyzer = new Analyzer(executionDataStore, coverageBuilder);
    for (File binaryDir : fileSystem.binaryDirs()) {
      analyzeAll(analyzer, binaryDir);
    }
    return coverageBuilder;
  }

  private void saveMeasures(SensorContext context, CoverageComplexityDataSet dataSet) {
    context.saveMeasure(new Measure(J3C_DATA_SET, dataSet.serializeAsJson()));
  }

  /**
   * Copied from {@link Analyzer#analyzeAll(File)} in order to add logging.
   */
  private void analyzeAll(Analyzer analyzer, File file) {
    System.out.println("Analyze file: " + file.getName());
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        analyzeAll(analyzer, f);
      }
    } else if (file.getName().endsWith(".class")) {
      try {
        analyzer.analyzeAll(file);
      } catch (Exception e) {
        J3cLogger.LOGGER.error("Exception during analysis of file " + file.getAbsolutePath(), e);
      }
    }
  }
}
