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
package org.keycloak.scripting;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.keycloak.models.ScriptModel;

/**
 * Wraps a compiled {@link ScriptModel} so it can be evaluated.
 *
 * @author <a href="mailto:jay@anslow.me.uk">Jay Anslow</a>
 */
class CompiledEvaluatableScriptAdapter extends AbstractEvaluatableScriptAdapter {
    /**
     * Holds the {@link CompiledScript} for the {@link ScriptModel}.
     */
    private final CompiledScript compiledScript;

    CompiledEvaluatableScriptAdapter(final ScriptModel scriptModel, final CompiledScript compiledScript) {
        super(scriptModel);

        if (compiledScript == null) {
            throw new IllegalArgumentException("compiledScript must not be null");
        }

        this.compiledScript = compiledScript;
    }

    @Override
    protected ScriptEngine getEngine() {
        return compiledScript.getEngine();
    }

    @Override
    protected Object eval(final Bindings bindings) throws ScriptException {
        return compiledScript.eval(bindings);
    }

    @Override
    public Object eval(ScriptContext context) throws ScriptExecutionException {
        try {
            return compiledScript.eval(context);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }
}
