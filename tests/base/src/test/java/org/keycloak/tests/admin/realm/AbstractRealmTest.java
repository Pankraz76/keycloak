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
package org.keycloak.tests.admin.realm;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.testframework.admin.AdminClientFactory;
import org.keycloak.testframework.annotations.InjectAdminClient;
import org.keycloak.testframework.annotations.InjectAdminClientFactory;
import org.keycloak.testframework.annotations.InjectAdminEvents;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.events.AdminEvents;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.remote.runonserver.InjectRunOnServer;
import org.keycloak.testframework.remote.runonserver.RunOnServerClient;

public class AbstractRealmTest {

    @InjectRealm(ref = "managedRealm")
    ManagedRealm managedRealm;

    @InjectAdminClient(ref = "managed", realmRef = "managedRealm")
    Keycloak adminClient;

    @InjectAdminClientFactory
    AdminClientFactory adminClientFactory;

    @InjectRunOnServer(ref = "managed", realmRef = "managedRealm")
    RunOnServerClient runOnServer;

    @InjectAdminEvents(ref = "managedEvents", realmRef = "managedRealm")
    AdminEvents adminEvents;
}
