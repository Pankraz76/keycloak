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

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceLoaderTest {

    static String NONE = "../";
    static String SINGLE = "%2E%2E%2F";
    static String DOUBLE = "%252E%252E%252F";

    @Test
    public void testResource() throws IOException {
        String parent = "dummy-resources/parent";
        assertResourceAsStream(parent, "myresource.css", true, true);
        assertResourceAsStream(parent, NONE + "myresource.css", false, true);
        assertResourceAsStream(parent, SINGLE + "myresource.css", false, false);
        assertResourceAsStream(parent, DOUBLE + "myresource.css", false, false);

        assertResourceAsStream(parent, "one/" + NONE + "myresource.css", true, true);
        assertResourceAsStream(parent, "one/" + SINGLE + "myresource.css", false, false);
        assertResourceAsStream(parent, "one/" + DOUBLE + "myresource.css", false, false);

        assertResourceAsStream(parent, "one/two/" + NONE + NONE + "myresource.css", true, true);
        assertResourceAsStream(parent, "one/" + NONE + NONE + "myresource.css", false, true);
    }

    @Test
    public void testFiles() throws IOException {
        Path tempDirectory = Files.createTempDirectory("safepath-test");

        File parent = new File(tempDirectory.toFile(), "resources");
        Assert.assertTrue(parent.mkdir());

        new FileOutputStream(new File(tempDirectory.toFile(), "myresource.css")).close();
        new FileOutputStream(new File(parent, "myresource.css")).close();

        assertFileAsStream(parent, "myresource.css", true, true);
        assertFileAsStream(parent, NONE + "myresource.css", false, true);
        assertFileAsStream(parent, SINGLE + "myresource.css", false, false);
        assertFileAsStream(parent, DOUBLE + "myresource.css", false, false);

        assertFileAsStream(new File(tempDirectory.toFile(), "test/../resources/"), "myresource.css", true, true);

        // relativize tmp folder to the current working directory, something like ../../../tmp/path
        Path relativeParent = Paths.get(".").toAbsolutePath().relativize(parent.toPath());
        assertFileAsStream(relativeParent.toFile(), "myresource.css", true, true);
    }

    private void assertResourceAsStream(String parent, String resource, boolean expectValid, boolean expectResourceToExist) throws IOException {
        InputStream verified = ResourceLoader.getResourceAsStream(parent, resource);
        if (expectValid) {
            Assert.assertNotNull(verified);
        } else {
            Assert.assertNull(verified);
        }

        if (expectResourceToExist) {
            Assert.assertNotNull(ResourceLoader.class.getClassLoader().getResource(parent + "/" + resource));
        }
    }

    private void assertFileAsStream(File parent, String resource, boolean expectValid, boolean expectFileToExist) throws IOException {
        InputStream verified = ResourceLoader.getFileAsStream(parent, resource);
        if (expectValid) {
            Assert.assertNotNull(verified);
        } else {
            Assert.assertNull(verified);
        }

        if (expectFileToExist) {
            Assert.assertTrue(new File(parent, resource).getCanonicalFile().isFile());
        }
    }

}
