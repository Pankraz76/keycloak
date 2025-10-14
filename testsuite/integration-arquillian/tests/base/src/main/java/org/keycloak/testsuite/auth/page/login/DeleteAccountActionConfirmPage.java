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
package org.keycloak.testsuite.auth.page.login;

import org.keycloak.authentication.requiredactions.DeleteAccount;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import static org.keycloak.testsuite.util.UIUtils.clickLink;

public class DeleteAccountActionConfirmPage extends RequiredActions {

  @FindBy(css = "[name='cancel-aia']")
  WebElement cancelActionButton;

  @FindBy(css = "[type='submit']")
  WebElement confirmActionButton;

  @Override
  public String getActionId() {
    return DeleteAccount.PROVIDER_ID;
  }

  @Override
  public boolean isCurrent() {
    return driver.getCurrentUrl().contains("login-actions/required-action") && driver.getCurrentUrl().contains("execution=delete_account");
  }


  public void clickCancelAIA() {
    clickLink(cancelActionButton);
  }

  public void clickConfirmAction() {
    clickLink(confirmActionButton);
  }

  public String getErrorMessageText() {
    return driver.findElement(By.cssSelector("#kc-content-wrapper > div > span.kc-feedback-text")).getText();
  }
}
