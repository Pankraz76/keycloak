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
 * An exception while running a remote method within DBus.
 */
@SuppressWarnings("checkstyle:mutableexception")
public class DBusExecutionException extends RuntimeException {
    private static final long serialVersionUID = 6327661667731344250L;

    private String type;

    /**
    * Create an exception with the specified message
    * @param _message message
    */
    public DBusExecutionException(String _message) {
        super(_message);
    }

    public void setType(String _type) {
        this.type = _type;
    }

    /**
    * Get the DBus type of this exception. Use if this
    * was an exception we don't have a class file for.
    *
    * @return string
    */
    public String getType() {
        if (null == type) {
            return getClass().getName();
        } else {
            return type;
        }
    }
}
