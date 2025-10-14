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

import org.keycloak.events.Event;
import org.keycloak.events.EventType;

import java.util.HashMap;
import java.util.Map;

public class EventParser {

    private EventParser() {
    }

    public static Event parse(SysLog sysLog) {
        if (!sysLog.getCategory().equals("org.keycloak.events")) {
            return null;
        }

        String message = sysLog.getMessage().substring(sysLog.getMessage().indexOf(')') + 1).trim();

        if (!message.startsWith("type=")) {
            return null;
        }

        String[] split = message.split(", ");

        Map<String, String> eventMap = new HashMap<>();
        for (String s : split) {
            String[] split1 = s.split("=");
            eventMap.put(split1[0], split1[1].substring(1, split1[1].length() - 1));
        }

        Event event = new Event();
        event.setTime(sysLog.getTimestamp().getTime() / 1000);
        event.setDetails(new HashMap<>());

        for (Map.Entry<String, String> e : eventMap.entrySet()) {
            switch (e.getKey()) {
                case "type":
                    event.setType(EventType.valueOf(e.getValue()));
                    break;
                case "clientId":
                    event.setClientId(e.getValue());
                    break;
                case "realmId":
                    event.setRealmId(e.getValue());
                    break;
                case "sessionId":
                    event.setSessionId(e.getValue());
                    break;
                case "ipAddress":
                    event.setIpAddress(e.getValue());
                default:
                    event.getDetails().put(e.getKey(), e.getValue());
                    break;
            }
        }

        return event;
    }

}
