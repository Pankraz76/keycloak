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

import org.freedesktop.dbus.exceptions.DBusException;

import java.lang.reflect.Type;

public class TypeSignature {
    // CHECKSTYLE:OFF
    String sig;
    // CHECKSTYLE:ON
    public TypeSignature(String _sig) {
        this.sig = _sig;
    }

    public TypeSignature(Type[] _types) throws DBusException {
        StringBuffer sb = new StringBuffer();
        for (Type t : _types) {
            String[] ts = Marshalling.getDBusType(t);
            for (String s : ts) {
                sb.append(s);
            }
        }
        this.sig = sb.toString();
    }

    public String getSig() {
        return sig;
    }
}
