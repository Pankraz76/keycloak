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
package org.keycloak.testframework.injection;

import org.keycloak.testframework.config.Config;
import org.keycloak.testframework.config.SuiteConfigSource;
import org.keycloak.testframework.server.KeycloakServerConfig;

public class SuiteSupport {

    private static SuiteConfig suiteConfig = new SuiteConfig();

    public static SuiteConfig startSuite() {
        return suiteConfig;
    }

    public static void stopSuite() {
        SuiteConfigSource.clear();
        Config.initConfig();
        Extensions.reset();
        suiteConfig = null;
    }

    public static class SuiteConfig {

        public SuiteConfig registerServerConfig(Class<? extends KeycloakServerConfig> serverConfig) {
            SuiteConfigSource.set("kc.test.server.config", serverConfig.getName());
            return this;
        }

        public SuiteConfig supplier(String name, String supplier) {
            SuiteConfigSource.set("kc.test." + name, supplier);
            return this;
        }

        public SuiteConfig includedSuppliers(String name, String... suppliers) {
            SuiteConfigSource.set("kc.test." + name + ".suppliers.included", String.join(",", suppliers));
            return this;
        }

        public SuiteConfig excludedSuppliers(String name, String... suppliers) {
            SuiteConfigSource.set("kc.test." + name + ".suppliers.excluded", String.join(",", suppliers));
            return this;
        }

    }

}
