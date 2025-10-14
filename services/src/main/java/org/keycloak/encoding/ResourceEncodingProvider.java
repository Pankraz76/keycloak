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
package org.keycloak.encoding;

import org.keycloak.provider.Provider;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceEncodingProvider extends Provider {

    InputStream getEncodedStream(StreamSupplier producer, String... path);

    String getEncoding();

    @Override
    default void close() {
    }

    interface StreamSupplier {

        InputStream getInputStream() throws IOException;

    }

}
