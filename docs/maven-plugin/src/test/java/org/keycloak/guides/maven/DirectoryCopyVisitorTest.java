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
package org.keycloak.guides.maven;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DirectoryCopyVisitorTest {

	@TempDir
	Path temp;

	private Path srcDir;
	private Path targetDir;

	@BeforeEach
	void setUpDirectories() throws IOException {
		srcDir = temp.resolve("source");
		targetDir = temp.resolve("target");
		Files.createDirectories(srcDir);
		Files.createDirectories(targetDir);
	}

	@Test
	void copyDirectoriesMultipleLevels() throws IOException {
		Path level1 = srcDir.resolve("level1");
		Path level2a = level1.resolve("level2a");
		Path level2b = level1.resolve("level2b");
		Path level3 = level2a.resolve("level3");
		Files.createDirectories(level3);
		Files.createDirectories(level2b);
		Files.createFile(srcDir.resolve("rootfile"));
		Files.createFile(level1.resolve("l1file"));
		Files.createFile(level2b.resolve("l2filea"));
		Files.createFile(level2b.resolve("l2fileb"));

		Files.walkFileTree(srcDir, new DirectoryCopyVisitor(targetDir));

		assertEquals(List.of("level1", "rootfile"), listDirContent(targetDir));
		assertEquals(List.of("l1file", "level2a", "level2b"), listDirContent(targetDir.resolve("level1")));
		assertEquals(List.of("level3"), listDirContent(targetDir.resolve("level1").resolve("level2a")));
		assertEquals(List.of(), listDirContent(targetDir.resolve("level1").resolve("level2a").resolve("level3")));
		assertEquals(List.of("l2filea", "l2fileb"), listDirContent(targetDir.resolve("level1").resolve("level2b")));
	}

	private List<String> listDirContent(Path path) throws IOException {
		return Files.list(path).map(Path::getFileName).map(Path::toString).sorted().toList();
	}
}
