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
package org.keycloak.testsuite.organization.admin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.testsuite.AbstractAdminTest;
import org.keycloak.testsuite.util.IdentityProviderBuilder;

public class IdentityProviderThemeConfigTest extends AbstractAdminTest {

    @Before
    public void onBefore() {
        RealmResource realm = testRealm();
        RealmRepresentation rep = realm.toRepresentation();
        rep.setLoginTheme("themeconfig");
        realm.update(rep);
    }

    @Test
    public void testIdentityProviderThemeConfigs() {
        testRealm().identityProviders().create(
                IdentityProviderBuilder.create()
                        .alias("broker")
                        .providerId("oidc")
                        .setAttribute("unsupported-themeConfig", "This value is not shown in the Keycloak theme")
                        .setAttribute("kcTheme-idpConfigValue", "This value is shown in the Keycloak theme")
                        .build()).close();

        oauth.realm(TEST_REALM_NAME);
        oauth.openLoginForm();
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("This value is shown in the Keycloak theme"));
        Assert.assertFalse(pageSource.contains("This value is not shown in the Keycloak theme"));
    }
}
