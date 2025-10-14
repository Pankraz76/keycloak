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
package org.keycloak.models.credential.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.common.util.MultivaluedHashMap;

import java.util.List;
import java.util.Map;

public class PasswordCredentialData {
    private final int hashIterations;
    private final String algorithm;

    private MultivaluedHashMap<String, String> additionalParameters;

    /**
     * Creator for standard algorithms (no algorithm tuning beyond hash iterations)
     * @param hashIterations iterations
     * @param algorithm algorithm id
     */
    public PasswordCredentialData(int hashIterations, String algorithm) {
        this(hashIterations, algorithm, null);
    }

    /**
     * Creator for custom algorithms (algorithm with tuning parameters beyond simple has iterations)
     * @param hashIterations iterations
     * @param algorithm algorithm id
     * @param additionalParameters additional tuning parameters
     */
    @JsonCreator
    public PasswordCredentialData(@JsonProperty("hashIterations") int hashIterations, @JsonProperty("algorithm") String algorithm, @JsonProperty("algorithmData") Map<String, List<String>> additionalParameters) {
        this.hashIterations = hashIterations;
        this.algorithm = algorithm;
        this.additionalParameters = additionalParameters != null ?  new MultivaluedHashMap<>(additionalParameters) : null;
    }



    public int getHashIterations() {
        return hashIterations;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Returns a map of algorithm-specific settings. These settings may include additional
     * parameters such as Bcrypt memory-tuning parameters. It should be used immutably.
     * @return algorithm data
     */
    public MultivaluedHashMap<String, String> getAdditionalParameters() {
        if (additionalParameters == null) {
            additionalParameters = new MultivaluedHashMap<>();
        }
        return additionalParameters;
    }
}
