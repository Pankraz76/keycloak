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

public class RecoveryAuthnCodesCredentialData {

    private final Integer hashIterations;
    private final String algorithm;

    private int totalCodes;
    private int remainingCodes;

    @JsonCreator
    public RecoveryAuthnCodesCredentialData(@JsonProperty("hashIterations") Integer hashIterations,
            @JsonProperty("algorithm") String algorithm, @JsonProperty("remaining") int remainingCodes,
                                            @JsonProperty("total") int totalCodes) {
        this.hashIterations = hashIterations;
        this.algorithm = algorithm;
        this.remainingCodes = remainingCodes;
        this.totalCodes = totalCodes;
    }

    public Integer getHashIterations() {
        return hashIterations;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getRemainingCodes() {
        return remainingCodes;
    }

    public void setRemainingCodes(int remainingCodes) {
        this.remainingCodes = remainingCodes;
    }

    public int getTotalCodes() {
        return totalCodes;
    }

    public void setTotalCodes(int totalCodes) {
        this.totalCodes = totalCodes;
    }


}
