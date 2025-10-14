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
package org.keycloak.client.registration.cli.commands;

import org.keycloak.client.cli.common.BaseAuthOptionsCmd;
import org.keycloak.client.registration.cli.KcRegMain;

import picocli.CommandLine.Option;

/**
 * @author <a href="mailto:mstrukel@redhat.com">Marko Strukelj</a>
 */
public abstract class AbstractAuthOptionsCmd extends BaseAuthOptionsCmd {

    @Option(names = {"-t", "--token"}, description = "Initial / Registration access token to use)")
    public void setToken(String token) {
        this.externalToken = token;
    }

    public AbstractAuthOptionsCmd() {
        super(KcRegMain.COMMAND_STATE);
    }

}
