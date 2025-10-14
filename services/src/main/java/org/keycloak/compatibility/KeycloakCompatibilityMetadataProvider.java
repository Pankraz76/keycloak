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

import org.keycloak.common.Profile;
import org.keycloak.common.Version;
import org.keycloak.migration.ModelVersion;

/**
 * A {@link CompatibilityMetadataProvider} implementation to provide the Keycloak version.
 */
public class KeycloakCompatibilityMetadataProvider implements CompatibilityMetadataProvider {

    public static final String ID = "keycloak";
    public static final String VERSION_KEY = "version";
    private final String version;

    // Constructor required for ServiceLoader
    @SuppressWarnings("unused")
    public KeycloakCompatibilityMetadataProvider() {
        this(Version.VERSION);
    }

    public KeycloakCompatibilityMetadataProvider(String version) {
        this.version = version;
    }

    @Override
    public Map<String, String> metadata() {
        return Map.of(VERSION_KEY, version);
    }

    @Override
    public CompatibilityResult isCompatible(Map<String, String> other) {
        CompatibilityResult equalComparison = CompatibilityMetadataProvider.super.isCompatible(other);

        // If V2 feature is enabled, we consider versions upgradable in a rolling way if the other is a previous micro release
        if (!Util.isNotCompatible(equalComparison) || !Profile.isFeatureEnabled(Profile.Feature.ROLLING_UPDATES_V2)) {
            return equalComparison;
        }


        // We need to make sure the previous version is not null
        String otherVersion = other.get(VERSION_KEY);
        if (otherVersion == null)
            return equalComparison;

        // Check if only version attribute is incompatible we don't want to allow rolling update if some other metadata didn't match
        boolean versionMismatch = equalComparison.incompatibleAttributes()
                .map(erroredAttributes -> erroredAttributes.size() == 1 && erroredAttributes.iterator().next().equals(VERSION_KEY))
                .orElse(false);

        if (!versionMismatch) {
            return equalComparison;
        }

        ModelVersion otherModelVersion = new ModelVersion(otherVersion);
        ModelVersion currentModelVersion = new ModelVersion(version);

        // Check we are in the same major.minor release stream
        if (!currentModelVersion.hasSameMajorMinor(otherModelVersion)) {
            return equalComparison;
        }

        int otherMicro = otherModelVersion.getMicro();
        int currentMicro = currentModelVersion.getMicro();

        // Make sure we are updating to a newer or the same micro release and do not allow rolling rollback
        return currentMicro < otherMicro ?
                equalComparison :
                CompatibilityResult.providerCompatible(ID);
    }

    @Override
    public String getId() {
        return ID;
    }
}
