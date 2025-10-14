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
package org.keycloak.services.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/**
 * Represents a chunk from the Vite build manifest (see {@link ViteManifest}).
 */
public record Chunk (
    @JsonProperty(required = true)
    String file,

    @JsonProperty
    Optional<String> src,

    @JsonProperty
    Optional<String> name,

    @JsonProperty
    Optional<Boolean> isEntry,

    @JsonProperty
    Optional<Boolean> isDynamicEntry,

    @JsonProperty
    Optional<String[]> imports,

    @JsonProperty
    Optional<String[]> dynamicImports,

    @JsonProperty
    Optional<String[]> assets,

    @JsonProperty Optional<String[]> css
){}
