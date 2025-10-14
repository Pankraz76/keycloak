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

public class HostnameV2Options {

    public static final Option<String> HOSTNAME = new OptionBuilder<>("hostname", String.class)
            .category(OptionCategory.HOSTNAME_V2)
            .description("Address at which is the server exposed. Can be a full URL, or just a hostname. When only hostname is provided, scheme, port and context path are resolved from the request.")
            .build();

    public static final Option<String> HOSTNAME_ADMIN = new OptionBuilder<>("hostname-admin", String.class)
            .category(OptionCategory.HOSTNAME_V2)
            .description("Address for accessing the administration console. Use this option if you are exposing the administration console using a reverse proxy on a different address than specified in the 'hostname' option.")
            .build();

    public static final Option<Boolean> HOSTNAME_BACKCHANNEL_DYNAMIC = new OptionBuilder<>("hostname-backchannel-dynamic", Boolean.class)
            .category(OptionCategory.HOSTNAME_V2)
            .description("Enables dynamic resolving of backchannel URLs, including hostname, scheme, port and context path. Set to true if your application accesses Keycloak via a private network. If set to true, 'hostname' option needs to be specified as a full URL.")
            .defaultValue(Boolean.FALSE)
            .build();

    public static final Option<Boolean> HOSTNAME_STRICT = new OptionBuilder<>("hostname-strict", Boolean.class)
            .category(OptionCategory.HOSTNAME_V2)
            .description("Disables dynamically resolving the hostname from request headers. Should always be set to true in production, unless your reverse proxy overwrites the Host header. If enabled, the 'hostname' option needs to be specified.")
            .defaultValue(Boolean.TRUE)
            .build();

    public static final Option<Boolean> HOSTNAME_DEBUG = new OptionBuilder<>("hostname-debug", Boolean.class)
            .category(OptionCategory.HOSTNAME_V2)
            .description("Toggles the hostname debug page that is accessible at /realms/master/hostname-debug.")
            .defaultValue(Boolean.FALSE)
            .build();

}
