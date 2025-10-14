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
package org.keycloak.testframework.ui.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeHeadlessWebDriverSupplier extends AbstractWebDriverSupplier {

    @Override
    public String getAlias() {
        return "chrome-headless";
    }

    @Override
    public WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        setCommonCapabilities(options);
        options.addArguments(
                "--headless",
                "--disable-gpu",
                "--window-size=1920,1200",
                "--ignore-certificate-errors",
                "--disable-dev-shm-usage"
        );
        return new ChromeDriver(options);
    }
}
