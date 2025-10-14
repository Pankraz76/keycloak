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
package org.keycloak.testsuite.util.cli;

import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

public class GroupCommands {

    public static class Create extends AbstractCommand {

        private String groupPrefix;
        private String realmName;

        @Override
        public String getName() {
            return "createGroups";
        }

        @Override
        protected void doRunCommand(KeycloakSession session) {
            groupPrefix = getArg(0);
            realmName = getArg(1);
            int first = getIntArg(2);
            int count = getIntArg(3);
            int batchCount = getIntArg(4);

            BatchTaskRunner.runInBatches(first, count, batchCount, session.getKeycloakSessionFactory(), this::createGroupsInBatch);

            log.infof("Command finished. All groups from %s to %s created", groupPrefix + first, groupPrefix
                    + (first + count - 1));
        }

        private void createGroupsInBatch(KeycloakSession session, int first, int count) {
            RealmModel realm = session.realms().getRealmByName(realmName);
            if (realm == null) {
                log.errorf("Unknown realm: %s", realmName);
                throw new HandledException();
            }

            int last = first + count;
            for (int counter = first; counter < last; counter++) {
                String groupName = groupPrefix + counter;
                GroupModel group = session.groups().createGroup(realm, groupName);
                group.setSingleAttribute("test-attribute", groupName + "_testAttribute");
            }
            log.infof("groups from %s to %s created", groupPrefix + first, groupPrefix + (last - 1));
        }

        @Override
        public String printUsage() {
            return super.printUsage() + " <group-prefix> <realm-name> <starting-group-offset> <total-count> <batch-size>. " +
                    "\n'total-count' refers to total count of newly created groups. 'batch-size' refers to number of created groups in each transaction. 'starting-group-offset' refers to starting group offset." +
                    "\nFor example if 'starting-group-offset' is 15 and total-count is 10 and group-prefix is 'test', it will create groups test15, test16, test17, ... , test24" +
                    "Example usage: " + super.printUsage() + " test demo 0 500 100";
        }

    }

}
