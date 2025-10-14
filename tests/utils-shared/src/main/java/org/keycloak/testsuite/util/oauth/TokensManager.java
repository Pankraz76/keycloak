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

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.crypto.Algorithm;
import org.keycloak.crypto.AsymmetricSignatureVerifierContext;
import org.keycloak.crypto.KeyWrapper;
import org.keycloak.crypto.ServerECDSASignatureVerifierContext;
import org.keycloak.jose.jws.JWSInput;
import org.keycloak.representations.JsonWebToken;

public class TokensManager {

    private final KeyManager keyManager;

    TokensManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public <T extends JsonWebToken> T verifyToken(String token, Class<T> clazz) {
        try {
            TokenVerifier<T> verifier = TokenVerifier.create(token, clazz);
            String kid = verifier.getHeader().getKeyId();
            String algorithm = verifier.getHeader().getAlgorithm().name();
            KeyWrapper key = keyManager.getPublicKey(algorithm, kid);
            AsymmetricSignatureVerifierContext verifierContext;
            switch (algorithm) {
                case Algorithm.ES256, Algorithm.ES384, Algorithm.ES512 ->
                        verifierContext = new ServerECDSASignatureVerifierContext(key);
                default -> verifierContext = new AsymmetricSignatureVerifierContext(key);
            }
            verifier.verifierContext(verifierContext);
            verifier.verify();
            return verifier.getToken();
        } catch (VerificationException e) {
            throw new RuntimeException("Failed to decode token", e);
        }
    }

    public <T extends JsonWebToken> T parseToken(String token, Class<T> clazz) {
        try {
            return new JWSInput(token).readJsonContent(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
