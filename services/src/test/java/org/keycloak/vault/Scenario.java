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
package org.keycloak.vault;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A small helper class to navigate to proper vault directory.
 *
 * @author Sebastian Łaskawiec
 */
enum Scenario {
    EXISTING("src/test/resources/org/keycloak/vault"),
    NON_EXISTING("src/test/resources/org/keycloak/vault/non-existing"),
    WRITABLE_IN_RUNTIME("target/test-classes");

    Path path;

    Scenario(String path) {
        this.path = Paths.get(path);
    }

    public Path getPath() {
        return path;
    }

    public String getAbsolutePathAsString() {
        return path.toAbsolutePath().toString();
    }

}
