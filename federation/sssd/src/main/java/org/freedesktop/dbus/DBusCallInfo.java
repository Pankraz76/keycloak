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
package org.freedesktop.dbus;

import org.freedesktop.dbus.messages.Message;

/**
 * Holds information on a method call
 */
public class DBusCallInfo {
    /**
    * Indicates the caller won't wait for a reply (and we won't send one).
    */
    public static final int NO_REPLY = Message.Flags.NO_REPLY_EXPECTED;
    public static final int ASYNC    = 0x100;
    private final String    source;
    private final String    destination;
    private final String    objectpath;
    private final String    iface;
    private final String    method;
    private final int       flags;

    public DBusCallInfo(Message _m) {
        source = _m.getSource();
        destination = _m.getDestination();
        objectpath = _m.getPath();
        iface = _m.getInterface();
        method = _m.getName();
        flags = _m.getFlags();
    }

    /** Returns the BusID which called the method.
     * @return source
     */
    public String getSource() {
        return source;
    }

    /** Returns the name with which we were addressed on the Bus.
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /** Returns the object path used to call this method.
     * @return objectpath
     */
    public String getObjectPath() {
        return objectpath;
    }

    /** Returns the interface this method was called with.
     * @return interface
     */
    public String getInterface() {
        return iface;
    }

    /** Returns the method name used to call this method.
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /** Returns any flags set on this method call.
     * @return flags
     */
    public int getFlags() {
        return flags;
    }
}
