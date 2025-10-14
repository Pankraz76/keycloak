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
package org.keycloak.admin.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import jakarta.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

public class JacksonProvider extends ResteasyJackson2Provider {

    @Override
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
        ObjectMapper objectMapper = super.locateMapper(type, mediaType);

        // Same like JSONSerialization class. Makes it possible to use admin-client against older versions of Keycloak server where the properties on representations might be different
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // The client must work with the newer versions of Keycloak server, which might contain the JSON fields not yet known by the client. So unknown fields will be ignored.
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }
}
