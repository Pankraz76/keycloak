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
package org.keycloak.broker.spiffe;

import org.keycloak.crypto.PublicKeysWrapper;
import org.keycloak.http.simple.SimpleHttp;
import org.keycloak.jose.jwk.JWK;
import org.keycloak.keys.PublicKeyLoader;
import org.keycloak.models.KeycloakSession;
import org.keycloak.util.JWKSUtils;

public class SpiffeBundleEndpointLoader implements PublicKeyLoader {

    private final KeycloakSession session;
    private final String bundleEndpoint;

    public SpiffeBundleEndpointLoader(KeycloakSession session, String bundleEndpoint) {
        this.session = session;
        this.bundleEndpoint = bundleEndpoint;
    }

    @Override
    public PublicKeysWrapper loadKeys() throws Exception {
        SpiffeJSONWebKeySet jwks = SimpleHttp.create(session).doGet(bundleEndpoint).asJson(SpiffeJSONWebKeySet.class);
        PublicKeysWrapper keysWrapper = JWKSUtils.getKeyWrappersForUse(jwks, JWK.Use.JWT_SVID);
        return jwks.getSpiffeRefreshHint() == null ? keysWrapper : new PublicKeysWrapper(keysWrapper.getKeys(), jwks.getSpiffeRefreshHint());
    }

}
