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

import java.lang.ref.WeakReference;

/**
 * An alternative to a WeakReference when you don't want
 * that behavior.
 */
public class StrongReference<T> extends WeakReference<T> {
    private T referant;

    public StrongReference(T _referant) {
        super(_referant);
        this.referant = _referant;
    }

    @Override
    public void clear() {
        referant = null;
    }

    @Override
    public boolean enqueue() {
        return false;
    }

    @Override
    public T get() {
        return referant;
    }

}
