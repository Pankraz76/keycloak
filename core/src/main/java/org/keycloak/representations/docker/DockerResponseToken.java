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
package org.keycloak.representations.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.representations.JsonWebToken;

import java.util.ArrayList;
import java.util.List;

/**
 *  * {
 *    "iss": "auth.docker.com",
 *    "sub": "jlhawn",
 *    "aud": "registry.docker.com",
 *    "exp": 1415387315,
 *    "nbf": 1415387015,
 *    "iat": 1415387015,
 *    "jti": "tYJCO1c6cnyy7kAn0c7rKPgbV1H1bFws",
 *    "access": [
 *        {
 *        "type": "repository",
 *        "name": "samalba/my-app",
 *        "actions": [
 *           "push"
 *         ]
 *        }
 *    ]
 * }
 */
public class DockerResponseToken extends JsonWebToken {

    @JsonProperty("access")
    protected List<DockerAccess> accessItems = new ArrayList<>();

    public List<DockerAccess> getAccessItems() {
        return accessItems;
    }

    @Override
    public DockerResponseToken id(final String id) {
        super.id(id);
        return this;
    }

    @Override
    public DockerResponseToken exp(final Long expiration) {
        super.exp(expiration);
        return this;
    }

    @Override
    public DockerResponseToken nbf(final Long notBefore) {
        super.nbf(notBefore);
        return this;
    }

    @Override
    public DockerResponseToken issuedNow() {
        super.issuedNow();
        return this;
    }

    @Override
    public DockerResponseToken iat(final Long issuedAt) {
        super.iat(issuedAt);
        return this;
    }

    @Override
    public DockerResponseToken issuer(final String issuer) {
        super.issuer(issuer);
        return this;
    }

    @Override
    public DockerResponseToken audience(final String... audience) {
        super.audience(audience);
        return this;
    }

    @Override
    public DockerResponseToken subject(final String subject) {
        super.subject(subject);
        return this;
    }

    @Override
    public DockerResponseToken type(final String type) {
        super.type(type);
        return this;
    }

    @Override
    public DockerResponseToken issuedFor(final String issuedFor) {
        super.issuedFor(issuedFor);
        return this;
    }
}
