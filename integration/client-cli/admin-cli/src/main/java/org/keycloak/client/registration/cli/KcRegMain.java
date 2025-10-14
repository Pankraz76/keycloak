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
package org.keycloak.client.registration.cli;

import org.keycloak.client.cli.common.CommandState;
import org.keycloak.client.cli.common.Globals;
import org.keycloak.client.cli.util.OsUtil;
import org.keycloak.client.registration.cli.commands.KcRegCmd;

/**
 * @author <a href="mailto:mstrukel@redhat.com">Marko Strukelj</a>
 */
public class KcRegMain {

    public static final String DEFAULT_CONFIG_FILE_PATH = System.getProperty("user.home") + "/.keycloak/kcreg.config";

    public static final String DEFAULT_CONFIG_FILE_STRING = OsUtil.OS_ARCH.isWindows() ? "%HOMEDRIVE%%HOMEPATH%\\.keycloak\\kcreg.config" : "~/.keycloak/kcreg.config";

    public static final String CMD = OsUtil.OS_ARCH.isWindows() ? "kcreg.bat" : "kcreg.sh";

    public static final CommandState COMMAND_STATE = new CommandState() {

        @Override
        public String getCommand() {
            return CMD;
        }

        @Override
        public String getDefaultConfigFilePath() {
            return DEFAULT_CONFIG_FILE_PATH;
        }

        @Override
        public boolean isTokenGlobal() {
            return false;
        };

    };

    public static void main(String [] args) {
        Globals.main(args, new KcRegCmd(), CMD, DEFAULT_CONFIG_FILE_STRING);
    }
}
