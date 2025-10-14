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
package org.keycloak.locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

public class DefaultLocaleSelectorProviderTest {

    private static final Locale LOCALE_DE_CH = Locale.forLanguageTag("de-CH");
    private static final Locale LOCALE_DE_CH_1996 = Locale.forLanguageTag("de-CH-1996");
    private static final Locale LOCALE_DE_AT = Locale.forLanguageTag("de-AT");

    @Test
    public void findBestMatchingLocaleReturnsExactLocaleInCaseOfExactMatch() {
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(Arrays.asList(Locale.FRENCH, Locale.GERMAN),
                "de-CH-1996", "de-CH", "de"), equalTo(Locale.GERMAN));
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(Arrays.asList(Locale.FRENCH, LOCALE_DE_CH),
                "de-CH-1996", "de-CH", "de"), equalTo(LOCALE_DE_CH));
        assertThat(
                DefaultLocaleSelectorProvider.findBestMatchingLocale(Arrays.asList(Locale.FRENCH, LOCALE_DE_CH_1996),
                        "de-CH-1996", "de-CH", "de"),
                equalTo(LOCALE_DE_CH_1996));


        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(
                Arrays.asList(Locale.FRENCH, LOCALE_DE_CH_1996, LOCALE_DE_CH, Locale.GERMAN),
                "de"), equalTo(Locale.GERMAN));
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(
                Arrays.asList(Locale.FRENCH, LOCALE_DE_CH_1996, LOCALE_DE_CH, Locale.GERMAN),
                "de-CH"), equalTo(LOCALE_DE_CH));
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(
                Arrays.asList(Locale.FRENCH, LOCALE_DE_CH_1996, LOCALE_DE_CH, Locale.GERMAN),
                "de-CH-1996"), equalTo(LOCALE_DE_CH_1996));
    }

    @Test
    public void findBestMatchingLocaleForRegionReturnsLanguageWhenNoLocaleForRegionDefined() {
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(
                Arrays.asList(Locale.FRENCH, Locale.GERMAN),
                "de-CH"), equalTo(Locale.GERMAN));
    }

    @Test
    public void findBestMatchingLocaleForLanguageReturnsLanguageWhenLocalesForBothLanguageAndRegionDefined() {
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(
                Arrays.asList(Locale.FRENCH, LOCALE_DE_CH, Locale.GERMAN),
                "de"), equalTo(Locale.GERMAN));
    }

    /*
     * TODO:
     * Unclear whether this is really expected behavior: when just a language is requested ("de"), and the language
     * is not in the supported locales, but a language with region is specified ("de-CH"), the language with region
     * will be used. The other option would be to return null which would fall back to english.
     */
    @Test
    public void findBestMatchingLocaleForLanguageReturnsRegionWhenNoLocaleForLanguageDefined() {
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(
                Arrays.asList(Locale.FRENCH, LOCALE_DE_CH),
                "de"), equalTo(LOCALE_DE_CH));
    }

    @Test
    public void findBestMatchingLocaleReturnsNullWhenNoMatchingLanguageIsFound() {
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(Arrays.asList(Locale.FRENCH, Locale.GERMAN),
                "cs"), nullValue());
    }

    @Test
    public void findBestMatchingLocaleForRegionReturnsNullInCaseOfDifferentRegionsForSameLanguage() {
        assertThat(DefaultLocaleSelectorProvider.findBestMatchingLocale(
                Arrays.asList(Locale.FRENCH, LOCALE_DE_AT),
                "de-CH"), nullValue());
    }

}
