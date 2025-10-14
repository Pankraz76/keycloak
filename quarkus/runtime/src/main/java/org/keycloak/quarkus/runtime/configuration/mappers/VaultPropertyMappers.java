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
package org.keycloak.quarkus.runtime.configuration.mappers;

import org.keycloak.config.VaultOptions;

import static org.keycloak.quarkus.runtime.configuration.mappers.PropertyMapper.fromOption;

import java.util.List;

final class VaultPropertyMappers implements PropertyMapperGrouping {

    @Override
    public List<PropertyMapper<?>> getPropertyMappers() {
        return List.of(
                fromOption(VaultOptions.VAULT)
                        .paramLabel("provider")
                        .build(),
                fromOption(VaultOptions.VAULT_DIR)
                        .to("kc.spi-vault--file--dir")
                        .paramLabel("dir")
                        .build(),
                fromOption(VaultOptions.VAULT_FILE)
                        .to("kc.spi-vault--keystore--file")
                        .paramLabel("file")
                        .build(),
                fromOption(VaultOptions.VAULT_PASS)
                        .to("kc.spi-vault--keystore--pass")
                        .paramLabel("pass")
                        .build(),
                fromOption(VaultOptions.VAULT_TYPE)
                        .to("kc.spi-vault--keystore--type")
                        .paramLabel("type")
                        .build()
        );
    }

}
