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
package org.keycloak.models.credential;

import static org.keycloak.utils.StringUtil.isBlank;

import org.keycloak.credential.CredentialModel;
import org.keycloak.models.credential.dto.PasswordCredentialData;
import org.keycloak.models.credential.dto.PasswordSecretData;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PasswordCredentialModel extends CredentialModel {

    public static final String TYPE = "password";
    public static final String PASSWORD_HISTORY = "password-history";

    private final PasswordCredentialData credentialData;
    private final PasswordSecretData secretData;

    private PasswordCredentialModel(PasswordCredentialData credentialData, PasswordSecretData secretData) {
        this.credentialData = credentialData;
        this.secretData = secretData;
    }

    public static PasswordCredentialModel createFromValues(PasswordCredentialData credentialData, PasswordSecretData secretData) {
        return new PasswordCredentialModel(credentialData, secretData);
    }

    public static PasswordCredentialModel createFromValues(String algorithm, byte[] salt, int hashIterations, String encodedPassword){
        return createFromValues(algorithm, salt, hashIterations, null, encodedPassword);
    }

    public static PasswordCredentialModel createFromValues(String algorithm, byte[] salt, int hashIterations, Map<String, List<String>> additionalParameters, String encodedPassword){
        PasswordCredentialData credentialData = new PasswordCredentialData(hashIterations, algorithm, additionalParameters);
        PasswordSecretData secretData = new PasswordSecretData(encodedPassword, salt);

        PasswordCredentialModel passwordCredentialModel = new PasswordCredentialModel(credentialData, secretData);

        try {
            passwordCredentialModel.setCredentialData(JsonSerialization.writeValueAsString(credentialData));
            passwordCredentialModel.setSecretData(JsonSerialization.writeValueAsString(secretData));
            passwordCredentialModel.setType(TYPE);
            return passwordCredentialModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PasswordCredentialModel createFromCredentialModel(CredentialModel credentialModel) {
        try {
            PasswordCredentialData credentialData = isBlank(credentialModel.getCredentialData()) ? null : JsonSerialization.readValue(credentialModel.getCredentialData(),
                    PasswordCredentialData.class);
            PasswordSecretData secretData = isBlank(credentialModel.getSecretData()) ? null : JsonSerialization.readValue(credentialModel.getSecretData(), PasswordSecretData.class);
            PasswordCredentialModel passwordCredentialModel = new PasswordCredentialModel(credentialData, secretData);
            passwordCredentialModel.setCreatedDate(credentialModel.getCreatedDate());
            passwordCredentialModel.setCredentialData(credentialModel.getCredentialData());
            passwordCredentialModel.setId(credentialModel.getId());
            passwordCredentialModel.setSecretData(credentialModel.getSecretData());
            passwordCredentialModel.setType(credentialModel.getType());
            passwordCredentialModel.setUserLabel(credentialModel.getUserLabel());

            return passwordCredentialModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public PasswordCredentialData getPasswordCredentialData() {
        return credentialData;
    }

    public PasswordSecretData getPasswordSecretData() {
        return secretData;
    }


}
