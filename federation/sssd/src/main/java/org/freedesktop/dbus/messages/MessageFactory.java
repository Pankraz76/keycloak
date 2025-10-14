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

import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.errors.Error;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.exceptions.MessageTypeException;
import org.freedesktop.dbus.utils.Hexdump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class MessageFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageFactory.class);

    private MessageFactory() {}

    public static Message createMessage(byte _type, byte[] _buf, byte[] _header, byte[] _body, List<FileDescriptor> _filedescriptors) throws DBusException, MessageTypeException {
        Message m;
        switch (_type) {
            case Message.MessageType.METHOD_CALL:
                m = new MethodCall();
                break;
            case Message.MessageType.METHOD_RETURN:
                m = new MethodReturn();
                break;
            case Message.MessageType.SIGNAL:
                m = new DBusSignal();
                break;
            case Message.MessageType.ERROR:
                m = new Error();
                break;
            default:
                throw new MessageTypeException(String.format("Message type %s unsupported", _type));
        }

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(Hexdump.format(_buf));
            LOGGER.trace(Hexdump.format(_header));
            LOGGER.trace(Hexdump.format(_body));
        }

        m.populate(_buf, _header, _body, _filedescriptors);
        return m;
    }

}
