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

import org.keycloak.common.enums.HostnameVerificationPolicy;

import java.util.List;

public class TruststoreOptions {

    public static final Option<List<String>> TRUSTSTORE_PATHS = OptionBuilder.listOptionBuilder("truststore-paths", String.class)
            .category(OptionCategory.TRUSTSTORE)
            .description("List of pkcs12 (p12, pfx, or pkcs12 file extensions), PEM files, or directories containing those files that will be used as a system truststore.")
            .build();

    public static final Option<HostnameVerificationPolicy> HOSTNAME_VERIFICATION_POLICY = new OptionBuilder<>("tls-hostname-verifier", HostnameVerificationPolicy.class)
            .category(OptionCategory.TRUSTSTORE)
            .description("The TLS hostname verification policy for out-going HTTPS and SMTP requests. ANY should not be used in production.")
            .defaultValue(HostnameVerificationPolicy.DEFAULT)
            .deprecatedValues("STRICT and WILDCARD have been deprecated, use DEFAULT instead.", HostnameVerificationPolicy.STRICT, HostnameVerificationPolicy.WILDCARD)
            .build();

}
