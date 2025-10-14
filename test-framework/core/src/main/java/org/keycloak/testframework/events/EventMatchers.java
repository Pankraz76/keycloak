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
package org.keycloak.testframework.events;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.UUID;

public class EventMatchers {

    public static Matcher<String> isUUID() {
        return new UUIDMatcher();
    }

    private EventMatchers() {
    }

    public static class UUIDMatcher extends TypeSafeMatcher<String> {

        @Override
        protected boolean matchesSafely(String item) {
            try {
                UUID.fromString(item);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("not a UUID");
        }
    }

}
