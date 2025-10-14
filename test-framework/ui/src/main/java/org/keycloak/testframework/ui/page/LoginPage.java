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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "[type=submit]")
    private WebElement submitButton;

    @FindBy(id = "rememberMe")
    private WebElement rememberMe;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void fillLogin(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    public void submit() {
        submitButton.click();
    }

    public void clickSocial(String alias) {
        WebElement socialButton = findSocialButton(alias);
        socialButton.click();
    }

    public WebElement findSocialButton(String alias) {
        String id = "social-" + alias;
        return driver.findElement(By.id(id));
    }

    public void rememberMe(boolean value) {
        boolean selected = isRememberMe();
        if ((value && !selected) || !value && selected) {
            rememberMe.click();
        }
    }

    public boolean isRememberMe() {
        return rememberMe.isSelected();
    }

    @Override
    public String getExpectedPageId() {
        return "login-login";
    }

    public String getUsername() {
        return usernameInput.getAttribute("value");
    }

    public void clearUsernameInput() {
        usernameInput.clear();
    }
}
