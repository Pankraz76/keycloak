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
import org.jboss.logging.Logger;
import org.keycloak.common.util.Base64;
import org.keycloak.common.util.MultivaluedHashMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PasswordSecretData {

    public static final Logger logger = Logger.getLogger(PasswordSecretData.class);

    private final String value;
    private final byte[] salt;

    private MultivaluedHashMap<String, String> additionalParameters;

    /**
     * Creator with the option to provide customized secret data (multiple salt values, chiefly)
     * @param value hash value
     * @param salt salt value
     * @param additionalParameters additional data required by the algorithm
     * @throws IOException invalid base64 in salt value
     */
    @JsonCreator
    public PasswordSecretData(@JsonProperty("value") String value, @JsonProperty("salt") String salt, @JsonProperty("algorithmData") Map<String, List<String>> additionalParameters) throws IOException {
        this.additionalParameters = additionalParameters != null ? new MultivaluedHashMap<>( additionalParameters) : null;

        if (salt == null || "__SALT__".equals(salt)) {
            this.value = value;
            this.salt = null;
        }
        else {
            this.value = value;
            this.salt = Base64.decode(salt);
        }
    }

    /**
     * Default creator (Secret consists only of a value and a single salt)
     * @param value hash value
     * @param salt salt
     */
    public PasswordSecretData(String value, byte[] salt) {
        this.value = value;
        this.salt = salt;
        this.additionalParameters = null;
    }

    public String getValue() {
        return value;
    }

    public byte[] getSalt() {
        return salt;
    }

    public MultivaluedHashMap<String, String> getAdditionalParameters() {
        if (additionalParameters == null) {
            additionalParameters = new MultivaluedHashMap<>();
        }
        return additionalParameters;
    }
}
