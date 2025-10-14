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
package org.keycloak.vault;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesKeystoreVaultProviderFactory extends AbstractVaultProviderFactory {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());

    public static final String PROVIDER_ID = "files-keystore";

    private Path keystoreFile;
    private String keystorePass;
    private String keystoreType;

    @Override
    public VaultProvider create(KeycloakSession session) {
        if (keystoreFile == null) {
            logger.debug("Can not create a vault since it's not initialized correctly");
            return null;
        }
        return new FilesKeystoreVaultProvider(keystoreFile, keystorePass, keystoreType, getRealmName(session), super.keyResolvers);
    }

    @Override
    public void init(Config.Scope config) {
        super.init(config);

        String pathConfigProperty = config.get("file");
        if (pathConfigProperty == null) {
            logger.debug("Path to the vault keystore is not configured");
            return;
        }
        keystoreFile = Paths.get(pathConfigProperty);
        if (!Files.exists(keystoreFile)) {
            throw new VaultNotFoundException("The vault does not exist on the path " + keystoreFile.toAbsolutePath());
        }

        keystorePass = config.get("pass");
        if (keystorePass == null) {
            logger.debug("Password for the vault keystore is not configured");
            return;
        }

        keystoreType = config.get("type", "PKCS12");
        logger.debugf("A type of the provided keystore is %s", keystoreType);

        logger.debugf("Configured KeystoreVaultProviderFactory with the keystore file located in %s", keystoreFile.toString());
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
