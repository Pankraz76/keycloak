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
package org.keycloak.testsuite.cli;

import org.keycloak.common.crypto.FipsMode;
import org.keycloak.testsuite.arquillian.AuthServerTestEnricher;
import org.keycloak.testsuite.cli.exec.AbstractExec;
import org.keycloak.testsuite.cli.exec.AbstractExecBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="mailto:mstrukel@redhat.com">Marko Strukelj</a>
 */
public class KcRegExec extends AbstractExec {

    public static final String WORK_DIR = System.getProperty("user.dir") + "/target/containers/keycloak-client-tools";

    public static final String CMD = OS_ARCH.isWindows() ? "kcreg.bat" : "kcreg.sh";

    private KcRegExec(String workDir, String argsLine, InputStream stdin) {
        this(workDir, argsLine, null, stdin);
    }

    private KcRegExec(String workDir, String argsLine, String env, InputStream stdin) {
        super(workDir, argsLine, env, stdin);
    }

    @Override
    public String getCmd() {
        return "bin/" + CMD;
    }

    public static KcRegExec.Builder newBuilder() {
        return (KcRegExec.Builder) new KcRegExec.Builder().workDir(WORK_DIR);
    }

    public static KcRegExec execute(String args) {
        return newBuilder()
                .argsLine(args)
                .execute();
    }

    @Override
    public List<String> stderrLines() {
        List<String> lines = super.stderrLines();
        // remove the two lines with the BC provider info if FIPS
        return AuthServerTestEnricher.AUTH_SERVER_FIPS_MODE == FipsMode.DISABLED || lines.size() < 2
            ? lines
            : lines.subList(2, lines.size());
    }

    public static class Builder extends AbstractExecBuilder<KcRegExec> {

        @Override
        public KcRegExec execute() {
            KcRegExec exe = new KcRegExec(workDir, argsLine, env, stdin);
            exe.dumpStreams = dumpStreams;
            exe.execute();
            return exe;
        }

        @Override
        public KcRegExec executeAsync() {
            KcRegExec exe = new KcRegExec(workDir, argsLine, env, stdin);
            exe.dumpStreams = dumpStreams;
            exe.executeAsync();
            return exe;
        }
    }

}