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
package org.keycloak.testsuite.console.page.fragment;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.fragment.Root;
import org.jboss.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import static org.keycloak.testsuite.util.UIUtils.clickLink;
import static org.keycloak.testsuite.util.UIUtils.getTextFromElement;

/**
 * @author Vaclav Muzikar <vmuzikar@redhat.com>
 */
public class LocaleDropdown {
    protected Logger log = Logger.getLogger(this.getClass());

    @Root
    private WebElement root;

    @FindBy(tagName = "ul")
    private WebElement dropDownMenu;

    @FindBy(id = "kc-current-locale-link")
    private WebElement currentLocaleLink;

    @Drone
    private WebDriver driver;

    public String getSelected() {
        return getTextFromElement(currentLocaleLink);
    }

    public void selectByText(String text) {
        // open the menu
        Actions actions = new Actions(driver);
        log.info("Moving mouse cursor to the localization menu");
        actions.moveToElement(currentLocaleLink, -10, 0)
                .moveToElement(currentLocaleLink).perform();

        // click desired locale
        clickLink(dropDownMenu.findElement(By.xpath("./li/a[text()='" + text + "']")));
    }
}
