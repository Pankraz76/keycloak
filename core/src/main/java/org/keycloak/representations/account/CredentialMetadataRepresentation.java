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
package org.keycloak.representations.account;

import java.util.List;

import org.keycloak.representations.idm.CredentialRepresentation;

public class CredentialMetadataRepresentation {

    LocalizedMessage infoMessage;
    List<LocalizedMessage> infoProperties;
    LocalizedMessage warningMessageTitle;
    LocalizedMessage warningMessageDescription;

    private CredentialRepresentation credential;


    public CredentialRepresentation getCredential() {
        return credential;
    }

    public void setCredential(CredentialRepresentation credential) {
        this.credential = credential;
    }

    public LocalizedMessage getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(LocalizedMessage infoMessage) {
        this.infoMessage = infoMessage;
    }

    public List<LocalizedMessage> getInfoProperties() {
        return infoProperties;
    }

    public void setInfoProperties(List<LocalizedMessage> infoProperties) {
        this.infoProperties = infoProperties;
    }

    public LocalizedMessage getWarningMessageTitle() {
        return warningMessageTitle;
    }

    public void setWarningMessageTitle(LocalizedMessage warningMessageTitle) {
        this.warningMessageTitle = warningMessageTitle;
    }

    public LocalizedMessage getWarningMessageDescription() {
        return warningMessageDescription;
    }

    public void setWarningMessageDescription(LocalizedMessage warningMessageDescription) {
        this.warningMessageDescription = warningMessageDescription;
    }
}
