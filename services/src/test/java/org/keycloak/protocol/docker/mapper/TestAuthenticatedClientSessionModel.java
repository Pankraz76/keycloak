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
package org.keycloak.protocol.docker.mapper;

import org.keycloak.models.AuthenticatedClientSessionModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserSessionModel;

import java.util.HashMap;
import java.util.Map;

class TestAuthenticatedClientSessionModel implements AuthenticatedClientSessionModel {

    private final Map<String, String> notes = new HashMap<>();

    @Override
    public String getId() {
        return null;
    }

    @Override
    public int getTimestamp() {
        return 0;
    }

    @Override
    public void setTimestamp(int timestamp) {

    }

    @Override
    public void detachFromUserSession() {

    }

    @Override
    public UserSessionModel getUserSession() {
        return null;
    }

    @Override
    public String getNote(String name) {
        return notes.get(name);
    }

    @Override
    public void setNote(String name, String value) {
        notes.put(name, value);
    }

    @Override
    public void removeNote(String name) {
        notes.remove(name);
    }

    @Override
    public Map<String, String> getNotes() {
        return notes;
    }

    @Override
    public String getRedirectUri() {
        return null;
    }

    @Override
    public void setRedirectUri(String uri) {

    }

    @Override
    public RealmModel getRealm() {
        return null;
    }

    @Override
    public ClientModel getClient() {
        return null;
    }

    @Override
    public String getAction() {
        return null;
    }

    @Override
    public void setAction(String action) {

    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public void setProtocol(String method) {

    }
}
