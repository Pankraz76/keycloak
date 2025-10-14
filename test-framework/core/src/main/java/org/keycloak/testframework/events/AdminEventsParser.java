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

import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.AuthDetails;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class AdminEventsParser {

    private AdminEventsParser() {
    }

    public static AdminEvent parse(SysLog sysLog) {
        if (!sysLog.getCategory().equals("org.keycloak.events")) {
            return null;
        }

        String message = sysLog.getMessage().substring(sysLog.getMessage().indexOf(')') + 1).trim();

        if (!message.startsWith("operationType=")) {
            return null;
        }

        String[] split = message.split(", ");

        Map<String, String> eventMap = new HashMap<>();
        for (String s : split) {
            String[] split1 = s.split("=");
            eventMap.put(split1[0], split1[1].substring(1, split1[1].length() - 1));
        }

        AdminEvent adminEvent = new AdminEvent();
        adminEvent.setTime(sysLog.getTimestamp().getTime() / 1000);
        adminEvent.setAuthDetails(new AuthDetails());

        for (Map.Entry<String, String> e : eventMap.entrySet()) {
            switch (e.getKey()) {
                case "operationType":
                    adminEvent.setOperationType(OperationType.valueOf(e.getValue()));
                    break;
                case "realmId":
                    adminEvent.setRealmId(e.getValue());
                    adminEvent.getAuthDetails().setRealmId(e.getValue());
                    break;
                case "realmName":
                    adminEvent.getAuthDetails().setRealmName(e.getValue());
                    break;
                case "clientId":
                    adminEvent.getAuthDetails().setClientId(e.getValue());
                    break;
                case "userId":
                    adminEvent.getAuthDetails().setUserId(e.getValue());
                    break;
                case "ipAddress":
                    adminEvent.getAuthDetails().setIpAddress(e.getValue());
                    break;
                case "resourceType":
                    adminEvent.setResourceType(ResourceType.valueOf(e.getValue()));
                    break;
                case "resourcePath":
                    adminEvent.setResourcePath(e.getValue());
                    break;
                case "error":
                    adminEvent.setError(e.getValue());
                    break;
                default:
                    break;
            }
        }

        return adminEvent;
    }
}
