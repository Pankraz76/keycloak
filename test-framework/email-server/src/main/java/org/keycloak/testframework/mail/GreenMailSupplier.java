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
package org.keycloak.testframework.mail;

import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierOrder;
import org.keycloak.testframework.mail.annotations.InjectMailServer;
import org.keycloak.testframework.realm.RealmConfigBuilder;
import org.keycloak.testframework.realm.RealmConfigInterceptor;

public class GreenMailSupplier implements Supplier<MailServer, InjectMailServer>, RealmConfigInterceptor<MailServer, InjectMailServer> {

    private final String HOSTNAME = "localhost";
    private final int PORT = 3025;
    private final String FROM = "auto@keycloak.org";

    @Override
    public MailServer getValue(InstanceContext<MailServer, InjectMailServer> instanceContext) {
        return new MailServer(HOSTNAME, PORT);
    }

    @Override
    public void close(InstanceContext<MailServer, InjectMailServer> instanceContext) {
        instanceContext.getValue().stop();
    }

    @Override
    public boolean compatible(InstanceContext<MailServer, InjectMailServer> a, RequestedInstance<MailServer, InjectMailServer> b) {
        return true;
    }

    @Override
    public RealmConfigBuilder intercept(RealmConfigBuilder realm, InstanceContext<MailServer, InjectMailServer> instanceContext) {
        return realm.smtp(HOSTNAME, PORT, FROM);
    }

    @Override
    public int order() {
        return SupplierOrder.BEFORE_REALM;
    }
}
