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
package org.keycloak.vault;

import java.util.Optional;

/**
 * Default {@link VaultCharSecret} implementation based on {@link String}.
 *
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 */
public class DefaultVaultStringSecret implements VaultStringSecret {

    private static final VaultStringSecret EMPTY_VAULT_SECRET = new VaultStringSecret() {
        @Override
        public Optional<String> get() {
            return Optional.empty();
        }

        @Override
        public void close() {
        }
    };

    public static VaultStringSecret forString(Optional<String> secret) {
        if (secret == null || ! secret.isPresent()) {
            return EMPTY_VAULT_SECRET;
        }
        return new DefaultVaultStringSecret(secret.get());
    }

    private String secret;

    private DefaultVaultStringSecret(final String secret) {
        this.secret = secret;
    }

    @Override
    public Optional<String> get() {
        return Optional.of(this.secret);
    }

    @Override
    public void close() {
        this.secret = null;
    }
}
