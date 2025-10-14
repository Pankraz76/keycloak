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
package org.keycloak.services.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class DateUtil {

    /**
     * Parses a string timestamp or date to an Epoch timestamp in milliseconds (number of milliseconds since January 1, 1970, 00:00:00 GMT);
     * if the date is a ISO-8601 extended local date format, the time at the beginning of the day is returned.
     *
     * @param date the date in ISO-8601 extended local date format
     * @return Epoch time for the start of the day
     */
    public static long toStartOfDay(String date) {
        if (date.indexOf('-') != -1) {
            return LocalDate.parse(date).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
        } else {
            return Long.parseLong(date);
        }
    }

    /**
     * Parses a string timestamp or date to an Epoch timestamp in milliseconds (number of milliseconds since January 1, 1970, 00:00:00 GMT);
     * if the date is a ISO-8601 extended local date format, the time at the end of the day is returned.
     *
     * @param date the date in ISO-8601 extended local date format
     * @return Epoch time for the end of the day
     */
    public static long toEndOfDay(String date) {
        if (date.indexOf('-') != -1) {
            return LocalDate.parse(date).atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC).toEpochMilli();
        } else {
            return Long.parseLong(date);
        }
    }

}
