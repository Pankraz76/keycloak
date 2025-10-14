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

/**
 * This class should be extended to create Tuples.
 *
 * Any such class may be used as the return type for a method
 * which returns multiple values.
 *
 * All fields in the Tuple which you wish to be serialized and sent to the
 * remote method should be annotated with the org.freedesktop.dbus.Position
 * annotation, in the order they should appear to DBus.
 */
public abstract class Tuple extends Container {
    public Tuple() {
    }
}
