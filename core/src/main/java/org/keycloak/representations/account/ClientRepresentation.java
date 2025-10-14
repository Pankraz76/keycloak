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

/**
 * Created by st on 29/03/17.
 */
public class ClientRepresentation {
    private String clientId;
    private String clientName;
    private String description;
    private boolean userConsentRequired;
    private boolean inUse;
    private boolean offlineAccess;
    private String rootUrl;
    private String baseUrl;
    private String effectiveUrl;
    private ConsentRepresentation consent;
    private String logoUri;
    private String policyUri;
    private String tosUri;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUserConsentRequired() {
        return userConsentRequired;
    }

    public void setUserConsentRequired(boolean userConsentRequired) {
        this.userConsentRequired = userConsentRequired;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public boolean isOfflineAccess() {
        return offlineAccess;
    }

    public void setOfflineAccess(boolean offlineAccess) {
        this.offlineAccess = offlineAccess;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEffectiveUrl() {
       return effectiveUrl;
    }

    public void setEffectiveUrl(String effectiveUrl) {
        this.effectiveUrl = effectiveUrl;
    }

    public ConsentRepresentation getConsent() {
        return consent;
    }

    public void setConsent(ConsentRepresentation consent) {
        this.consent = consent;
    }

    public String getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    public String getPolicyUri() {
        return policyUri;
    }

    public void setPolicyUri(String policyUri) {
        this.policyUri = policyUri;
    }

    public String getTosUri() {
        return tosUri;
    }

    public void setTosUri(String tosUri) {
        this.tosUri = tosUri;
    }
}
