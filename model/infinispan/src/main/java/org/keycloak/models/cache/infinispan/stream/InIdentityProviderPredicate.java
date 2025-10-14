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
import org.keycloak.models.cache.infinispan.entities.InIdentityProvider;
import org.keycloak.models.cache.infinispan.entities.Revisioned;
import org.keycloak.marshalling.Marshalling;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
@ProtoTypeId(Marshalling.IN_IDENTITY_PROVIDER_PREDICATE)
public class InIdentityProviderPredicate implements Predicate<Map.Entry<String, Revisioned>> {
    private String id;

    public static InIdentityProviderPredicate create() {
        return new InIdentityProviderPredicate();
    }

    public InIdentityProviderPredicate provider(String id) {
        this.id = id;
        return this;
    }

    @ProtoField(1)
    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean test(Map.Entry<String, Revisioned> entry) {
        return entry.getValue() instanceof InIdentityProvider provider && provider.contains(id);
    }

}
