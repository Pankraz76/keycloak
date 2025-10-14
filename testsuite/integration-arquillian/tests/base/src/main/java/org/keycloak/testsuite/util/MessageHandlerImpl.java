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
package org.keycloak.testsuite.util;

import org.jboss.logging.Logger;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;

import java.io.InputStream;

public class MessageHandlerImpl implements MessageHandler {
    MessageContext context;

    private static final Logger log = Logger.getLogger(MessageHandlerImpl.class);

    MessageHandlerImpl(MessageContext context) {
        this.context = context;
    }

    @Override
    public void from(String from) {
        log.info("FROM: ${from}");
    }

    @Override
    public void recipient(String recipient) {
        log.info("RECIPIENT: ${recipient}");
    }

    @Override
    public void data(InputStream data) {
        log.info("DATA");
    }

    @Override
    public void done() {
        log.info("DONE");
    }
}
