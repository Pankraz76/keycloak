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

import java.util.List;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import static org.keycloak.client.cli.util.IoUtil.printOut;

/**
 * @author <a href="mailto:mstrukel@redhat.com">Marko Strukelj</a>
 */
@Command(name = "help", description = "This help")
public class HelpCmd implements Runnable {

    @Parameters
    private List<String> args;

    @Override
    public void run() {
        if (args == null || args.size() == 0) {
            printOut(KcRegCmd.usage());
        } else {
            outer:
            switch (args.get(0)) {
                case "config": {
                    if (args.size() > 1) {
                        switch (args.get(1)) {
                            case "credentials": {
                                printOut(new ConfigCredentialsCmd().help());
                                break outer;
                            }
                            case "initial-token": {
                                printOut(ConfigInitialTokenCmd.usage());
                                break outer;
                            }
                            case "registration-token": {
                                printOut(ConfigRegistrationTokenCmd.usage());
                                break outer;
                            }
                            case "truststore": {
                                printOut(new ConfigTruststoreCmd().help());
                                break outer;
                            }
                        }
                    }
                    printOut(ConfigCmd.usage());
                    break;
                }
                case "create": {
                    printOut(new CreateCmd().help());
                    break;
                }
                case "get": {
                    printOut(new GetCmd().help());
                    break;
                }
                case "update": {
                    printOut(new UpdateCmd().help());
                    break;
                }
                case "delete": {
                    printOut(new DeleteCmd().help());
                    break;
                }
                case "attrs": {
                    printOut(AttrsCmd.usage());
                    break;
                }
                case "update-token": {
                    printOut(new UpdateTokenCmd().help());
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown command: " + args.get(0));
                }
            }
        }
    }
}
