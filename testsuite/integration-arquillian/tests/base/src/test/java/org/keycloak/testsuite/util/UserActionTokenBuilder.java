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

import org.keycloak.authentication.actiontoken.resetcred.ResetCredentialsActionToken;
import org.keycloak.authentication.actiontoken.verifyemail.VerifyEmailActionToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:bruno@abstractj.org">Bruno Oliveira</a>
 */
public class UserActionTokenBuilder {

    private final Map<String, String> realmAttributes;
    private static final String ATTR_PREFIX = "actionTokenGeneratedByUserLifespan.";

    private UserActionTokenBuilder(HashMap<String, String> attr) {
        realmAttributes = attr;
    }

    public static UserActionTokenBuilder create() {
        return new UserActionTokenBuilder(new HashMap<>());
    }

    public UserActionTokenBuilder resetCredentialsLifespan(int lifespan) {
        realmAttributes.put(ATTR_PREFIX + ResetCredentialsActionToken.TOKEN_TYPE, String.valueOf(lifespan));
        return this;
    }

    public UserActionTokenBuilder verifyEmailLifespan(int lifespan) {
        realmAttributes.put(ATTR_PREFIX + VerifyEmailActionToken.TOKEN_TYPE, String.valueOf(lifespan));
        return this;
    }

    public Map<String, String> build() {
        return realmAttributes;
    }
}
