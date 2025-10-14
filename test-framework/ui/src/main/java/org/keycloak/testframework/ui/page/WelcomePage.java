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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WelcomePage extends AbstractPage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "password-confirmation")
    private WebElement passwordConfirmationInput;

    @FindBy(xpath = "//button")
    private WebElement submitButton;

    @FindBy(css = ".pf-v5-c-alert")
    private WebElement pageAlert;

    @FindBy(css = ".pf-v5-c-title")
    private WebElement welcomeMessage;

    @FindBy(css = ".pf-v5-c-login__main-header-desc")
    private WebElement welcomeDescription;

    @FindBy(css = ".pf-v5-c-button")
    private WebElement openAdminConsoleLink;

    public WelcomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get("http://localhost:8080");
    }

    public void fillRegistration(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        passwordConfirmationInput.sendKeys(password);
    }

    public void submit() {
        submitButton.click();
    }

    public void clickOpenAdminConsole() {
        openAdminConsoleLink.click();
    }

    public String getWelcomeMessage() {
        return welcomeMessage.getText();
    }

    public String getWelcomeDescription() {
        return welcomeDescription.getText();
    }

    public String getPageAlert() {
        return pageAlert.getText();
    }

    @Override
    public String getExpectedPageId() {
        return "welcome";
    }
}
