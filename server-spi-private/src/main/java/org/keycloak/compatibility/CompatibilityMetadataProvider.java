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
package org.keycloak.compatibility;

import java.util.Map;

/**
 * Provides the metadata used by the "update-compatibility" command.
 * <p>
 * Implementations should return all metadata required to determine if it is possible to update from one Keycloak
 * deployment to another in a compatible manner. Metadata key/value pairs may be added or removed in a subsequent
 * version, so it's necessary for implementations to handle missing metadata gracefully.
 * <p>
 * The {@link CompatibilityResult} determines if a rolling update is possible. Factory methods are present with default
 * implementations of {@link CompatibilityResult}.
 */
public interface CompatibilityMetadataProvider {

    int DEFAULT_PRIORITY = 1;

    /**
     * Provides the metadata to be persisted.
     * <p>
     * If an empty {@link Map} is returned, no information about this implementation will be persisted. A {@code null}
     * return value is not supported, and it will interrupt the process.
     *
     * @return The metadata required by this provider to determine if a rolling update is possible.
     */
    Map<String, String> metadata();

    /**
     * It compares the current metadata with {@code other} from another deployment.
     * <p>
     * The default implementation will allow a rolling update if the metadata from the current server is equal to the
     * {@code other}. Implementations can overwrite this method as required.
     *
     * @param other The other deployment metadata. It only contains the metadata from this implementation.
     * @return The {@link CompatibilityResult} with the outcome.
     * @see CompatibilityResult
     */
    default CompatibilityResult isCompatible(Map<String, String> other) {
        return Util.isCompatible(getId(), other, metadata());
    }

    /**
     * @return The priority. Only relevant is multiple implementation has the same {@link #getId()} and/or to replace
     * the default implementation shipped in Keycloak.
     */
    default int priority() {
        return DEFAULT_PRIORITY;
    }

    /**
     * @return The ID of this implementation. It should be unique as implementation with the same ID and priority is not
     * valid.
     */
    String getId();
}
