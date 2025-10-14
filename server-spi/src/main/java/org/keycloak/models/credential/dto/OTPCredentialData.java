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

public class OTPCredentialData {
    private final String subType;
    private final int digits;
    private int counter;
    private final int period;
    private final String algorithm;

    private final String secretEncoding;

    @JsonCreator
    public OTPCredentialData(@JsonProperty("subType") String subType,
                             @JsonProperty("digits") int digits,
                             @JsonProperty("counter") int counter,
                             @JsonProperty("period") int period,
                             @JsonProperty("algorithm") String algorithm,
                             @JsonProperty("secretEncoding") String secretEncoding) {
        this.subType = subType;
        this.digits = digits;
        this.counter = counter;
        this.period = period;
        this.algorithm = algorithm;
        this.secretEncoding = secretEncoding;
    }

    public String getSubType() {
        return subType;
    }

    public int getDigits() {
        return digits;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getPeriod() {
        return period;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getSecretEncoding() {
        return secretEncoding;
    }
}
