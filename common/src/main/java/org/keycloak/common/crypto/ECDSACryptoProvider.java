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
package org.keycloak.common.crypto;

import java.io.IOException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public interface ECDSACryptoProvider {
    
    public byte[] concatenatedRSToASN1DER(final byte[] signature, int signLength) throws IOException;

    public byte[] asn1derToConcatenatedRS(final byte[] derEncodedSignatureValue, int signLength) throws IOException;

    public ECPublicKey getPublicFromPrivate(ECPrivateKey ecPrivateKey);
}
