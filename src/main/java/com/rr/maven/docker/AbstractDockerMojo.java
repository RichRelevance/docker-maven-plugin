/**
 * Copyright (C) 2015 RichRelevance (${email})
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.rr.maven.docker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

abstract public class AbstractDockerMojo extends AbstractMojo {

  /**
   * This is a custom parameter for selecting where the docker server is. In this case the default is localhost:4243.
   */
  @Parameter(defaultValue = "tcp://localhost:4243", required = true)
  String dockerHost;
  /**
   * This is the project build dir (typically target/) for the whole maven project
   */
  @Parameter(defaultValue = "${project.build.directory}", required = true)
  private String buildDir;
  /**
   * This is the project finalName usually a shorten version for example if the full versions is com.rr.package:ArtifactId:1.1.0
   * this will be just ArtifactId
   */
  @Parameter(defaultValue = "${project.artifactId}", required = true)
  private String artifactId;
  /**
   * This is the maven version currently set in the poms
   */
  @Parameter(defaultValue = "${project.version}", required = true)
  private String projversion;
  /**
   * This is a custom parameter for the remote docker repo it defaults to localhost:5000. This is typically used as part of the
   * tag so it knows where to push the image too when you call docker push
   */
  @Parameter(defaultValue = "localhost:5000", required = true)
  private String dockerRepo;

  /**
   * Method that returns the FQDN for the tag IE:  localhost:5000/ArtifactId:1.1.0
   *
   * @return String
   */
  String getDockerTagId() {
    return dockerRepo + "/" + artifactId.toLowerCase() + ":" + projversion;
  }

  /**
   * Sorten method to return a File instead of the string that we get from buildDir
   *
   * @return File
   */
  File getBuildDir() {
    return new File(buildDir);
  }
}
