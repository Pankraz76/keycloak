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

import org.jboss.logging.Logger;
import org.keycloak.representations.idm.AdminEventRepresentation;
import org.keycloak.testframework.realm.ManagedRealm;

import java.util.List;

public class AdminEvents extends AbstractEvents<AdminEventRepresentation> {

    private static final Logger LOGGER = Logger.getLogger(AdminEvents.class);

    public AdminEvents(ManagedRealm realm) {
        super(realm);
    }

    @Override
    protected List<AdminEventRepresentation> getEvents(long from, long to) {
        return realm.admin().getAdminEvents(null, null, null, null, null, null, null, from, to, null, null, "asc");
    }

    @Override
    protected String getEventId(AdminEventRepresentation rep) {
        return rep.getId();
    }

    @Override
    protected String getRealmId(AdminEventRepresentation rep) {
        return rep.getRealmId();
    }

    @Override
    protected void clearServerEvents() {
        realm.admin().clearAdminEvents();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
