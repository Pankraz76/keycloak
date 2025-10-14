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
package org.keycloak.tests.admin;

import org.junit.jupiter.api.Test;
import org.keycloak.TokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.crypto.Algorithm;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.testframework.annotations.InjectAdminClient;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.realm.ManagedRealm;

import static org.junit.jupiter.api.Assertions.assertEquals;

@KeycloakIntegrationTest
public class AdminSignatureAlgorithmTest {

    @InjectAdminClient
    Keycloak admin;

    @InjectRealm(attachTo = "master")
    ManagedRealm masterRealm;

    @Test
    public void changeRealmTokenAlgorithm() throws Exception {
        masterRealm.updateWithCleanup(r -> r.defaultSignatureAlgorithm(Algorithm.ES256));

        admin.tokenManager().invalidate(admin.tokenManager().getAccessTokenString());
        AccessTokenResponse accessToken = admin.tokenManager().getAccessToken();
        TokenVerifier<AccessToken> verifier = TokenVerifier.create(accessToken.getToken(), AccessToken.class);
        assertEquals(Algorithm.ES256, verifier.getHeader().getAlgorithm().name());
    }
}
