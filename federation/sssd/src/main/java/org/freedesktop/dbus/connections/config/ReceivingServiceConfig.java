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
package org.freedesktop.dbus.connections.config;

import org.freedesktop.dbus.connections.ReceivingService.IThreadPoolRetryHandler;

/**
 * Bean which holds configuration for {@link org.freedesktop.dbus.connections.ReceivingService}.
 *
 * @author hypfvieh
 * @since 4.2.0 - 2022-07-14
 */
public final class ReceivingServiceConfig {
    private int signalThreadPoolSize       = 1;
    private int errorThreadPoolSize        = 1;
    private int methodCallThreadPoolSize   = 4;
    private int methodReturnThreadPoolSize = 1;
    private int signalThreadPriority       = Thread.NORM_PRIORITY;
    private int methodCallThreadPriority   = Thread.NORM_PRIORITY;
    private int errorThreadPriority        = Thread.NORM_PRIORITY;
    private int methodReturnThreadPriority = Thread.NORM_PRIORITY;

    private IThreadPoolRetryHandler retryHandler;

    ReceivingServiceConfig() {
    }

    public int getSignalThreadPoolSize() {
        return signalThreadPoolSize;
    }

    public int getErrorThreadPoolSize() {
        return errorThreadPoolSize;
    }

    public int getMethodCallThreadPoolSize() {
        return methodCallThreadPoolSize;
    }

    public int getMethodReturnThreadPoolSize() {
        return methodReturnThreadPoolSize;
    }

    public int getSignalThreadPriority() {
        return signalThreadPriority;
    }

    public int getMethodCallThreadPriority() {
        return methodCallThreadPriority;
    }

    public int getErrorThreadPriority() {
        return errorThreadPriority;
    }

    public int getMethodReturnThreadPriority() {
        return methodReturnThreadPriority;
    }

    public IThreadPoolRetryHandler getRetryHandler() {
        return retryHandler;
    }

    void setSignalThreadPoolSize(int _signalThreadPoolSize) {
        signalThreadPoolSize = _signalThreadPoolSize;
    }

    void setErrorThreadPoolSize(int _errorThreadPoolSize) {
        errorThreadPoolSize = _errorThreadPoolSize;
    }

    void setMethodCallThreadPoolSize(int _methodCallThreadPoolSize) {
        methodCallThreadPoolSize = _methodCallThreadPoolSize;
    }

    void setMethodReturnThreadPoolSize(int _methodReturnThreadPoolSize) {
        methodReturnThreadPoolSize = _methodReturnThreadPoolSize;
    }

    void setSignalThreadPriority(int _signalThreadPriority) {
        signalThreadPriority = _signalThreadPriority;
    }

    void setMethodCallThreadPriority(int _methodCallThreadPriority) {
        methodCallThreadPriority = _methodCallThreadPriority;
    }

    void setErrorThreadPriority(int _errorThreadPriority) {
        errorThreadPriority = _errorThreadPriority;
    }

    void setMethodReturnThreadPriority(int _methodReturnThreadPriority) {
        methodReturnThreadPriority = _methodReturnThreadPriority;
    }

    void setRetryHandler(IThreadPoolRetryHandler _retryHandler) {
        retryHandler = _retryHandler;
    }

}
