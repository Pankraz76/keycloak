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
package org.keycloak.utils;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

public class MediaTypeMatcher {

    public static boolean isHtmlRequest(HttpHeaders headers) {
        return isAcceptMediaType(headers, MediaType.TEXT_HTML_TYPE);
    }

    public static boolean isJsonRequest(HttpHeaders headers) {
        return isAcceptMediaType(headers, MediaType.APPLICATION_JSON_TYPE);
    }

    private static boolean isAcceptMediaType(HttpHeaders headers, MediaType textHtmlType) {
        for (MediaType m : headers.getAcceptableMediaTypes()) {
            if (!m.isWildcardType() && m.isCompatible(textHtmlType)) {
                return true;
            }
        }
        return false;
    }
}
