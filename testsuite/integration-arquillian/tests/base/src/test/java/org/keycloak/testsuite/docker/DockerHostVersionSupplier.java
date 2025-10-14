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
package org.keycloak.testsuite.docker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DockerHostVersionSupplier implements Supplier<Optional<DockerVersion>> {
    private static final Logger log = LoggerFactory.getLogger(DockerHostVersionSupplier.class);

    @Override
    public Optional<DockerVersion> get() {
        try {
            Process process = new ProcessBuilder()
                    .command("docker", "version", "--format", "'{{.Client.Version}}'")
                    .start();

            final BufferedReader stdout = getReader(process, Process::getInputStream);
            final BufferedReader err = getReader(process, Process::getErrorStream);

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                final String versionString = stdout.lines().collect(Collectors.joining()).replaceAll("'", "");
                return Optional.ofNullable(DockerVersion.parseVersionString(versionString));
            }
        } catch (IOException | InterruptedException e) {
            log.error("Could not determine host machine's docker version: ", e);
        }

        return Optional.empty();
    }

    private static BufferedReader getReader(final Process process, final Function<Process, InputStream> streamSelector) {
        return new BufferedReader(new InputStreamReader(streamSelector.apply(process)));
    }
}
