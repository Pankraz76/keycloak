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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResetOtpPage extends AbstractPage {

    @FindBy(id = "kc-otp-reset-form-submit")
    protected WebElement submitButton;

    @FindBy(id = "kc-otp-reset-form-description")
    protected WebElement description;

    @Override
    public boolean isCurrent() {
        return description.getText().equals("Which OTP configuration should be removed?");
    }

    public void selectOtp(int index) {
        driver.findElement(By.id("kc-otp-credential-" + index)).click();
    }

    public void submitOtpReset() {
        UIUtils.clickLink(submitButton);
    }
}
