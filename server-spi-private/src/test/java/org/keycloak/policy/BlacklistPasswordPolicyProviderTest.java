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
package org.keycloak.policy;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

import static org.keycloak.policy.BlacklistPasswordPolicyProviderFactory.FileBasedPasswordBlacklist;

public class BlacklistPasswordPolicyProviderTest {

    @Test
    public void testUpperCaseInFile() {
        FileBasedPasswordBlacklist blacklist =
                new FileBasedPasswordBlacklist(Paths.get("src/test/java/org/keycloak/policy"), "short_blacklist.txt");
        blacklist.lazyInit();

        // all passwords in the deny list are in lower case
        Assert.assertFalse(blacklist.contains("1Password!"));
    }

    @Test
    public void testAlwaysLowercaseInFile() {
        FileBasedPasswordBlacklist blacklist =
                new FileBasedPasswordBlacklist(Paths.get("src/test/java/org/keycloak/policy"), "short_blacklist.txt");
        blacklist.lazyInit();
        Assert.assertTrue(blacklist.contains("1Password!".toLowerCase()));
    }

    @Test
    public void testLowerCaseInFile() {
        FileBasedPasswordBlacklist blacklist =
                new FileBasedPasswordBlacklist(Paths.get("src/test/java/org/keycloak/policy"), "short_blacklist.txt");
        blacklist.lazyInit();
        Assert.assertTrue(blacklist.contains("pass1!word"));
    }
}
