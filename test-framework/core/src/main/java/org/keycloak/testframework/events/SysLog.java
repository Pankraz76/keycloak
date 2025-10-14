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

import java.time.Instant;
import java.util.Date;

public class SysLog {

    private static final String SEPARATOR = " - \uFEFF";

    private Date timestamp;
    private String hostname;
    private String appName;
    private String category;
    private String message;

    private SysLog() {
    }

    public static SysLog parse(String logEntry) {
        int i = logEntry.indexOf(SEPARATOR);

        String[] header = logEntry.substring(0, i).split(" ");

        SysLog sysLog = new SysLog();
        sysLog.timestamp = Date.from(Instant.parse(header[1]));
        sysLog.hostname = header[2];
        sysLog.appName = header[3];
        sysLog.category = header[5];
        sysLog.message = logEntry.substring(i + SEPARATOR.length());
        return sysLog;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getHostname() {
        return hostname;
    }

    public String getAppName() {
        return appName;
    }

    public String getCategory() {
        return category;
    }

    public String getMessage() {
        return message;
    }
}
