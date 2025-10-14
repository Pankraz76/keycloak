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
package org.keycloak.migration.migrators;

import org.keycloak.migration.ModelVersion;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.representations.idm.RealmRepresentation;

public class MigrateTo21_0_0 implements Migration {

    public static final ModelVersion VERSION = new ModelVersion("21.0.0");

    @Override
    public void migrate(KeycloakSession session) {
        session.realms().getRealmsStream().forEach(this::updateAdminTheme);
    }

    @Override
    public void migrateImport(KeycloakSession session, RealmModel realm, RealmRepresentation rep, boolean skipUserDependent) {
        updateAdminTheme(realm);
    }

    private void updateAdminTheme(RealmModel realm) {
        String adminTheme = realm.getAdminTheme();
        if ("keycloak".equals(adminTheme) || "rh-sso".equals(adminTheme)) {
            realm.setAdminTheme("keycloak.v2");
        }
    }

    @Override
    public ModelVersion getVersion() {
        return VERSION;
    }
}
