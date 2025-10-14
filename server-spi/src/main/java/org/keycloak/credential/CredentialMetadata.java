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
package org.keycloak.credential;

import java.util.List;

public class CredentialMetadata {
    LocalizedMessage infoMessage;
    List<LocalizedMessage> infoProperties;
    LocalizedMessage warningMessageTitle;
    LocalizedMessage warningMessageDescription;
    CredentialModel credentialModel;

    public CredentialModel getCredentialModel() {
        return credentialModel;
    }

    public void setCredentialModel(CredentialModel credentialModel) {
        this.credentialModel = credentialModel;
    }

    public LocalizedMessage getInfoMessage() {
        return infoMessage;
    }

    public List<LocalizedMessage> getInfoProperties() {
        return infoProperties;
    }

    public LocalizedMessage getWarningMessageTitle() {
        return warningMessageTitle;
    }

    public LocalizedMessage getWarningMessageDescription() {
        return warningMessageDescription;
    }

    public void setWarningMessageTitle(String key, String... parameters) {
        LocalizedMessage message = new LocalizedMessage(key, parameters);
        this.warningMessageTitle = message;
    }

    public void setWarningMessageDescription(String key, String... parameters) {
        LocalizedMessage message = new LocalizedMessage(key, parameters);
        this.warningMessageDescription = message;
    }

    public void setInfoMessage(String key, String... parameters) {
        LocalizedMessage message = new LocalizedMessage(key, parameters);
        this.infoMessage = message;
    }

    public void setInfoProperties(List<LocalizedMessage> infoProperties) {
        this.infoProperties = infoProperties;
    }

    public static class LocalizedMessage {
        private final String key;
        private final Object[] parameters;

        public LocalizedMessage(String key, Object[] parameters) {
            this.key = key;
            this.parameters = parameters;
        }

        public String getKey() {
            return key;
        }

        public Object[] getParameters() {
            return parameters;
        }
    }

}
