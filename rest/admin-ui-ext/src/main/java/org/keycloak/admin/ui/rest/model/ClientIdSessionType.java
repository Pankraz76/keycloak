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
package org.keycloak.admin.ui.rest.model;

import java.util.Objects;

/**
 * A tuple containing the clientId and the session type (online/offline).
 *
 */
public class ClientIdSessionType {


    public enum SessionType {
        ALL, REGULAR, OFFLINE
    }

    private final String clientId;
    private final SessionType type;

    public ClientIdSessionType(String clientId, SessionType type) {
        this.clientId = clientId;
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public SessionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientIdSessionType clientIdSessionType = (ClientIdSessionType) o;
        return Objects.equals(clientId, clientIdSessionType.clientId) && type == clientIdSessionType.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, type);
    }
}
