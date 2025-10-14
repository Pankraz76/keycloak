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
package org.keycloak.testsuite.user.profile;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.representations.userprofile.config.UPConfig;
import org.keycloak.userprofile.DeclarativeUserProfileProvider;
import org.keycloak.userprofile.UserProfile;
import org.keycloak.userprofile.UserProfileContext;

import java.util.Map;

public class CustomUserProfileProvider extends DeclarativeUserProfileProvider {

    public CustomUserProfileProvider(KeycloakSession session, CustomUserProfileProviderFactory factory) {
        super(session, factory);
        UPConfig upConfig = getConfiguration();

        upConfig.getAttribute(UserModel.FIRST_NAME).setRequired(null);
        upConfig.getAttribute(UserModel.LAST_NAME).setRequired(null);
        upConfig.getAttribute(UserModel.EMAIL).setRequired(null);

        setConfiguration(upConfig);
    }

    @Override
    public UserProfile create(UserProfileContext context, UserModel user) {
        return this.create(context, user.getAttributes(), user);
    }

    @Override
    public UserProfile create(UserProfileContext context, Map<String, ?> attributes, UserModel user) {
        return super.create(context, attributes, user);
    }

    @Override
    public UserProfile create(UserProfileContext context, Map<String, ?> attributes) {
        return this.create(context, attributes, (UserModel) null);
    }

}
