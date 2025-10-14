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
package org.keycloak.testsuite.i18n;

import org.junit.Test;
import org.keycloak.admin.client.resource.RealmLocalizationResource;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

public class RealmLocalizationTest extends AbstractI18NTest {

    /**
     * Make sure that realm localization texts support unicode ().
     */
    @Test
    public void realmLocalizationTextsSupportUnicode() {
        String locale = "en";
        String key = "Äǜṳǚǘǖ";
        String text = "Öṏṏ";
        RealmLocalizationResource localizationResource = testRealm().localization();
        localizationResource.saveRealmLocalizationText(locale, key, text);

        Map<String, String> localizationTexts = localizationResource.getRealmLocalizationTexts(locale, false);

        assertThat(localizationTexts, hasEntry(key, text));
    }

}
