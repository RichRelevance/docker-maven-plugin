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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;

import com.google.common.collect.ImmutableList;

/**
 * This is the class where we actually talk to the remote docker system. It currently just wraps the CLI commands
 */
public class DockerHelperCli implements DockerHelper {
  private static final String dockerCommand = "docker";

  private final String dockerHost;
  private final File workingDir;
  private final String dockerTag;


  public DockerHelperCli(String dockerHost, File workingDir, String dockerTag) {
    this.dockerHost = dockerHost;
    this.workingDir = workingDir;
    this.dockerTag = dockerTag;
  }


  /**
   * Builds the physical contain based on the Dockerfile generated earilyer.
   *
   * @return String - stdout
   * @throws IOException
   * @throws InterruptedException
   * @throws MojoFailureException
   */
  public String buildContainer() throws IOException, InterruptedException, MojoFailureException {
    List<String> commandList = new ImmutableList.Builder<String>()
      .add(dockerCommand)
      .add("-H")
      .add(dockerHost)
      .add("build")
      .add("-rm")
      .add("-t")
      .add(dockerTag)
      .add(".")
      .build();

    ProcessBuilder command = new ProcessBuilder(commandList);
    command.directory(workingDir);
    command.redirectErrorStream(true);

    Process process = command.start();

    String result = getOutput(process.getInputStream());

    process.waitFor();

    if (process.exitValue() > 0) {
      throw new MojoFailureException("Processes exited with a none-zero status output is: " + result);
    }

    return result;
  }

  /**
   * Pushes the container we just built to the remote system
   *
   * @return String - stdout
   * @throws IOException
   * @throws InterruptedException
   * @throws MojoFailureException
   */
  public String pushContainer() throws IOException, InterruptedException, MojoFailureException {
    List<String> commandList = new ImmutableList.Builder<String>()
      .add(dockerCommand)
      .add("-H")
      .add(dockerHost)
      .add("push")
      .add(dockerTag)
      .build();

    ProcessBuilder command = new ProcessBuilder(commandList);
    command.redirectErrorStream(true);

    Process process = command.start();

    String result = getOutput(process.getInputStream());

    process.waitFor();

    if (process.exitValue() > 0) {
      throw new MojoFailureException("Processes exited with a none-zero status output is: " + result);
    }

    return result;
  }

  /**
   * helper method for getting the stdout from the commands
   *
   * @param is InputStream
   * @return String - stdout
   * @throws IOException
   */
  private String getOutput(InputStream is) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    StringBuilder builder = new StringBuilder();
    String line = null;
    while ((line = br.readLine()) != null) {
      builder.append(line).append("\n");
    }
    return builder.toString();
  }
}