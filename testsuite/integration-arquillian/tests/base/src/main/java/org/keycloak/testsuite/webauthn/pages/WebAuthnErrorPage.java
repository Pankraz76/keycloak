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
package org.keycloak.testsuite.webauthn.pages;

import org.junit.Assert;
import org.keycloak.testsuite.pages.LanguageComboboxAwarePage;
import org.keycloak.testsuite.util.UIUtils;
import org.keycloak.testsuite.util.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * @author <a href="mailto:mabartos@redhat.com">Martin Bartos</a>
 */
public class WebAuthnErrorPage extends LanguageComboboxAwarePage {

    @FindBy(id = "kc-try-again")
    private WebElement tryAgainButton;

    // Available only with AIA
    @FindBy(id = "cancelWebAuthnAIA")
    private WebElement cancelRegistrationAIA;

    @FindBy(css = "div[class^='pf-v5-c-alert'], div[class^='alert-error']")
    private WebElement errorMessage;

    public void clickTryAgain() {
        WaitUtils.waitUntilElement(tryAgainButton).is().clickable();
        tryAgainButton.click();
    }

    public void clickCancelRegistrationAIA() {
        try {
            WaitUtils.waitUntilElement(cancelRegistrationAIA).is().clickable();
            cancelRegistrationAIA.click();
        } catch (NoSuchElementException e) {
            Assert.fail("It only works with AIA");
        }
    }

    public String getError() {
        try {
            return UIUtils.getTextFromElement(errorMessage);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public boolean isCurrent() {
        try {
            driver.findElement(By.id("kc-try-again"));
            driver.findElement(By.id("kc-error-credential-form"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
