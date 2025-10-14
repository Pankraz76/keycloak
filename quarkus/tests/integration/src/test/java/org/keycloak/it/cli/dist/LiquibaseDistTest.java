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
package org.keycloak.it.cli.dist;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.keycloak.it.junit5.extension.DistributionTest;
import org.keycloak.it.junit5.extension.RawDistOnly;
import org.keycloak.it.utils.KeycloakDistribution;

@DistributionTest
@RawDistOnly(reason = "Containers are immutable")
@Tag(DistributionTest.SLOW)
public class LiquibaseDistTest {

    @Test
    public void dbLockMultipleExecution(KeycloakDistribution distribution) {
        var result = distribution.run("start-dev", "--log-level=org.keycloak.connections.jpa.updater.liquibase.lock.CustomLockService:trace");
        result.assertMessage("Initialize Database Lock Table, current locks []");
        result.assertMessage("Initialized record in the database lock table");

        // the code block in the CustomLockService should not be executed for the second time
        result = distribution.run("start-dev", "--log-level=org.keycloak.connections.jpa.updater.liquibase.lock.CustomLockService:trace");
        result.assertNoMessage("Initialize Database Lock Table, current locks");
        result.assertNoMessage("Initialized record in the database lock table");
    }
}
