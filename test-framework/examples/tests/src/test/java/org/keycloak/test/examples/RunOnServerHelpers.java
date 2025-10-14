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
package org.keycloak.test.examples;

import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.testframework.remote.providers.runonserver.FetchOnServer;
import org.keycloak.testframework.remote.providers.runonserver.FetchOnServerWrapper;

/**
 * Created by st on 26.01.17.
 */
public class RunOnServerHelpers {

    public static InternalRealm internalRealm() {
        return new InternalRealm();
    }

    public static class InternalRealm implements FetchOnServerWrapper<RealmRepresentation> {

        private InternalRealm() {
        }

        @Override
        public FetchOnServer getRunOnServer() {
            return session -> ModelToRepresentation.toRepresentation(session, session.getContext().getRealm(), true);
        }

        @Override
        public Class<RealmRepresentation> getResultClass() {
            return RealmRepresentation.class;
        }

    }

}
