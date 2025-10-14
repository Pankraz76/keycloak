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
package org.keycloak.services.filters;

import jakarta.annotation.Priority;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Provider
@PreMatching
@Priority(10)
public class InvalidQueryParameterFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(InvalidQueryParameterFilter.class);

    private static final String NUL_CHARACTER = "\u0000";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final Map<String, List<String>> queryParams = requestContext.getUriInfo().getQueryParameters();

        for (final List<String> queryParamValues : queryParams.values()) {
            for (final String queryParamValue : queryParamValues) {
                if (containsAnyNULCharacter(queryParamValue)) {
                    LOGGER.debugf("Request with invalid query parameter value is blocked");
                    throw new BadRequestException("Blocking invalid query parameter value");
                }
            }
        }
    }

    /**
     * Unsafe character values we can safely assume is a bad request:
     * NUL	U+0000	Breaks encoding (esp. UTF-8)
     *
     * @param input the value to check if contains unsafe characters
     * @return true if the input contains at least one of the unsafe characters
     */
    private boolean containsAnyNULCharacter(String input) {
        if (input == null) {
            return false;
        }
        return input.contains(NUL_CHARACTER);
    }

}
