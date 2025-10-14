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
package org.keycloak.testframework.oauth;

import org.keycloak.representations.adapters.action.LogoutAction;
import org.keycloak.representations.adapters.action.PushNotBeforeAction;
import org.keycloak.representations.adapters.action.TestAvailabilityAction;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class KcAdminInvocations {

    private final BlockingQueue<LogoutAction> adminLogoutActions = new LinkedBlockingQueue<>();
    private final BlockingQueue<PushNotBeforeAction> adminPushNotBeforeActions = new LinkedBlockingQueue<>();
    private final BlockingQueue<TestAvailabilityAction> adminTestAvailabilityAction = new LinkedBlockingQueue<>();

    KcAdminInvocations() {
    }

    public PushNotBeforeAction getAdminPushNotBefore() throws InterruptedException {
        return adminPushNotBeforeActions.poll(10, TimeUnit.SECONDS);
    }

    void add(PushNotBeforeAction action) {
        adminPushNotBeforeActions.add(action);
    }

    public TestAvailabilityAction getTestAvailable() throws InterruptedException {
        return adminTestAvailabilityAction.poll(10, TimeUnit.SECONDS);
    }

    void add(TestAvailabilityAction action) {
        adminTestAvailabilityAction.add(action);
    }

    public LogoutAction getAdminLogoutAction() throws InterruptedException {
        return adminLogoutActions.poll(10, TimeUnit.SECONDS);
    }

    void add(LogoutAction action) {
        adminLogoutActions.add(action);
    }

    public void clear() {
        adminLogoutActions.clear();
        adminPushNotBeforeActions.clear();
        adminTestAvailabilityAction.clear();
    }

}
