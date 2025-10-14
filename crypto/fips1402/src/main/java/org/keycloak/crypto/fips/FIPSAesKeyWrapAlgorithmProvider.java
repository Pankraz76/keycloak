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
package org.keycloak.crypto.fips;

import java.security.Key;

import org.bouncycastle.crypto.KeyUnwrapper;
import org.bouncycastle.crypto.KeyWrapper;
import org.bouncycastle.crypto.SymmetricKey;
import org.bouncycastle.crypto.SymmetricSecretKey;
import org.bouncycastle.crypto.fips.FipsAES;
import org.keycloak.jose.jwe.JWEHeader;
import org.keycloak.jose.jwe.JWEKeyStorage;
import org.keycloak.jose.jwe.JWEHeader.JWEHeaderBuilder;
import org.keycloak.jose.jwe.alg.JWEAlgorithmProvider;
import org.keycloak.jose.jwe.enc.JWEEncryptionProvider;

/**
 * Variant of {@link org.keycloak.jose.jwe.alg.AesKeyWrapAlgorithmProvider} based on FIPS
 */
public class FIPSAesKeyWrapAlgorithmProvider implements JWEAlgorithmProvider {

    @Override
    public byte[] decodeCek(byte[] encodedCek, Key encryptionKey, JWEHeader header, JWEEncryptionProvider encryptionProvider) throws Exception {
        byte[] keyBytes = encryptionKey.getEncoded(); // bytes making up AES key doing the wrapping
        SymmetricKey aesKey = new SymmetricSecretKey(FipsAES.KW, keyBytes);
        FipsAES.KeyWrapOperatorFactory factory = new FipsAES.KeyWrapOperatorFactory();
        KeyUnwrapper unwrapper = factory.createKeyUnwrapper(aesKey, FipsAES.KW);
        return unwrapper.unwrap(encodedCek, 0, encodedCek.length);
    }

    @Override
    public byte[] encodeCek(JWEEncryptionProvider encryptionProvider, JWEKeyStorage keyStorage, Key encryptionKey, JWEHeaderBuilder headerBuilder) throws Exception {
        byte[] inputKeyBytes = keyStorage.getCekBytes(); // bytes making up the key to be wrapped
        byte[] keyBytes = encryptionKey.getEncoded(); // bytes making up AES key doing the wrapping
        SymmetricKey aesKey = new SymmetricSecretKey(FipsAES.KW, keyBytes);
        FipsAES.KeyWrapOperatorFactory factory = new FipsAES.KeyWrapOperatorFactory();
        KeyWrapper wrapper = factory.createKeyWrapper(aesKey, FipsAES.KW);
        return wrapper.wrap(inputKeyBytes, 0, inputKeyBytes.length);
    }
}
