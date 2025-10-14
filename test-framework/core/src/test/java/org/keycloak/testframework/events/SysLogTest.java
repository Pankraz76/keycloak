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
package org.keycloak.testframework.events;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SysLogTest {

    String logEntry = "<14>1 2024-08-21T08:14:33.591+02:00 fedora keycloak 17377 org.keycloak.category - \uFEFFSome log message";

    @Test
    public void testParseLog() {
        SysLog sysLog = SysLog.parse(logEntry);

        Assertions.assertNotNull(sysLog.getTimestamp());
        Assertions.assertEquals("fedora", sysLog.getHostname());
        Assertions.assertEquals("keycloak", sysLog.getAppName());
        Assertions.assertEquals("org.keycloak.category", sysLog.getCategory());
        Assertions.assertEquals("Some log message", sysLog.getMessage());
    }

}
