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
package org.keycloak.config;

public class TransactionOptions {

    public static final Option<Boolean> TRANSACTION_XA_ENABLED_DATASOURCE = new OptionBuilder<>("transaction-xa-enabled-<datasource>", Boolean.class)
            .category(OptionCategory.TRANSACTION)
            .description("If set to true, XA for <datasource> datasource will be used.")
            .buildTime(true)
            .defaultValue(Boolean.TRUE)
            .build();

    public static final Option<Boolean> TRANSACTION_XA_ENABLED = new OptionBuilder<>("transaction-xa-enabled", Boolean.class)
            .category(OptionCategory.TRANSACTION)
            .description("If set to true, XA datasources will be used.")
            .buildTime(true)
            .defaultValue(Boolean.FALSE)
            .wildcardKey(TRANSACTION_XA_ENABLED_DATASOURCE.getKey())
            .build();

    public static String getNamedTxXADatasource(String namedProperty) {
        if ("<default>".equals(namedProperty)) {
            return TRANSACTION_XA_ENABLED.getKey();
        }
        var key = TRANSACTION_XA_ENABLED_DATASOURCE.getKey();
        return key.substring(0, key.indexOf("<")).concat(namedProperty);
    }
}
