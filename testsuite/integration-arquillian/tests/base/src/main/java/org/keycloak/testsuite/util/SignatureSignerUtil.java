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
package org.keycloak.testsuite.util;

import org.keycloak.crypto.Algorithm;
import org.keycloak.crypto.AsymmetricSignatureSignerContext;
import org.keycloak.crypto.KeyWrapper;
import org.keycloak.crypto.ServerECDSASignatureSignerContext;
import org.keycloak.crypto.SignatureSignerContext;

import java.security.PrivateKey;

public class SignatureSignerUtil {

    public static SignatureSignerContext createSigner(PrivateKey privateKey, String kid, String algorithm) {
        return createSigner(privateKey, kid, algorithm, null);
    }

    public static SignatureSignerContext createSigner(PrivateKey privateKey, String kid, String algorithm, String curve) {
        KeyWrapper keyWrapper = new KeyWrapper();
        keyWrapper.setAlgorithm(algorithm);
        keyWrapper.setKid(kid);
        keyWrapper.setPrivateKey(privateKey);
        keyWrapper.setCurve(curve);
        SignatureSignerContext signer;
        switch (algorithm) {
            case Algorithm.ES256:
            case Algorithm.ES384:
            case Algorithm.ES512:
                signer = new ServerECDSASignatureSignerContext(keyWrapper);
                break;
            default:
                signer = new AsymmetricSignatureSignerContext(keyWrapper);
        }
        return signer;
    }
}
