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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class HtmlUnitWebDriverSupplier extends AbstractWebDriverSupplier {

    @Override
    public String getAlias() {
        return "htmlunit";
    }

    @Override
    public WebDriver getWebDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        setCommonCapabilities(capabilities);

        capabilities.setBrowserName("htmlunit");
        capabilities.setCapability(HtmlUnitDriver.DOWNLOAD_IMAGES_CAPABILITY, false);
        capabilities.setCapability(HtmlUnitDriver.JAVASCRIPT_ENABLED, true);

        HtmlUnitDriver driver = new HtmlUnitDriver(capabilities);
        driver.getWebClient().getOptions().setCssEnabled(false);
        return driver;
    }
}
