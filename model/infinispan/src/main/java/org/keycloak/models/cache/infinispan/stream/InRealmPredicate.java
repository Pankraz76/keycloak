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
package org.keycloak.models.cache.infinispan.stream;

import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoTypeId;
import org.keycloak.models.cache.infinispan.entities.InRealm;
import org.keycloak.models.cache.infinispan.entities.Revisioned;
import org.keycloak.marshalling.Marshalling;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@ProtoTypeId(Marshalling.IN_REALM_PREDICATE)
public class InRealmPredicate implements Predicate<Map.Entry<String, Revisioned>> {
    private String realm;

    public static InRealmPredicate create() {
        return new InRealmPredicate();
    }

    public InRealmPredicate realm(String id) {
        realm = id;
        return this;
    }

    @ProtoField(1)
    String getRealm() {
        return realm;
    }

    void setRealm(String realm) {
        this.realm = realm;
    }

    @Override
    public boolean test(Map.Entry<String, Revisioned> entry) {
        return entry.getValue() instanceof InRealm inRealm && realm.equals(inRealm.getRealm());

    }

}
