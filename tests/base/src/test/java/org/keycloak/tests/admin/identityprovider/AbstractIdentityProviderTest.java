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
package org.keycloak.tests.admin.identityprovider;

import org.junit.jupiter.api.Assertions;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.utils.StripSecretsUtils;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import org.keycloak.testframework.annotations.InjectAdminEvents;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.events.AdminEventAssertion;
import org.keycloak.testframework.events.AdminEvents;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.remote.runonserver.InjectRunOnServer;
import org.keycloak.testframework.remote.runonserver.RunOnServerClient;
import org.keycloak.tests.utils.admin.AdminEventPaths;
import org.keycloak.tests.utils.admin.ApiUtil;

import java.util.Map;

public class AbstractIdentityProviderTest {

    @InjectRealm
    ManagedRealm managedRealm;

    @InjectAdminEvents
    AdminEvents adminEvents;

    @InjectRunOnServer
    RunOnServerClient runOnServer;

    protected String create(IdentityProviderRepresentation idpRep) {
        String idpId = ApiUtil.getCreatedId(managedRealm.admin().identityProviders().create(idpRep));
        Assertions.assertNotNull(idpId);

        String secret = idpRep.getConfig() != null ? idpRep.getConfig().get("clientSecret") : null;
        idpRep = StripSecretsUtils.stripSecrets(null, idpRep);
        // if legacy hide on login page attribute was used, the attr will be removed when converted to model
        idpRep.setHideOnLogin(Boolean.parseBoolean(idpRep.getConfig().remove(IdentityProviderModel.LEGACY_HIDE_ON_LOGIN_ATTR)));

        AdminEventAssertion.assertEvent(adminEvents.poll(), OperationType.CREATE, AdminEventPaths.identityProviderPath(idpRep.getAlias()), idpRep, ResourceType.IDENTITY_PROVIDER);

        if (secret != null) {
            idpRep.getConfig().put("clientSecret", secret);
        }

        return idpId;
    }

    protected IdentityProviderRepresentation createRep(String alias, String providerId) {
        return createRep(alias, providerId,true, null);
    }

    protected IdentityProviderRepresentation createRep(String alias, String providerId,boolean enabled, Map<String, String> config) {
        return createRep(alias, alias, providerId, enabled, config);
    }

    protected IdentityProviderRepresentation createRep(String alias, String displayName, String providerId, boolean enabled, Map<String, String> config) {
        IdentityProviderRepresentation idp = new IdentityProviderRepresentation();

        idp.setAlias(alias);
        idp.setDisplayName(displayName);
        idp.setProviderId(providerId);
        idp.setEnabled(enabled);
        if (config != null) {
            idp.setConfig(config);
        }
        return idp;
    }

    protected void assertProviderInfo(Map<String, String> info, String id, String name) {
        System.out.println(info);
        Assertions.assertEquals(id, info.get("id"), "id");
        Assertions.assertEquals(name, info.get("name"), "name");
    }
}
