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

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <a href="mailto:external.benjamin.weimer@bosch-si.com">Benjamin Weimer</a>,
 */
public class RegexUtilsTest {
    @Test
    public void valueMatchesRegexTest() {
        assertThat(RegexUtils.valueMatchesRegex("AB.*", "AB_ADMIN"), is(true));
        assertThat(RegexUtils.valueMatchesRegex("AB.*", "AA_ADMIN"), is(false));
        assertThat(RegexUtils.valueMatchesRegex("99.*", 999), is(true));
        assertThat(RegexUtils.valueMatchesRegex("98.*", 999), is(false));
        assertThat(RegexUtils.valueMatchesRegex("99\\..*", 99.9), is(true));
        assertThat(RegexUtils.valueMatchesRegex("AB.*", null), is(false));
        assertThat(RegexUtils.valueMatchesRegex("AB.*", Arrays.asList("AB_ADMIN", "AA_ADMIN")), is(true));
    }

}
