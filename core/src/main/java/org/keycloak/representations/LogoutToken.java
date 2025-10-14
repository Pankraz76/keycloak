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
package org.keycloak.representations;

import org.keycloak.TokenCategory;
import org.keycloak.util.TokenUtil;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class LogoutToken extends JsonWebToken {

    @JsonProperty("sid")
    protected String sid;

    @JsonProperty("events")
    protected Map<String, Object> events = new HashMap<>();

    public Map<String, Object> getEvents() {
        return events;
    }

    public void putEvents(String name, Object value) {
        events.put(name, value);
    }

    public String getSid() {
        return sid;
    }

    public LogoutToken setSid(String sid) {
        this.sid = sid;
        return this;
    }

    public LogoutToken() {
        type(TokenUtil.TOKEN_TYPE_LOGOUT);
    }

    @Override
    public TokenCategory getCategory() {
        return TokenCategory.LOGOUT;
    }
}
