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
 * An exception within DBus.
 */
public class DBusException extends Exception {
    private static final long serialVersionUID = -1L;

    /**
    * Create an exception with the specified message
    * @param _message message
    */
    public DBusException(String _message) {
        super(_message);
    }

    public DBusException() {
        super();
    }

    public DBusException(String _message, Throwable _cause, boolean _enableSuppression, boolean _writableStackTrace) {
        super(_message, _cause, _enableSuppression, _writableStackTrace);
    }

    public DBusException(String _message, Throwable _cause) {
        super(_message, _cause);
    }

    public DBusException(Throwable _cause) {
        super(_cause);
    }
}
