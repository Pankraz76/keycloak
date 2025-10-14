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
package org.keycloak.broker.provider;

import org.jboss.logging.Logger;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.IdentityProviderMapperSyncMode;
import org.keycloak.models.IdentityProviderSyncMode;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public final class IdentityProviderMapperSyncModeDelegate {

    protected static final Logger logger = Logger.getLogger(IdentityProviderMapperSyncModeDelegate.class);

    public static void delegateUpdateBrokeredUser(KeycloakSession session, RealmModel realm, UserModel user, IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context, IdentityProviderMapper mapper) {
        IdentityProviderSyncMode effectiveSyncMode = combineIdpAndMapperSyncMode(context.getIdpConfig().getSyncMode(), mapperModel.getSyncMode());

        if (!mapper.supportsSyncMode(effectiveSyncMode)) {
            logger.warnf("The mapper %s does not explicitly support sync mode %s. Please ensure that the SPI supports the sync mode correctly and update it to reflect this.", mapper.getDisplayType(), effectiveSyncMode);
        }

        if (effectiveSyncMode == IdentityProviderSyncMode.LEGACY) {
            mapper.updateBrokeredUserLegacy(session, realm, user, mapperModel, context);
        } else if (effectiveSyncMode == IdentityProviderSyncMode.FORCE) {
            mapper.updateBrokeredUser(session, realm, user, mapperModel, context);
        }
    }

    public static IdentityProviderSyncMode combineIdpAndMapperSyncMode(IdentityProviderSyncMode syncMode, IdentityProviderMapperSyncMode mapperSyncMode) {
        return IdentityProviderMapperSyncMode.INHERIT.equals(mapperSyncMode) ? syncMode : IdentityProviderSyncMode.valueOf(mapperSyncMode.toString());
    }
}
