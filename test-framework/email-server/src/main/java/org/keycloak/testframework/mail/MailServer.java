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

import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.user.TokenValidator;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.keycloak.testframework.injection.ManagedTestResource;

public class MailServer extends ManagedTestResource {

    private final GreenMail greenMail;

    public MailServer(String host, int port) {
        ServerSetup setup = new ServerSetup(port, host, "smtp");

        greenMail = new GreenMail(setup);
        greenMail.start();
    }

    public void stop() {
        greenMail.stop();
    }

    public void credentials(String username, String password) {
        greenMail.setUser(username, password);
    }

    public void credentials(String username, TokenValidator validator) {
        greenMail.setUser(username, null);
        GreenMailUser user = greenMail.getUserManager().getUser(username);
        // greenmail refactoring required, see https://github.com/greenmail-mail-test/greenmail/pull/838
        ((com.icegreen.greenmail.user.UserImpl)user).setTokenValidator(validator);
    }

    public MimeMessage[] getReceivedMessages() {
        return greenMail.getReceivedMessages();
    }

    public MimeMessage getLastReceivedMessage() {
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        return receivedMessages != null && receivedMessages.length > 0 ? receivedMessages[receivedMessages.length - 1] : null;
    }

    public boolean waitForIncomingEmail(long timeout, int emailCount) {
        return greenMail.waitForIncomingEmail(timeout, emailCount);
    }

    public boolean waitForIncomingEmail(int emailCount) {
        return greenMail.waitForIncomingEmail(emailCount);
    }

    @Override
    public void runCleanup() {
        try {
            greenMail.purgeEmailFromAllMailboxes();
        } catch (FolderException e) {
            throw new RuntimeException(e);
        }
    }
}
