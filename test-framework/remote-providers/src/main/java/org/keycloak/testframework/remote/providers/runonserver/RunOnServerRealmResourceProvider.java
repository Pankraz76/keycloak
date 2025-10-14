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
package org.keycloak.testframework.remote.providers.runonserver;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.util.JsonSerialization;

public class RunOnServerRealmResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;
    private final ClassLoader classLoader;

    public RunOnServerRealmResourceProvider(KeycloakSession session, ClassLoader classLoader) {
        this.session = session;
        this.classLoader = classLoader;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {
    }

    @POST
    @Path("/")
    @Consumes(MediaType.TEXT_PLAIN + ";charset=utf-8")
    @Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
    public String runOnServer(String runOnServer) {
        try {
            Object o = SerializationUtil.decode(runOnServer, classLoader);
            if (o instanceof FetchOnServer f) {
                Object result = f.run(session);
                return result != null ? JsonSerialization.writeValueAsString(result) : null;
            } else if (o instanceof RunOnServer r) {
                r.run(session);
                return null;
            } else {
                throw new IllegalArgumentException("Can't handle serialized class: " + o.getClass().getName());
            }
        } catch (Throwable t) {
            return SerializationUtil.encodeException(t);
        }
    }

}
