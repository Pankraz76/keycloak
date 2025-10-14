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
package org.freedesktop.dbus.interfaces;

/**
 * Denotes a class as exportable or a remote interface which can be called.
 * <p>
 * Any interface which should be exported or imported should extend this interface. All public methods from that
 * interface are exported/imported with the given method signatures.
 * </p>
 * <p>
 * All method calls on exported objects are run in their own threads. Application writers are responsible for any
 * concurrency issues.
 * </p>
 */
public interface DBusInterface {

    /**
     * Returns true on remote objects. Local objects implementing this interface MUST return false.
     *
     * @return boolean
     */
    default boolean isRemote() {
        return false;
    }

    /**
     * Returns the path of this object.
     *
     * @return string
     */
    String getObjectPath();

}
