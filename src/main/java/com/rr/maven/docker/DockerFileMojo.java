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
/*
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.rr.maven.docker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Generate the Dockerfile as specified in the pom using this plugin
 */
@Mojo(name = "buildDockerfile", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true)
public class DockerFileMojo extends AbstractDockerMojo {

  /**
   * POM file parameter for the "FROM" image in the Dockerfile
   */
  @Parameter(defaultValue = "centos", required = true)
  private String baseImage;

  /**
   * POM file parameter for specifying the maintainer in the Dockerfile
   */
  @Parameter(defaultValue = "")
  private String maintainer;

  /**
   * List of build level commands to run on the container (ADD, RUN, etc) order will be preserved
   */
  @Parameter(required = true)
  private List<String> commandList;

  /**
   * List of ports to expose from the container via the EXPOSE command in the Dockerfile
   */
  @Parameter
  private List<String> exposePorts;

  /**
   * The CMD line at the end of the file to specific the start command/script in the Dockerfile
   */
  @Parameter
  private String cmd;

  /**
   * This method gets called by maven to generate the Dockerfile this happens by default in the package phase
   *
   * @throws MojoExecutionException
   * @throws MojoFailureException
   */
  @Override
  public final void execute() throws MojoExecutionException, MojoFailureException {
    File baseDir = getBuildDir();

    if (!baseDir.exists()) {
      baseDir.mkdirs();
    }

    File dockerFile = new File(baseDir, "Dockerfile");

    try (PrintWriter writer = new PrintWriter(dockerFile, "UTF-8")) {
      writer.println("FROM " + baseImage);

      if (maintainer != null) {
        writer.println("MAINTAINER " + maintainer);
      }

      for (String command : commandList) {
        writer.println(command);
      }

      if (exposePorts != null) {
        for (String port : exposePorts) {
          writer.println("EXPOSE " + port);
        }
      }

      writer.println("CMD " + cmd);

    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      getLog().error("Error creating dockerfile", e);
      throw new MojoExecutionException("Error generating dockerfile", e);
    }
  }
}
