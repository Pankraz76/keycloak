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
package org.keycloak.cookie;

import org.keycloak.models.KeycloakContext;
import org.keycloak.services.resources.RealmsResource;

class CookiePathResolver {

    private final KeycloakContext context;
    private String realmPath;

    private String requestPath;

    CookiePathResolver(KeycloakContext context) {
        this.context = context;
    }

    String resolvePath(CookieType cookieType) {
        switch (cookieType.getPath()) {
            case REALM:
                if (realmPath == null) {
                    realmPath = RealmsResource.realmBaseUrl(context.getUri()).path("/").build(context.getRealm().getName()).getRawPath();
                }
                return realmPath;
            case REQUEST:
                if (requestPath == null) {
                    requestPath = context.getUri().getRequestUri().getRawPath();
                }
                return requestPath;
            default:
                throw new IllegalArgumentException("Unsupported enum value " + cookieType.getPath().name());
        }
    }

}
