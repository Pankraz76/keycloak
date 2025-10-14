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
package org.keycloak.theme;

import org.keycloak.Config;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;

public class DefaultThemeSelectorProvider implements ThemeSelectorProvider {

    public static final String LOGIN_THEME_KEY = "login_theme";

    private final KeycloakSession session;

    public DefaultThemeSelectorProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public String getThemeName(Theme.Type type) {
        String name = null;

        switch (type) {
            case WELCOME:
                name = Config.scope("theme").get("welcomeTheme");
                break;
            case LOGIN:
                ClientModel client = session.getContext().getClient();
                if (client != null) {
                    name = client.getAttribute(LOGIN_THEME_KEY);
                }

                if (name == null || name.isEmpty()) {
                    name = session.getContext().getRealm().getLoginTheme();
                }
                
                break;
            case ACCOUNT:
                name = session.getContext().getRealm().getAccountTheme();
                break;
            case EMAIL:
                name = session.getContext().getRealm().getEmailTheme();
                break;
            case ADMIN:
                name = session.getContext().getRealm().getAdminTheme();
                break;
        }

        if (name == null || name.isEmpty()) {
            name = getDefaultThemeName(type);
        }

        return name;
    }

    @Override
    public void close() {
    }

}

