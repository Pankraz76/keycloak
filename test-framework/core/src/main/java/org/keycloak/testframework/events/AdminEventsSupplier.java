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
package org.keycloak.testframework.events;

import org.keycloak.testframework.annotations.InjectAdminEvents;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.realm.RealmConfigBuilder;

public class AdminEventsSupplier extends AbstractEventsSupplier<AdminEvents, InjectAdminEvents> {

    @Override
    public AdminEvents getValue(InstanceContext<AdminEvents, InjectAdminEvents> instanceContext) {
        return super.getValue(instanceContext);
    }

    @Override
    public AdminEvents createValue(ManagedRealm realm) {
        return new AdminEvents(realm);
    }

    @Override
    public RealmConfigBuilder intercept(RealmConfigBuilder realm, InstanceContext<AdminEvents, InjectAdminEvents> instanceContext) {
        return realm.adminEventsEnabled(true).adminEventsDetailsEnabled(true);
    }

}
