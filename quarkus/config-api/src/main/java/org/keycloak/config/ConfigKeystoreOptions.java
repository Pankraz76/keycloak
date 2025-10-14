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

public class ConfigKeystoreOptions {

    public static final Option<String> CONFIG_KEYSTORE = new OptionBuilder<>("config-keystore", String.class)
            .category(OptionCategory.CONFIG)
            .description("Specifies a path to the KeyStore Configuration Source.")
            .build();

    public static final Option<String> CONFIG_KEYSTORE_PASSWORD = new OptionBuilder<>("config-keystore-password", String.class)
            .category(OptionCategory.CONFIG)
            .description("Specifies a password to the KeyStore Configuration Source.")
            .build();

    public static final Option<String> CONFIG_KEYSTORE_TYPE = new OptionBuilder<>("config-keystore-type", String.class)
            .category(OptionCategory.CONFIG)
            .description("Specifies a type of the KeyStore Configuration Source.")
            .defaultValue("PKCS12")
            .build();

}
