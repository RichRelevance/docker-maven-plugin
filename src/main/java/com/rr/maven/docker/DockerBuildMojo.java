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
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "packageContainer", defaultPhase = LifecyclePhase.INSTALL)
public class DockerBuildMojo extends AbstractDockerMojo {

  /**
   * Magic happens here for the packageContainer goal; basically it calls the buildContainer method which needs to be done
   * after we have generated the Dockerfile
   *
   * @throws MojoExecutionException
   * @throws MojoFailureException
   */
  @Override
  public final void execute() throws MojoExecutionException, MojoFailureException {
    File baseDir = getBuildDir();

    DockerHelper helper = new DockerHelperCli(dockerHost, baseDir, getDockerTagId());

    try {
      String output = helper.buildContainer();
      getLog().info(output);
    } catch (IOException | InterruptedException e) {
      getLog().error("Error building container: ", e);
      throw new MojoExecutionException("Error building container", e);
    }
  }
}
