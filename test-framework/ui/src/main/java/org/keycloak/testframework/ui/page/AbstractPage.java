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
package org.keycloak.testframework.ui.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractPage {

    @FindBy(xpath = "//body")
    private WebElement body;

    protected final WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public abstract String getExpectedPageId();

    public String getCurrentPageId() {
        return body.getAttribute("data-page-id");
    }

    public void waitForPage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> isActivePage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Waiting for '" + getExpectedPageId() + "', but was '" + getCurrentPageId() + "'");
        }
    }

    private boolean isActivePage() {
        return getExpectedPageId().equals(getCurrentPageId());
    }

    public void assertCurrent() {
        Assertions.assertEquals(getExpectedPageId(), getCurrentPageId(), "Not on the expected page");
    }
}
