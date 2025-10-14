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
package org.freedesktop.dbus.config;

/**
 * Constant class containing all properties supported as system properties.
 *
 * @author hypfvieh
 * @since v4.2.2 - 2023-01-20
 */
public final class DBusSysProps {
    public static final String SYSPROP_DBUS_TEST_HOME_DIR     = "DBUS_TEST_HOMEDIR";

    public static final String DBUS_SYSTEM_BUS_ADDRESS        = "DBUS_SYSTEM_BUS_ADDRESS";
    public static final String DEFAULT_SYSTEM_BUS_ADDRESS     = "unix:path=/var/run/dbus/system_bus_socket";
    public static final String DBUS_SESSION_BUS_ADDRESS       = "DBUS_SESSION_BUS_ADDRESS";

    public static final String DBUS_MACHINE_ID_SYS_VAR        = "DBUS_MACHINE_ID_LOCATION";

    public static final String DBUS_SESSION_BUS_ADDRESS_MACOS = "DBUS_LAUNCHD_SESSION_BUS_SOCKET";

    private DBusSysProps() {}
}
