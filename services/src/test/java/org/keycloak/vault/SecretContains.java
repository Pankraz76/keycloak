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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.nio.charset.StandardCharsets;

/**
 * Checks if {@link VaultRawSecret} is equal to a String.
 */
public class SecretContains extends TypeSafeMatcher<VaultRawSecret> {

    private String thisVaultAsString;

    public SecretContains(String thisVaultAsString) {
        this.thisVaultAsString = thisVaultAsString;
    }

    @Override
    protected boolean matchesSafely(VaultRawSecret secret) {
        String convertedSecret = StandardCharsets.UTF_8.decode(secret.get().get()).toString();
        return thisVaultAsString.equals(convertedSecret);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is equal to " + thisVaultAsString);
    }

    public static Matcher<VaultRawSecret> secretContains(String thisVaultAsString) {
        return new SecretContains(thisVaultAsString);
    }
}
