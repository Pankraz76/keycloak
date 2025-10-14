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
package org.freedesktop.dbus.exceptions;

/**
 * Thrown when something goes wrong with the connection to DBus.<p>
 * This includes find the connection parameter (e.g. machine-id file) or establishing the connection.
 *
 * @author David M.
 * @since v3.3.0 - 2021-01-27
 */
public class DBusConnectionException extends DBusException {
    private static final long serialVersionUID = -1L;

    public DBusConnectionException() {
        super();
    }

    public DBusConnectionException(String _message, Throwable _cause, boolean _enableSuppression,
            boolean _writableStackTrace) {
        super(_message, _cause, _enableSuppression, _writableStackTrace);
    }

    public DBusConnectionException(String _message, Throwable _cause) {
        super(_message, _cause);
    }

    public DBusConnectionException(String _message) {
        super(_message);
    }

    public DBusConnectionException(Throwable _cause) {
        super(_cause);
    }

}
