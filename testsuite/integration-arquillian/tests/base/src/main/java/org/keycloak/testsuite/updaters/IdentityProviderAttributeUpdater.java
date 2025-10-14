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
package org.keycloak.testsuite.updaters;

import org.keycloak.admin.client.resource.IdentityProviderResource;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hmlnarik
 */
public class IdentityProviderAttributeUpdater {

    private final Map<String, String> originalAttributes = new HashMap<>();

    private final IdentityProviderResource identityProviderResource;

    private final IdentityProviderRepresentation rep;

    public IdentityProviderAttributeUpdater(IdentityProviderResource identityProviderResource) {
        this.identityProviderResource = identityProviderResource;
        this.rep = identityProviderResource.toRepresentation();
        if (this.rep.getConfig() == null) {
            this.rep.setConfig(new HashMap<>());
        }
    }

    public IdentityProviderAttributeUpdater setAttribute(String name, String value) {
        if (! originalAttributes.containsKey(name)) {
            this.originalAttributes.put(name, this.rep.getConfig().put(name, value));
        } else {
            this.rep.getConfig().put(name, value);
        }
        return this;
    }

    public IdentityProviderAttributeUpdater removeAttribute(String name) {
        if (! originalAttributes.containsKey(name)) {
            this.originalAttributes.put(name, this.rep.getConfig().put(name, null));
        } else {
            this.rep.getConfig().put(name, null);
        }
        return this;
    }

    public IdentityProviderAttributeUpdater setStoreToken(boolean storeToken) {
        rep.setStoreToken(storeToken);
        return this;
    }

    public Closeable update() {
        identityProviderResource.update(rep);

        return () -> {
            rep.getConfig().putAll(originalAttributes);
            identityProviderResource.update(rep);
        };
    }
}
