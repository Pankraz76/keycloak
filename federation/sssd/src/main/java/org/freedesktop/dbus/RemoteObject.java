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

import org.freedesktop.dbus.interfaces.DBusInterface;

public class RemoteObject {
    private final String                         busname;
    private final String                         objectpath;
    private final Class<? extends DBusInterface> iface;
    private final boolean                        autostart;

    public RemoteObject(String _busname, String _objectpath, Class<? extends DBusInterface> _iface, boolean _autostart) {
        this.busname = _busname;
        this.objectpath = _objectpath;
        this.iface = _iface;
        this.autostart = _autostart;
    }

    @Override
    public boolean equals(Object _o) {
        if (!(_o instanceof RemoteObject)) {
            return false;
        }
        RemoteObject them = (RemoteObject) _o;

        if (!them.objectpath.equals(this.objectpath)) {
            return false;
        } else if (null == this.busname && null != them.busname) {
            return false;
        } else if (null != this.busname && null == them.busname) {
            return false;
        } else if (null != them.busname && !them.busname.equals(this.busname)) {
            return false;
        } else if (null == this.iface && null != them.iface) {
            return false;
        } else if (null != this.iface && null == them.iface) {
            return false;
        } else if (null != them.iface && !them.iface.equals(this.iface)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (null == busname ? 0 : busname.hashCode()) + objectpath.hashCode() + (null == iface ? 0 : iface.hashCode());
    }

    public boolean isAutostart() {
        return autostart;
    }

    public String getBusName() {
        return busname;
    }

    public String getObjectPath() {
        return objectpath;
    }

    public Class<? extends DBusInterface> getInterface() {
        return iface;
    }

    @Override
    public String toString() {
        return busname + ":" + objectpath + ":" + iface;
    }
}
