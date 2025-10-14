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
package org.freedesktop.dbus.connections;

import org.freedesktop.dbus.DBusAsyncReply;
import org.freedesktop.dbus.interfaces.CallbackHandler;
import org.freedesktop.dbus.messages.MethodCall;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PendingCallbackManager {
    private final Map<MethodCall, CallbackHandler<? extends Object>> pendingCallbacks;
    private final Map<MethodCall, DBusAsyncReply<?>>                 pendingCallbackReplys;

    PendingCallbackManager() {
        pendingCallbacks = new ConcurrentHashMap<>();
        pendingCallbackReplys = new ConcurrentHashMap<>();
    }

    public synchronized void queueCallback(MethodCall _call, Method _method, CallbackHandler<?> _callback, AbstractConnection _connection) {
        pendingCallbacks.put(_call, _callback);
        pendingCallbackReplys.put(_call, new DBusAsyncReply<>(_call, _method, _connection));

    }

    public synchronized CallbackHandler<? extends Object> removeCallback(MethodCall _methodCall) {
        pendingCallbackReplys.remove(_methodCall);
        return pendingCallbacks.remove(_methodCall);
    }

    public synchronized CallbackHandler<? extends Object> getCallback(MethodCall _methodCall) {
        return pendingCallbacks.get(_methodCall);
    }

    public synchronized DBusAsyncReply<?> getCallbackReply(MethodCall _methodCall) {
        return pendingCallbackReplys.get(_methodCall);
    }

}
