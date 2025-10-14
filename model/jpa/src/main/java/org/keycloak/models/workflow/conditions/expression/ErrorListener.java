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
package org.keycloak.models.workflow.conditions.expression;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import java.util.ArrayList;
import java.util.List;

public class ErrorListener extends BaseErrorListener {
    private boolean hasErrors = false;
    private final List<String> errorMessages = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        hasErrors = true;
        String error = String.format("Error at line %d:%d - %s", line, charPositionInLine, msg);
        errorMessages.add(error);
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
