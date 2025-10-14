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
package org.keycloak.testsuite.util.userprofile;

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserProfileResource;
import org.keycloak.models.UserModel;
import org.keycloak.representations.userprofile.config.UPAttribute;
import org.keycloak.representations.userprofile.config.UPAttributePermissions;
import org.keycloak.representations.userprofile.config.UPAttributeRequired;
import org.keycloak.representations.userprofile.config.UPConfig;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;
import java.util.Set;

import static org.keycloak.userprofile.config.UPConfigUtils.ROLE_ADMIN;
import static org.keycloak.userprofile.config.UPConfigUtils.ROLE_USER;

public class UserProfileUtil {

    public static final String SCOPE_DEPARTMENT = "department";
    public static final String ATTRIBUTE_DEPARTMENT = "department";

    public static final String PERMISSIONS_ALL = "\"permissions\": {\"view\": [\"admin\", \"user\"], \"edit\": [\"admin\", \"user\"]}";
    public static final String PERMISSIONS_ADMIN_ONLY = "\"permissions\": {\"view\": [\"admin\"], \"edit\": [\"admin\"]}";
    public static final String PERMISSIONS_ADMIN_EDITABLE = "\"permissions\": {\"view\": [\"admin\", \"user\"], \"edit\": [\"admin\"]}";

    public static String VALIDATIONS_LENGTH = "\"validations\": {\"length\": { \"min\": 3, \"max\": 255 }}";

    public static final String CONFIGURATION_FOR_USER_EDIT = "{\"attributes\": ["
            + "{\"name\": \"firstName\"," + PERMISSIONS_ALL + "},"
            + "{\"name\": \"lastName\"," + PERMISSIONS_ALL + "},"
            + "{\"name\": \"department\"," + PERMISSIONS_ALL + "}"
            + "]}";

    public static UPConfig setUserProfileConfiguration(RealmResource testRealm, String configuration) {
        try {
            UPConfig config = configuration == null ? null : JsonSerialization.readValue(configuration, UPConfig.class);

            if (config != null) {
                UPAttribute username = config.getAttribute(UserModel.USERNAME);

                if (username == null) {
                    config.addOrReplaceAttribute(new UPAttribute(UserModel.USERNAME));
                }

                UPAttribute email = config.getAttribute(UserModel.EMAIL);

                if (email == null) {
                    config.addOrReplaceAttribute(new UPAttribute(UserModel.EMAIL, new UPAttributePermissions(Set.of(ROLE_USER, ROLE_ADMIN), Set.of(ROLE_USER, ROLE_ADMIN)), new UPAttributeRequired(Set.of(ROLE_USER), Set.of())));
                }
            }

            testRealm.users().userProfile().update(config);

            return config;
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to read configuration", ioe);
        }
    }

    public static UPConfig enableUnmanagedAttributes(UserProfileResource upResource) {
        UPConfig cfg = upResource.getConfiguration();
        cfg.setUnmanagedAttributePolicy(UPConfig.UnmanagedAttributePolicy.ENABLED);
        upResource.update(cfg);
        return cfg;
    }

}

