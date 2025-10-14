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
package org.keycloak.encoding;

import org.keycloak.models.KeycloakSession;

public class ResourceEncodingHelper {

    public static ResourceEncodingProvider getResourceEncodingProvider(KeycloakSession session, String contentType) {
        String acceptEncoding = session.getContext().getRequestHeaders().getHeaderString("Accept-Encoding");
        if (acceptEncoding != null) {
            for (String e : acceptEncoding.split(",")) {
                e = e.trim();
                ResourceEncodingProviderFactory f = (ResourceEncodingProviderFactory) session.getKeycloakSessionFactory().getProviderFactory(ResourceEncodingProvider.class, e);
                if (f != null && f.encodeContentType(contentType)) {
                    ResourceEncodingProvider provider = session.getProvider(ResourceEncodingProvider.class, e.trim());
                    if (provider != null) {
                        return provider;
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

}
