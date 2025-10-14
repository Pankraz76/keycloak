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
package org.keycloak.testframework.remote.providers.timeoffset;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.keycloak.common.util.Time;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import java.util.Map;

public class TimeOffSetRealmResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;
    private final String KEY_OFFSET = "offset";

    public TimeOffSetRealmResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {

    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimeOffset() {
        int offset = Time.getOffset();
        var time = Map.of(KEY_OFFSET, offset);
        return Response.ok(time).build();
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setTimeOffset(Map<String, Integer> time) {
        if (!time.containsKey(KEY_OFFSET)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Time.setOffset(time.get(KEY_OFFSET));
        return Response.ok().header("Content-Type", MediaType.APPLICATION_JSON).build();
    }
}
