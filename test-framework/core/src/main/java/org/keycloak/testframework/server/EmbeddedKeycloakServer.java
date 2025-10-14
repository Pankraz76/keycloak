/*
 * Copyright 2025 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.testframework.server;

import io.quarkus.maven.dependency.Dependency;
import org.keycloak.Keycloak;
import org.keycloak.common.Version;
import org.keycloak.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class EmbeddedKeycloakServer implements KeycloakServer {

    private Keycloak keycloak;
    private Path homeDir;
    private boolean enableTls = false;

    @Override
    public void start(KeycloakServerConfigBuilder keycloakServerConfigBuilder) {
        Keycloak.Builder builder = Keycloak.builder().setVersion(Version.VERSION);
        enableTls = keycloakServerConfigBuilder.tlsEnabled();

        for(Dependency dependency : keycloakServerConfigBuilder.toDependencies()) {
            builder.addDependency(dependency.getGroupId(), dependency.getArtifactId(), "");
        }

        Set<Path> configFiles = keycloakServerConfigBuilder.toConfigFiles();
        if (!configFiles.isEmpty()) {
            if (homeDir == null) {
                homeDir = Platform.getPlatform().getTmpDirectory().toPath();
            }

            Path conf = homeDir.resolve("conf");

            if (!conf.toFile().exists()) {
                conf.toFile().mkdirs();
            }

            for (Path configFile : configFiles) {
                try {
                    Files.copy(configFile, conf.resolve(configFile.getFileName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        builder.setHomeDir(homeDir);
        keycloak = builder.start(keycloakServerConfigBuilder.toArgs());
    }

    @Override
    public void stop() {
        try {
            keycloak.stop();
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBaseUrl() {
        if (isTlsEnabled()) {
            return "https://localhost:8443";
        } else {
            return "http://localhost:8080";
        }
    }

    @Override
    public String getManagementBaseUrl() {
        if (isTlsEnabled()) {
            return "https://localhost:9001";
        } else {
            return "http://localhost:9001";
        }
    }

    @Override
    public boolean isTlsEnabled() {
        return enableTls;
    }
}
