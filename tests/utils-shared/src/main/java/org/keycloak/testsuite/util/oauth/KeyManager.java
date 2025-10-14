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
package org.keycloak.testsuite.util.oauth;

import org.keycloak.crypto.KeyUse;
import org.keycloak.crypto.KeyWrapper;
import org.keycloak.jose.jwk.JSONWebKeySet;
import org.keycloak.jose.jwk.JWK;
import org.keycloak.jose.jwk.JWKParser;
import org.keycloak.jose.jwk.OKPPublicJWK;

import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class KeyManager {

    private final AbstractOAuthClient<?> client;
    private final Map<String, JSONWebKeySet> publicKeys = new HashMap<>();

    KeyManager(AbstractOAuthClient<?>  client) {
        this.client = client;
    }

    public KeyWrapper getPublicKey(String algorithm, String kid) {
        boolean loadedKeysFromServer = false;
        JSONWebKeySet jsonWebKeySet = publicKeys.get(client.getRealm());
        if (jsonWebKeySet == null) {
            jsonWebKeySet = getRealmKeys();
            publicKeys.put(client.getRealm(), jsonWebKeySet);
            loadedKeysFromServer = true;
        }

        KeyWrapper key = findKey(jsonWebKeySet, algorithm, kid);

        if (key == null && !loadedKeysFromServer) {
            jsonWebKeySet = getRealmKeys();
            publicKeys.put(client.getRealm(), jsonWebKeySet);

            key = findKey(jsonWebKeySet, algorithm, kid);
        }

        if (key == null) {
            throw new RuntimeException("Public key for realm:" + client.getRealm() + ", algorithm: " + algorithm + " not found");
        }

        return key;
    }

    public JSONWebKeySet getRealmKeys() {
        try {
            return new JwksRequest(client).send();
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve keys", e);
        }
    }

    private KeyWrapper findKey(JSONWebKeySet jsonWebKeySet, String algorithm, String kid) {
        for (JWK k : jsonWebKeySet.getKeys()) {
            if (k.getKeyId().equals(kid) && k.getAlgorithm().equals(algorithm)) {
                PublicKey publicKey = JWKParser.create(k).toPublicKey();

                KeyWrapper key = new KeyWrapper();
                key.setKid(k.getKeyId());
                key.setAlgorithm(k.getAlgorithm());
                if (k.getOtherClaim(OKPPublicJWK.CRV, String.class) != null) {
                    key.setCurve(k.getOtherClaim(OKPPublicJWK.CRV, String.class));
                }
                key.setPublicKey(publicKey);
                key.setUse(KeyUse.SIG);

                return key;
            }
        }
        return null;
    }

}
