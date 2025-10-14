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

import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.ui.annotations.InjectWebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;

import java.time.Duration;
import java.util.Map;

public abstract class AbstractWebDriverSupplier implements Supplier<WebDriver, InjectWebDriver> {

    @Override
    public WebDriver getValue(InstanceContext<WebDriver, InjectWebDriver> instanceContext) {
        return getWebDriver();
    }

    @Override
    public boolean compatible(InstanceContext<WebDriver, InjectWebDriver> a, RequestedInstance<WebDriver, InjectWebDriver> b) {
        return true;
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return LifeCycle.GLOBAL;
    }

    @Override
    public void close(InstanceContext<WebDriver, InjectWebDriver> instanceContext) {
        instanceContext.getValue().quit();
    }

    public abstract WebDriver getWebDriver();

    public void setCommonCapabilities(MutableCapabilities capabilities) {
        capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, PageLoadStrategy.NORMAL.toString());
        capabilities.setCapability("timeouts", Map.of("implicit", Duration.ofSeconds(5).toMillis()));
    }

}
