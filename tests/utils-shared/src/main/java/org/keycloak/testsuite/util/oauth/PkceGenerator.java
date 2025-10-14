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

import org.keycloak.OAuth2Constants;
import org.keycloak.protocol.oidc.utils.PkceUtils;

public class PkceGenerator {

    private final String codeVerifier;
    private final String codeChallenge;
    private final String codeChallengeMethod;

    public static PkceGenerator s256() {
        String codeVerifier = PkceUtils.generateCodeVerifier();
        String codeChallenge = PkceUtils.generateS256CodeChallenge(codeVerifier);
        String codeChallengeMethod = OAuth2Constants.PKCE_METHOD_S256;
        return new PkceGenerator(codeVerifier, codeChallenge, codeChallengeMethod);
    }

    public static PkceGenerator plain() {
        String codeVerifier = PkceUtils.generateCodeVerifier();
        String codeChallengeMethod = OAuth2Constants.PKCE_METHOD_PLAIN;
        return new PkceGenerator(codeVerifier, codeVerifier, codeChallengeMethod);
    }

    private PkceGenerator(String codeVerifier, String codeChallenge, String codeChallengeMethod) {
        this.codeVerifier = codeVerifier;
        this.codeChallenge = codeChallenge;
        this.codeChallengeMethod = codeChallengeMethod;
    }

    public String getCodeVerifier() {
        return codeVerifier;
    }

    public String getCodeChallenge() {
        return codeChallenge;
    }

    public String getCodeChallengeMethod() {
        return codeChallengeMethod;
    }

}
