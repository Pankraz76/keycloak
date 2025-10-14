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
package org.keycloak.tests.suites;

import org.junit.platform.suite.api.AfterSuite;
import org.junit.platform.suite.api.BeforeSuite;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.keycloak.common.Profile;
import org.keycloak.testframework.injection.SuiteSupport;
import org.keycloak.testframework.server.KeycloakServerConfig;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;
import org.keycloak.tests.admin.client.SessionTest;
import org.keycloak.tests.admin.concurrency.ConcurrentLoginTest;

@Suite
@SelectClasses({SessionTest.class, ConcurrentLoginTest.class})
public class MultisiteTestSuite {

    @BeforeSuite
    public static void beforeSuite() {
        SuiteSupport.startSuite()
                .registerServerConfig(MultisiteServerConfig.class);
    }

    @AfterSuite
    public static void afterSuite() {
        SuiteSupport.stopSuite();
    }

    public static class MultisiteServerConfig implements KeycloakServerConfig {

        @Override
        public KeycloakServerConfigBuilder configure(KeycloakServerConfigBuilder config) {
            return config.features(Profile.Feature.MULTI_SITE)
                    .featuresDisabled(Profile.Feature.PERSISTENT_USER_SESSIONS)
                    .externalInfinispanEnabled(true);
        }
    }
}
