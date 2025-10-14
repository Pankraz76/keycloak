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
package org.keycloak.config;

import java.nio.file.Path;

public class VaultOptions {

    public enum VaultType {

        file("file"),
        keystore("keystore");

        private final String provider;

        VaultType(String provider) {
            this.provider = provider;
        }

        public String getProvider() {
            return provider;
        }
    }

    public static final Option<VaultOptions.VaultType> VAULT = new OptionBuilder<>("vault", VaultType.class)
            .category(OptionCategory.VAULT)
            .description("Enables a vault provider.")
            .buildTime(true)
            .build();

    public static final Option<Path>  VAULT_DIR = new OptionBuilder<>("vault-dir", Path.class)
            .category(OptionCategory.VAULT)
            .description("If set, secrets can be obtained by reading the content of files within the given directory.")
            .build();

    public static final Option<String>  VAULT_PASS = new OptionBuilder<>("vault-pass", String.class)
            .category(OptionCategory.VAULT)
            .description("Password for the vault keystore.")
            .build();

    public static final Option<Path>  VAULT_FILE = new OptionBuilder<>("vault-file", Path.class)
            .category(OptionCategory.VAULT)
            .description("Path to the keystore file.")
            .build();

    public static final Option<String>  VAULT_TYPE = new OptionBuilder<>("vault-type", String.class)
            .category(OptionCategory.VAULT)
            .description("Specifies the type of the keystore file.")
            .defaultValue("PKCS12")
            .build();
}
