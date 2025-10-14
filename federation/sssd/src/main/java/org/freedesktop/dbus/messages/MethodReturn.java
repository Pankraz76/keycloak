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
package org.freedesktop.dbus.messages;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import java.util.ArrayList;
import java.util.List;

public class MethodReturn extends MethodBase {

    private MethodCall call;

    MethodReturn() {
    }

    public MethodReturn(String _dest, long _replyserial, String _sig, Object... _args) throws DBusException {
        this(null, _dest, _replyserial, _sig, _args);
    }

    public MethodReturn(String _source, String _dest, long _replyserial, String _sig, Object... _args) throws DBusException {
        super(DBusConnection.getEndianness(), Message.MessageType.METHOD_RETURN, (byte) 0);

        List<Object> hargs = new ArrayList<>();
        hargs.add(createHeaderArgs(HeaderField.REPLY_SERIAL, ArgumentType.UINT32_STRING, _replyserial));

        if (null != _source) {
            hargs.add(createHeaderArgs(HeaderField.SENDER, ArgumentType.STRING_STRING, _source));
        }

        if (null != _dest) {
            hargs.add(createHeaderArgs(HeaderField.DESTINATION, ArgumentType.STRING_STRING, _dest));
        }

        if (null != _sig) {
            hargs.add(createHeaderArgs(HeaderField.SIGNATURE, ArgumentType.SIGNATURE_STRING, _sig));
            setArgs(_args);
        }

        appendFileDescriptors(hargs, _sig, _args);
        padAndMarshall(hargs, getSerial(), _sig, _args);
    }

    public MethodReturn(MethodCall _mc, String _sig, Object... _args) throws DBusException {
        this(null, _mc, _sig, _args);
    }

    public MethodReturn(String _source, MethodCall _mc, String _sig, Object... _args) throws DBusException {
        this(_source, _mc.getSource(), _mc.getSerial(), _sig, _args);
        this.call = _mc;
    }

    public MethodCall getCall() {
        return call;
    }

    public void setCall(MethodCall _call) {
        this.call = _call;
    }
}
