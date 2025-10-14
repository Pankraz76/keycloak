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

import org.keycloak.common.crypto.FipsMode;
import org.keycloak.credential.hash.Pbkdf2Sha512PasswordHashProviderFactory;
import org.keycloak.crypto.hash.Argon2Parameters;
import org.keycloak.crypto.hash.Argon2PasswordHashProviderFactory;
import org.keycloak.testsuite.arquillian.AuthServerTestEnricher;

public class DefaultPasswordHash {

    public static String getDefaultAlgorithm() {
        return notFips() ? Argon2PasswordHashProviderFactory.ID : Pbkdf2Sha512PasswordHashProviderFactory.ID;
    }

    public static int getDefaultIterations() {
        return notFips() ? Argon2Parameters.DEFAULT_ITERATIONS : Pbkdf2Sha512PasswordHashProviderFactory.DEFAULT_ITERATIONS;
    }

    private static boolean notFips() {
        return AuthServerTestEnricher.AUTH_SERVER_FIPS_MODE == FipsMode.DISABLED;
    }

}
