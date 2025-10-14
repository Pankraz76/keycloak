/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates
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
package org.keycloak.theme.beans;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * Method used to format the link expiration time period in emails.
 *
 * @author Vlastimil Elias (velias at redhat dot com)
 */
public class LinkExpirationFormatterMethod implements TemplateMethodModelEx {

    protected final Properties messages;
    protected final Locale locale;

    public LinkExpirationFormatterMethod(Properties messages, Locale locale) {
        this.messages = messages;
        this.locale = locale;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Object val = arguments.isEmpty() ? null : arguments.get(0);
        if (val == null)
            return "";

        try {
            //input value is in minutes, as defined in EmailTemplateProvider!
            return format(Long.parseLong(val.toString().trim()) * 60);
        } catch (NumberFormatException e) {
            // not a number, return it as is
            return val.toString();
        }

    }

    protected String format(long valueInSeconds) {

        String unitKey = "seconds";
        long value = valueInSeconds;

        if (value > 0 && value % 60 == 0) {
            unitKey = "minutes";
            value = value / 60;
            if (value % 60 == 0) {
                unitKey = "hours";
                value = value / 60;
                if (value % 24 == 0) {
                    unitKey = "days";
                    value = value / 24;
                }
            }
        }

        return value + " " + MessageFormat.format(messages.getProperty("linkExpirationFormatter.timePeriodUnit." + unitKey), value);
    }
}
