/**
 * Swift SonarQube Plugin - Objective-C module - Enables analysis of Swift and Objective-C projects into SonarQube.
 * Copyright © 2015 Backelite (${email})
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.backelite.sonarqube.objectivec.issues.fauxpas;

import com.backelite.sonarqube.commons.Constants;
import com.backelite.sonarqube.objectivec.lang.core.ObjectiveC;
import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

import java.io.File;

public class FauxPasSensor implements Sensor {

    public static final String REPORT_PATH_KEY = Constants.PROPERTY_PREFIX
            + ".fauxpas.report";
    public static final String DEFAULT_REPORT_PATH = "sonar-reports/*fauxpas.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(FauxPasSensor.class);

    private final Settings conf;
    private final FileSystem fileSystem;
    private final ResourcePerspectives resourcePerspectives;
    private final Project project;

    public FauxPasSensor(final FileSystem moduleFileSystem, final Settings config, final ResourcePerspectives resourcePerspectives, final Project project) {
        this.conf = config;
        this.fileSystem = moduleFileSystem;
        this.resourcePerspectives = resourcePerspectives;
        this.project = project;
    }


    private void parseReportIn(final String baseDir, final FauxPasReportParser parser) {

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{reportPath()});
        scanner.setBasedir(baseDir);
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();

        for (String filename : files) {
            LOGGER.info("Processing FauxPas report {}", filename);
            parser.parseReport(new File(filename));
        }

    }

    private String reportPath() {
        String reportPath = conf.getString(REPORT_PATH_KEY);
        if (reportPath == null) {
            reportPath = DEFAULT_REPORT_PATH;
        }
        return reportPath;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .onlyOnLanguage(ObjectiveC.KEY)
                .name("FauxPas")
                .onlyOnFileType(InputFile.Type.MAIN);
    }

    @Override
    public void execute(SensorContext context) {

        final String projectBaseDir = fileSystem.baseDir().getPath();

        FauxPasReportParser parser = new FauxPasReportParser(project, context, resourcePerspectives, fileSystem);
        parseReportIn(projectBaseDir, parser);
    }
}
