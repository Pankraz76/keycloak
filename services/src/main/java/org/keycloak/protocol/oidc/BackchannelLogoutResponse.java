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
package org.keycloak.protocol.oidc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BackchannelLogoutResponse {

    private boolean localLogoutSucceeded;
    private List<DownStreamBackchannelLogoutResponse> clientResponses = new ArrayList<>();

    public List<DownStreamBackchannelLogoutResponse> getClientResponses() {
        return clientResponses;
    }

    public void addClientResponses(DownStreamBackchannelLogoutResponse clientResponse) {
        this.clientResponses.add(clientResponse);
    }

    public boolean getLocalLogoutSucceeded() {
        return localLogoutSucceeded;
    }

    public void setLocalLogoutSucceeded(boolean localLogoutSucceeded) {
        this.localLogoutSucceeded = localLogoutSucceeded;
    }

    public static class DownStreamBackchannelLogoutResponse {
        protected boolean withBackchannelLogoutUrl;
        protected Integer responseCode;

        public boolean isWithBackchannelLogoutUrl() {
            return withBackchannelLogoutUrl;
        }

        public void setWithBackchannelLogoutUrl(boolean withBackchannelLogoutUrl) {
            this.withBackchannelLogoutUrl = withBackchannelLogoutUrl;
        }

        public Optional<Integer> getResponseCode() {
            return Optional.ofNullable(responseCode);
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }
    }
}

