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
package org.keycloak.testframework.ui;

import org.keycloak.testframework.TestFrameworkExtension;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.ui.page.PageSupplier;
import org.keycloak.testframework.ui.webdriver.ChromeHeadlessWebDriverSupplier;
import org.keycloak.testframework.ui.webdriver.ChromeWebDriverSupplier;
import org.keycloak.testframework.ui.webdriver.FirefoxHeadlessWebDriverSupplier;
import org.keycloak.testframework.ui.webdriver.FirefoxWebDriverSupplier;
import org.keycloak.testframework.ui.webdriver.HtmlUnitWebDriverSupplier;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class UITestFrameworkExtension implements TestFrameworkExtension {

    @Override
    public List<Supplier<?, ?>> suppliers() {
        return List.of(
                new HtmlUnitWebDriverSupplier(),
                new ChromeHeadlessWebDriverSupplier(),
                new ChromeWebDriverSupplier(),
                new FirefoxHeadlessWebDriverSupplier(),
                new FirefoxWebDriverSupplier(),
                new PageSupplier()
        );
    }

    @Override
    public Map<Class<?>, String> valueTypeAliases() {
        return Map.of(
                WebDriver.class, "browser"
        );
    }

}
