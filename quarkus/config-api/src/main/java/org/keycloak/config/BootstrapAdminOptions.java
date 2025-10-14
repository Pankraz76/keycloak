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

public class BootstrapAdminOptions {
    
    public static final String DEFAULT_TEMP_ADMIN_USERNAME = "temp-admin";
    public static final String DEFAULT_TEMP_ADMIN_SERVICE = DEFAULT_TEMP_ADMIN_USERNAME;
    public static final int DEFAULT_TEMP_ADMIN_EXPIRATION = 120;
    private static final String USED_ONLY_WHEN = " Used only when the master realm is created.";
    private static final String NON_CLI = " Use a non-CLI configuration option for this option if possible.";

    public static final Option<String> PASSWORD = new OptionBuilder<>("bootstrap-admin-password", String.class)
            .category(OptionCategory.BOOTSTRAP_ADMIN)
            .description("Temporary bootstrap admin password." + USED_ONLY_WHEN + NON_CLI)
            .build();

    public static final Option<String> USERNAME = new OptionBuilder<>("bootstrap-admin-username", String.class)
            .category(OptionCategory.BOOTSTRAP_ADMIN)
            .description("Temporary bootstrap admin username." + USED_ONLY_WHEN)
            .defaultValue(DEFAULT_TEMP_ADMIN_USERNAME)
            .build();

    public static final Option<Integer> EXPIRATION = new OptionBuilder<>("bootstrap-admin-expiration", Integer.class)
            .category(OptionCategory.BOOTSTRAP_ADMIN)
            .description("Time in minutes for the bootstrap admin user to expire." + USED_ONLY_WHEN)
            .hidden()
            .build();

    public static final Option<String> CLIENT_ID = new OptionBuilder<>("bootstrap-admin-client-id", String.class)
            .category(OptionCategory.BOOTSTRAP_ADMIN)
            .description("Client id for the temporary bootstrap admin service account." + USED_ONLY_WHEN)
            .defaultValue(DEFAULT_TEMP_ADMIN_SERVICE)
            .build();

    public static final Option<String> CLIENT_SECRET = new OptionBuilder<>("bootstrap-admin-client-secret", String.class)
            .category(OptionCategory.BOOTSTRAP_ADMIN)
            .description("Client secret for the temporary bootstrap admin service account." + USED_ONLY_WHEN + NON_CLI)
            .build();

}
