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
package org.keycloak.common.crypto;

public enum FipsMode {
    NON_STRICT("org.keycloak.crypto.fips.FIPS1402Provider"),
    STRICT("org.keycloak.crypto.fips.Fips1402StrictCryptoProvider"),
    DISABLED("org.keycloak.crypto.def.DefaultCryptoProvider");

    private final String providerClassName;
    private final String optionName;

    FipsMode(String providerClassName) {
        this.providerClassName = providerClassName;
        this.optionName = name().toLowerCase().replace('_', '-');
    }

    public boolean isFipsEnabled() {
        return this.equals(NON_STRICT) || this.equals(STRICT);
    }

    public String getProviderClassName() {
        return providerClassName;
    }

    public static FipsMode valueOfOption(String name) {
        return valueOf(name.toUpperCase().replace('-', '_'));
    }

    @Override
    public String toString() {
        return optionName;
    }
}
