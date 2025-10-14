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
package org.keycloak.services.resources.account;

import org.keycloak.services.cors.Cors;

import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Created by st on 21/03/17.
 */
public class CorsPreflightService {

    /**
     * CORS preflight
     *
     * @return
     */
    @Path("{any:.*}")
    @OPTIONS
    public Response preflight() {
        return Cors.builder().auth().allowedMethods("GET", "POST", "DELETE", "PUT", "HEAD", "OPTIONS").preflight()
                .add(Response.ok());
    }

}
