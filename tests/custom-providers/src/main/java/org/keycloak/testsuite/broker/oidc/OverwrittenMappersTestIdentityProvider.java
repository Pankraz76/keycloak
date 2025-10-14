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
package org.keycloak.testsuite.broker.oidc;

import org.keycloak.broker.oidc.KeycloakOIDCIdentityProvider;
import org.keycloak.broker.oidc.KeycloakOIDCIdentityProviderFactory;
import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.broker.provider.IdentityProviderMapper;
import org.keycloak.models.KeycloakSession;

import java.util.Arrays;
import java.util.List;

/**
 * @author Daniel Fesenmeyer <daniel.fesenmeyer@bosch.com>
 */
public class OverwrittenMappersTestIdentityProvider extends KeycloakOIDCIdentityProvider {

    public OverwrittenMappersTestIdentityProvider(KeycloakSession session, OIDCIdentityProviderConfig config) {
        super(session, config);
    }

    @Override
    public boolean isMapperSupported(IdentityProviderMapper mapper) {
        List<String> compatibleIdps = Arrays.asList(mapper.getCompatibleProviders());

        // provide the same mappers as are available for the parent provider (Keycloak-OIDC)
        return compatibleIdps.contains(IdentityProviderMapper.ANY_PROVIDER)
                || compatibleIdps.contains(KeycloakOIDCIdentityProviderFactory.PROVIDER_ID);
    }

}
