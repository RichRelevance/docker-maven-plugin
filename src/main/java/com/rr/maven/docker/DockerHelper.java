/**
 * Copyright (C) 2015 RichRelevance (${email})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

import java.io.IOException;

import org.apache.maven.plugin.MojoFailureException;

/**
 * Simple interface for interacting with the external docker system currently implemented by the CLI method but in the future
 * could use the API client instead.
 */
public interface DockerHelper {
  public String buildContainer() throws IOException, InterruptedException, MojoFailureException;

  public String pushContainer() throws IOException, InterruptedException, MojoFailureException;

  public String removeContainer() throws IOException, InterruptedException, MojoFailureException;
}
