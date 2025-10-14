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
package org.keycloak.protocol.docker.installation.compose;

import java.net.URL;
import java.security.cert.Certificate;

public class DockerComposeZipContent {

    private final DockerComposeYamlFile yamlFile;
    private final String dataDirectoryName;
    private final DockerComposeCertsDirectory certsDirectory;

    public DockerComposeZipContent(final Certificate realmCert, final URL realmBaseUrl, final String realmName, final String clientId) {
        final String dataDirectoryName = "data";
        final String certsDirectoryName = "certs";
        final String registryCertFilename = "localhost.crt";
        final String registryKeyFilename = "localhost.key";
        final String idpCertTrustChainFilename = "localhost_trust_chain.pem";

        this.yamlFile = new DockerComposeYamlFile(dataDirectoryName, certsDirectoryName, "/opt/" + certsDirectoryName, registryCertFilename, registryKeyFilename, idpCertTrustChainFilename, realmBaseUrl, realmName, clientId);
        this.dataDirectoryName = dataDirectoryName;
        this.certsDirectory = new DockerComposeCertsDirectory(certsDirectoryName, realmCert, registryCertFilename, registryKeyFilename, idpCertTrustChainFilename, realmName);
    }

    public DockerComposeYamlFile getYamlFile() {
        return yamlFile;
    }

    public String getDataDirectoryName() {
        return dataDirectoryName;
    }

    public DockerComposeCertsDirectory getCertsDirectory() {
        return certsDirectory;
    }
}
