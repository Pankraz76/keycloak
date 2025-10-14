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
package org.freedesktop.dbus.utils;

import java.util.regex.Pattern;

/**
 * Utility class containing commonly used regular expression patterns.
 *
 * @author hypfvieh
 * @version 4.1.0 - 2022-02-08
 */
public final class CommonRegexPattern {
    public static final Pattern PROXY_SPLIT_PATTERN = Pattern.compile("[<>]");
    public static final Pattern IFACE_PATTERN       = Pattern.compile("^interface *name *= *['\"]([^'\"]*)['\"].*$");

    public static final Pattern DBUS_IFACE_PATTERN  = Pattern.compile("^.*\\.([^\\.]+)$");

    public static final Pattern EXCEPTION_EXTRACT_PATTERN = Pattern.compile("\\.([^\\.]*)$");
    public static final Pattern EXCEPTION_PARTIAL_PATTERN = Pattern.compile(".*\\..*");

    private CommonRegexPattern() {

    }
}
