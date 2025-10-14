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

import io.smallrye.config.ConfigSourceInterceptorContext;

import org.keycloak.config.TransactionOptions;

import static org.keycloak.quarkus.runtime.configuration.mappers.PropertyMapper.fromOption;

import java.util.List;

public class TransactionPropertyMappers implements PropertyMapperGrouping {

    @Override
    public List<PropertyMapper<?>> getPropertyMappers() {
        return List.of(
                fromOption(TransactionOptions.TRANSACTION_XA_ENABLED)
                        .to("quarkus.datasource.jdbc.transactions")
                        .transformer(TransactionPropertyMappers::getQuarkusTransactionsValue)
                        .build(),
                fromOption(TransactionOptions.TRANSACTION_XA_ENABLED_DATASOURCE)
                        .to("quarkus.datasource.\"<datasource>\".jdbc.transactions")
                        .transformer(TransactionPropertyMappers::getQuarkusTransactionsValue)
                        .build()
        );
    }

    private static String getQuarkusTransactionsValue(String txValue, ConfigSourceInterceptorContext context) {
        boolean isXaEnabled = Boolean.parseBoolean(txValue);

        if (isXaEnabled) {
            return "xa";
        }

        return "enabled";
    }
}
