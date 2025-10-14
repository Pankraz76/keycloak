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

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class MessageHandlerFactoryImpl implements MessageHandlerFactory {

    MimeMessage message;

    public MessageHandler create(MessageContext ctx) {
        return new Handler(ctx);
    }

    class Handler implements MessageHandler {
        MessageContext ctx;



        public Handler(MessageContext ctx) {
            this.ctx = ctx;
        }

        public void from(String from) throws RejectException {
            System.out.println("FROM:" + from);
        }

        public void recipient(String recipient) throws RejectException {
            System.out.println("RECIPIENT:" + recipient);
        }

        public void data(InputStream data) throws IOException {
            String rawMail = this.convertStreamToString(data);

            Session session = Session.getDefaultInstance(new Properties());
            InputStream is = new ByteArrayInputStream(rawMail.getBytes());
            try
            {
                message = new MimeMessage(session, is);
                setMessage(message);
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
            }
        }

        public void done() {
            System.out.println("Finished");
        }

        public String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }



    }

    public MimeMessage getMessage(){
        return message;
    }

    public  void setMessage(MimeMessage msg){
        this.message = msg;
    }
}
