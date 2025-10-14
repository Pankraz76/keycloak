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
package org.keycloak.testframework;

import org.keycloak.testframework.admin.AdminClientFactorySupplier;
import org.keycloak.testframework.admin.AdminClientSupplier;
import org.keycloak.testframework.http.SimpleHttpSupplier;
import org.keycloak.testframework.infinispan.InfinispanExternalServerSupplier;
import org.keycloak.testframework.database.DevFileDatabaseSupplier;
import org.keycloak.testframework.database.DevMemDatabaseSupplier;
import org.keycloak.testframework.database.TestDatabase;
import org.keycloak.testframework.events.AdminEventsSupplier;
import org.keycloak.testframework.events.EventsSupplier;
import org.keycloak.testframework.events.SysLogServerSupplier;
import org.keycloak.testframework.http.HttpClientSupplier;
import org.keycloak.testframework.http.HttpServerSupplier;
import org.keycloak.testframework.https.CertificatesSupplier;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.realm.ClientSupplier;
import org.keycloak.testframework.realm.RealmSupplier;
import org.keycloak.testframework.realm.UserSupplier;
import org.keycloak.testframework.server.DistributionKeycloakServerSupplier;
import org.keycloak.testframework.server.EmbeddedKeycloakServerSupplier;
import org.keycloak.testframework.server.KeycloakServer;
import org.keycloak.testframework.server.KeycloakUrlsSupplier;
import org.keycloak.testframework.server.RemoteKeycloakServerSupplier;

import java.util.List;
import java.util.Map;

public class CoreTestFrameworkExtension implements TestFrameworkExtension {

    @Override
    public List<Supplier<?, ?>> suppliers() {
        return List.of(
                new AdminClientSupplier(),
                new AdminClientFactorySupplier(),
                new ClientSupplier(),
                new RealmSupplier(),
                new UserSupplier(),
                new DistributionKeycloakServerSupplier(),
                new EmbeddedKeycloakServerSupplier(),
                new RemoteKeycloakServerSupplier(),
                new KeycloakUrlsSupplier(),
                new DevMemDatabaseSupplier(),
                new DevFileDatabaseSupplier(),
                new SysLogServerSupplier(),
                new EventsSupplier(),
                new AdminEventsSupplier(),
                new HttpClientSupplier(),
                new HttpServerSupplier(),
                new InfinispanExternalServerSupplier(),
                new SimpleHttpSupplier(),
                new CertificatesSupplier()
        );
    }

    @Override
    public Map<Class<?>, String> valueTypeAliases() {
        return Map.of(
                KeycloakServer.class, "server",
                TestDatabase.class, "database"
        );
    }

}
