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
package org.keycloak.models.sessions.infinispan.changes;

import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoTypeId;
import org.keycloak.marshalling.Marshalling;
import org.keycloak.models.sessions.infinispan.entities.SessionEntity;

/**
 * Performs an entity replacement in Infinispan, using its versions instead of equality.
 *
 * @param <K> The Infinispan key type.
 * @param <T> The Infinispan value type (Keycloak entity)
 */
@ProtoTypeId(Marshalling.REPLACE_FUNCTION)
public class ReplaceFunction<K, T extends SessionEntity> implements BiFunction<K, SessionEntityWrapper<T>, SessionEntityWrapper<T>> {

    private final UUID expectedVersion;
    private final SessionEntityWrapper<T> newValue;

    @ProtoFactory
    public ReplaceFunction(UUID expectedVersion, SessionEntityWrapper<T> newValue) {
        this.expectedVersion = Objects.requireNonNull(expectedVersion);
        this.newValue = Objects.requireNonNull(newValue);
    }

    @Override
    public SessionEntityWrapper<T> apply(K key, SessionEntityWrapper<T> currentValue) {
        assert currentValue != null;
        return expectedVersion.equals(currentValue.getVersion()) ? newValue : currentValue;
    }

    @ProtoField(1)
    UUID getExpectedVersion() {
        return expectedVersion;
    }

    @ProtoField(2)
    SessionEntityWrapper<T> getNewValue() {
        return newValue;
    }
}
