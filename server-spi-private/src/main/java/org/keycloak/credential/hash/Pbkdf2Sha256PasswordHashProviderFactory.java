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
package org.keycloak.credential.hash;

import org.keycloak.models.KeycloakSession;

/**
 * PBKDF2 Password Hash provider with HMAC using SHA256
 *
 * @author <a href"mailto:abkaplan07@gmail.com">Adam Kaplan</a>
 */
public class Pbkdf2Sha256PasswordHashProviderFactory extends AbstractPbkdf2PasswordHashProviderFactory implements PasswordHashProviderFactory {

    public static final String ID = "pbkdf2-sha256";

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Hash iterations for PBKDF2-HMAC-SHA256 according to the <a href="https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#pbkdf2">Password Storage Cheat Sheet</a>.
     */
    public static final int DEFAULT_ITERATIONS = 600_000;

    @Override
    public PasswordHashProvider create(KeycloakSession session) {
        return new Pbkdf2PasswordHashProvider(ID, PBKDF2_ALGORITHM, DEFAULT_ITERATIONS, getMaxPaddingLength(), 256);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int order() {
        return 100;
    }
}
