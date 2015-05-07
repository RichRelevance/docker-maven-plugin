docker-maven-plugin
===================

A simple plugin for building docker containers are part of your maven build pipeline


an example pom would be

```XML
 <build>
    <plugins>
      <plugin>
        <groupId>com.rr.maven</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>1.0.1</version>
        <configuration>
          <baseImage>centos:latest</baseImage>
          <maintainer>ops@company.com</maintainer>
          <dockerRepo>docker.io</dockerRepo>
          <dockerHost>localhost:4243</dockerHost>
          <commandList>
            <param>ADD target/app/scripts/startup-docker.sh /srv/app/startup.sh</param>
            <param>RUN chmod 1777 /srv/app/startup.sh</param>
            <param>ADD target/${project.build.finalName}-withdeps.jar /srv/app/lib/${project.build.finalName}-withdeps.jar</param>
            <param>ADD config/config.properties /srv/app/config/config.properties</param>
          </commandList>
          <exposePorts>
            <param>8000</param>
            <param>8080</param>
          </exposePorts>
          <cmd>/srv/app/startup.sh</cmd>
        </configuration>
          <executions>
            <execution>
              <id>cleanContainers</id>
              <goals>
                <goal>cleanContainers</goal>
              </goals>
              <phase>clean</phase>
            </execution>
            <execution>
              <id>buildDockerfile</id>
              <goals>
                <goal>buildDockerfile</goal>
              </goals>
              <phase>package</phase>
            </execution>
            <execution>
              <id>packageContainer</id>
              <goals>
                <goal>packageContainer</goal>
              </goals>
              <phase>install</phase>
            </execution>
            <execution>
              <id>pushContainer</id>
              <goals>
                <goal>pushContainer</goal>
              </goals>
              <phase>deploy</phase>
            </execution>
          </executions>
      </plugin>
    </plugins>
  </build>
```
