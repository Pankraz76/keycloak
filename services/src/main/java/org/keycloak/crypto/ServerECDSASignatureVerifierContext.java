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
package org.keycloak.crypto;

import org.keycloak.common.VerificationException;
import org.keycloak.models.KeycloakSession;

public class ServerECDSASignatureVerifierContext extends  AsymmetricSignatureVerifierContext {
    public ServerECDSASignatureVerifierContext(KeycloakSession session, String kid, String algorithm) throws VerificationException {
        super(ServerAsymmetricSignatureVerifierContext.getKey(session, kid, algorithm));
    }

    public ServerECDSASignatureVerifierContext(KeyWrapper key) {
        super(key);
    }

    @Override
    public boolean verify(byte[] data, byte[] signature) throws VerificationException {
        try {
            int expectedSize = ECDSAAlgorithm.getSignatureLength(getAlgorithm());
            byte[] derSignature = ECDSAAlgorithm.concatenatedRSToASN1DER(signature, expectedSize);
            return super.verify(data, derSignature);
        } catch (Exception e) {
            throw new VerificationException("Signing failed", e);
        }
    }
}
