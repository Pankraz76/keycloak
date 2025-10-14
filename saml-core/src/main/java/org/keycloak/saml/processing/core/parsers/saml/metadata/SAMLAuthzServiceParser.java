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
package org.keycloak.saml.processing.core.parsers.saml.metadata;

/**
 * @author mhajas
 */
public class SAMLAuthzServiceParser extends SAMLEndpointTypeParser {

    private static final SAMLAuthzServiceParser INSTANCE = new SAMLAuthzServiceParser();

    public SAMLAuthzServiceParser() {
        super(SAMLMetadataQNames.AUTHZ_SERVICE);
    }

    public static SAMLAuthzServiceParser getInstance() {
        return INSTANCE;
    }
}
