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
import org.keycloak.representations.idm.EventRepresentation;
import org.keycloak.testframework.realm.ManagedRealm;

import java.util.List;

public class Events extends AbstractEvents<EventRepresentation> {

    private static final Logger LOGGER = Logger.getLogger(Events.class);

    public Events(ManagedRealm realm) {
        super(realm);
    }

    @Override
    protected List<EventRepresentation> getEvents(long from, long to) {
        return realm.admin().getEvents(null, null, null, from, to, null, null, null, "asc");
    }

    @Override
    protected String getEventId(EventRepresentation rep) {
        return rep.getId();
    }

    @Override
    protected String getRealmId(EventRepresentation rep) {
        return rep.getRealmId();
    }

    @Override
    protected void clearServerEvents() {
        realm.admin().clearEvents();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

}
