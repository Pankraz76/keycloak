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
package org.keycloak.testframework.realm;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RepresentationUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static <T> T clone(T t) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(t);
            return (T) objectMapper.readValue(bytes, t.getClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
