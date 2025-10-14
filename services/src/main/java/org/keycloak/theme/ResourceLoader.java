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
package org.keycloak.theme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

public class ResourceLoader {

    public static InputStream getResourceAsStream(String root, String resource) throws IOException {
        if (root == null || resource == null) {
            return null;
        }
        Path rootPath = Path.of("/", root).normalize().toAbsolutePath();
        Path resourcePath = rootPath.resolve(resource).normalize().toAbsolutePath();
        if (resourcePath.startsWith(rootPath)) {
            if (File.separatorChar == '/') {
                resource = resourcePath.toString().substring(1);
            } else {
                resource = resourcePath.toString().substring(2).replace('\\', '/');
            }
            URL url = classLoader().getResource(resource);
            return url != null ? url.openStream() : null;
        } else {
            return null;
        }
    }

    public static InputStream getFileAsStream(File root, String resource) throws IOException {
        File file = getFile(root, resource);
        return file != null && file.isFile() ? file.toURI().toURL().openStream() : null;
    }

    public static File getFile(File root, String resource) throws IOException {
        if (root == null || resource == null) {
            return null;
        }
        Path rootPath = root.toPath().toAbsolutePath().normalize();
        Path resourcePath = rootPath.resolve(resource).normalize().toAbsolutePath();
        if (resourcePath.startsWith(rootPath)) {
            return resourcePath.toFile();
        } else {
            return null;
        }
    }

    private static ClassLoader classLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
