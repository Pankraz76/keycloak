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

import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusMemberName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * DBus name Util class for internal and external use.
 */
public final class DBusNamingUtil {
    private static final Pattern DOLLAR_PATTERN = Pattern.compile("[$]");

    private DBusNamingUtil() {
    }

    /**
     * Get DBus interface name for specified interface class
     *
     * @param _clazz input DBus interface class
     * @return interface name
     * @see DBusInterfaceName
     */
    public static String getInterfaceName(Class<?> _clazz) {
        Objects.requireNonNull(_clazz, "clazz must not be null");

        if (_clazz.isAnnotationPresent(DBusInterfaceName.class)) {
            return _clazz.getAnnotation(DBusInterfaceName.class).value();
        }
        return DOLLAR_PATTERN.matcher(_clazz.getName()).replaceAll(".");
    }

    /**
     * Get DBus method name for specified method object.
     *
     * @param _method input method
     * @return method name
     * @see DBusMemberName
     */
    public static String getMethodName(Method _method) {
        Objects.requireNonNull(_method, "method must not be null");

        if (_method.isAnnotationPresent(DBusMemberName.class)) {
            return _method.getAnnotation(DBusMemberName.class).value();
        }
        return _method.getName();
    }

    /**
     * Get DBus signal name for specified signal class.
     *
     * @param _clazz input DBus signal class
     * @return signal name
     * @see DBusMemberName
     */
    public static String getSignalName(Class<?> _clazz) {
        Objects.requireNonNull(_clazz, "clazz must not be null");

        if (_clazz.isAnnotationPresent(DBusMemberName.class)) {
            return _clazz.getAnnotation(DBusMemberName.class).value();
        }
        return _clazz.getSimpleName();
    }

    /**
     * Get DBus name for specified annotation class
     *
     * @param _clazz input DBus annotation
     * @return interface name
     * @see DBusInterfaceName
     */
    public static String getAnnotationName(Class<? extends Annotation> _clazz) {
        Objects.requireNonNull(_clazz, "clazz must not be null");

        if (_clazz.isAnnotationPresent(DBusInterfaceName.class)) {
            return _clazz.getAnnotation(DBusInterfaceName.class).value();
        }
        return DOLLAR_PATTERN.matcher(_clazz.getName()).replaceAll(".");
    }
}
