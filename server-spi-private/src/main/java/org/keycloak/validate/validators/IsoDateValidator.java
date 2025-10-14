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
package org.keycloak.validate.validators;

import org.keycloak.provider.ConfiguredProvider;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.validate.AbstractStringValidator;
import org.keycloak.validate.ValidationContext;
import org.keycloak.validate.ValidationError;
import org.keycloak.validate.ValidatorConfig;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;


/**
 * A date validator that only takes into account the format associated with the current locale.
 */
public class IsoDateValidator extends AbstractStringValidator implements ConfiguredProvider {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static final String MESSAGE_INVALID_DATE = "error-invalid-date";

    public static final IsoDateValidator INSTANCE = new IsoDateValidator();

    public static final String ID = "iso-date";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected void doValidate(String value, String inputHint, ValidationContext context, ValidatorConfig config) {
        try {
            FORMATTER.parse(value);
        } catch (DateTimeParseException e) {
            context.addError(new ValidationError(getId(), inputHint, MESSAGE_INVALID_DATE, value));
        }
    }

    @Override
    public String getHelpText() {
        return "Validates date in rfc3339/iso8601 format, as provided by the html5-date input.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return Collections.emptyList();
    }

    @Override
    protected boolean isIgnoreEmptyValuesConfigured(ValidatorConfig config) {
        return true;
    }
}
