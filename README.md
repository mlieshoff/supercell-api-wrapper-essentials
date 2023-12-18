[![](https://img.shields.io/badge/java-packagecloud.io-844fec.svg)](https://packagecloud.io/)
[![Nightlies](https://github.com/mlieshoff/supercell-connectors/actions/workflows/nightlies.yml/badge.svg)](https://github.com/mlieshoff/supercell-connectors/actions/workflows/nightlies.yml)

# supercell-api-wrapper-essentials 1.0.0
Essentials for Java wrappers of official Supercell Clash Royal / Brawl Stars Api's

### Usage

#### Release Version Checker 
```xml
    <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <executions>
            <execution>
                <id>ReleaseVersion</id>
                <phase>prepare-package</phase>
                <goals>
                    <goal>java</goal>
                </goals>
                <configuration>
                    <mainClass>supercell.api.wrapper.essentials.build.ReleaseVersionChecker</mainClass>
                    <arguments>
                        <argument>${file_with_version_as_simple_line_of_string}</argument>
                        <argument>${maven_pom_file}</argument>
                    </arguments>
                </configuration>
            </execution>
        </executions>
    </plugin>
```

## How to bind the packagecloud repository

```xml
    <repositories>
        <repository>
            <id>packagecloud-jcrapi2</id>
            <url>https://packagecloud.io/mlieshoff/supercell-api-wrapper-essentials/maven2</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```

## Add dependency

to Gradle:
```groovy
    implementation group: 'supercell-api-wrapper-essentials', name: 'supercell-api-wrapper-essentials', version: '1.0.0'
```

to Maven:
```xml
    <dependency>
        <groupId>supercell-api-wrapper-essentials</groupId>
        <artifactId>supercell-api-wrapper-essentials</artifactId>
        <version>1.0.0</version>
    </dependency>
```

## Continuous Integration

https://github.com/mlieshoff/supercell-api-wrapper-essentials/actions

## Repository

https://packagecloud.io/mlieshoff/supercell-api-wrapper-essentials

## Logging

We are using SLF4j.
