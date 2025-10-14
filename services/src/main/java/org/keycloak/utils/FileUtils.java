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
package org.keycloak.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.keycloak.services.clientpolicy.ClientPoliciesUtil;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class FileUtils {

    /**
     * Read the input stream from the specified file
     *
     * @param fileName file name without path
     * @return input stream
     * @throws IOException
     */
    public static InputStream getJsonFileFromClasspathOrConfFolder(String fileName) throws IOException {
        // first try to read the json configuration file from classpath
        InputStream is = ClientPoliciesUtil.class.getResourceAsStream("/" + fileName);
        if (is == null) {
            Path path = Paths.get(System.getProperty("jboss.server.config.dir")).resolve(fileName);
            if (!Files.isReadable(path)) {
                throw new IOException(String.format("File \"%s\" does not exists under the config folder", path));
            }
            is = Files.newInputStream(path);
        }
        return is;
    }
}
