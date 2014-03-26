package com.rr.maven.docker;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "cleanContainers", defaultPhase = LifecyclePhase.CLEAN, threadSafe = true)
public class DockerCleanMojo extends AbstractDockerMojo {

  /**
   * Runs before the build and removes all existing containers with this name
   *
   * @throws MojoExecutionException
   * @throws MojoFailureException
   */
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    File baseDir = getBuildDir();

    DockerHelper helper = new DockerHelperCli(dockerHost, baseDir, getDockerTagId());

    try {
      String output = helper.removeContainer();
      getLog().info(output);
    } catch (IOException | InterruptedException e) {
      getLog().error("Error removing container: ", e);
      throw new MojoExecutionException("Error removing container", e);
    }
  }
}
