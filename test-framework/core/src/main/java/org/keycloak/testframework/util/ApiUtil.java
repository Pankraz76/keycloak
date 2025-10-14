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
package org.keycloak.testframework.util;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;

public class ApiUtil {

    public static String handleCreatedResponse(Response response) {
        try (response) {
            if (response.getStatus() != Response.Status.CONFLICT.getStatusCode()) {
                String uuid = getCreatedId(response);
                response.close();
                return uuid;
            } else {
                return null;
            }
        }
    }

    public static String getCreatedId(Response response) {
        Assertions.assertEquals(201, response.getStatus());
        String path = response.getLocation().getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

}
