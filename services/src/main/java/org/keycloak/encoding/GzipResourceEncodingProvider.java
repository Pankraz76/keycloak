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

import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.keycloak.theme.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.GZIPOutputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GzipResourceEncodingProvider implements ResourceEncodingProvider {

    private static final Logger logger = Logger.getLogger(ResourceEncodingProvider.class);

    private final File cacheDir;

    public GzipResourceEncodingProvider(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    public InputStream getEncodedStream(StreamSupplier producer, String... path) {
        try {
            File encodedFile = ResourceLoader.getFile(cacheDir, String.join("/", path) +  ".gz");
            if (encodedFile == null) {
                return null;
            }

            if (!encodedFile.exists()) {
                encodedFile = createEncodedFile(producer, encodedFile);
            }

            return encodedFile != null ? new FileInputStream(encodedFile) : null;
        } catch (Exception e) {
            logger.warn("Failed to encode resource", e);
            return null;
        }
    }

    public String getEncoding() {
        return "gzip";
    }

    private File createEncodedFile(StreamSupplier producer, File target) throws IOException {
        InputStream is = producer.getInputStream();
        if (is == null) {
            return null;
        }

        File parent = target.getParentFile();
        if (!parent.isDirectory()) {
            if (parent.mkdirs() && !parent.isDirectory()) {
                logger.warnf("Fail to create cache directory %s", parent.toString());
            }
        }
        File tmpEncodedFile = File.createTempFile(target.getName(), "tmp", parent);

        try (is; GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(tmpEncodedFile))) {
            IOUtils.copy(is, gos);
        }

        try {
            Files.move(tmpEncodedFile.toPath(), target.toPath(), REPLACE_EXISTING);
            return target;
        } catch (IOException io) {
            logger.warnf(io, "Fail to move temporary file to %s", target.toString());
            return null;
        }
    }

}
