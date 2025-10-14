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

import org.bouncycastle.crypto.CryptoServicesRegistrar;

/**
 * <p>A {@link FIPS1402Provider} that forces BC to run in FIPS approve mode by default.
 *
 * <p>In order to set the default mode the {@code org.bouncycastle.fips.approved_only} must be set. Otherwise,
 * calling {@link CryptoServicesRegistrar#setApprovedOnlyMode(boolean)} the mode is set on a per thread-basis and does not work
 * well when handling requests using multiple threads.
 */
public class Fips1402StrictCryptoProvider extends FIPS1402Provider {

    static {
        System.setProperty("org.bouncycastle.fips.approved_only", Boolean.TRUE.toString());
    }

    @Override
    public String[] getSupportedRsaKeySizes() {
        // RSA key of 1024 bits not supported in BCFIPS approved mode
        return new String[] {"2048", "3072", "4096"};
    }
}
