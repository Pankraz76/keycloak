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
package org.keycloak.testsuite.pages;

import org.keycloak.testsuite.util.UIUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * login page for UsernameForm. It contains only username, but not password
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class LoginUsernameOnlyPage extends LoginPage {

    @FindBy(id = "input-error-username")
    private WebElement usernameError;

    @Override
    public void login(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);

        UIUtils.clickLink(submitButton);
    }

    public String getUsernameError() {
        try {
            return UIUtils.getTextFromElement(usernameError);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    // Click button without fill anything
    public void clickSubmitButton() {
        UIUtils.clickLink(submitButton);
    }

    /**
     * Not supported for this implementation
     *
     * @return
     */
    @Deprecated
    @Override
    public void login(String username, String password) {
        throw new UnsupportedOperationException("Not supported - password field not available");
    }


    /**
     * Not supported for this implementation
     * @return
     */
    @Deprecated
    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("Not supported - password field not available");
    }


    /**
     * Not supported for this implementation
     * @return
     */
    @Deprecated
    @Override
    public void missingPassword(String username) {
        throw new UnsupportedOperationException("Not supported - password field not available");
    }


    @Override
    public boolean isCurrent(String realm) {
        if (!super.isCurrent(realm)) {
            return false;
        }

        // Check there is username field
        try {
            driver.findElement(By.id("username"));
        } catch (NoSuchElementException nfe) {
            return false;
        }

        // Check there is NO password field
        try {
            driver.findElement(By.id("password"));
            return false;
        } catch (NoSuchElementException nfe) {
            // Expected
        }

        return true;
    }
}
