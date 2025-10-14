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
package org.keycloak.testsuite.cli.exec;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;

import static org.keycloak.testsuite.cli.exec.AbstractExec.copyStream;

class StreamReaderThread extends Thread {

    private InputStream is;
    private OutputStream os;

    StreamReaderThread(InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
    }

    public void run() {
        try {
            copyStream(is, os);
        } catch (InterruptedIOException ignored) {
            // Ignore, this is when the stream is terminated via signal upon exit
        } catch (IOException e) {
            throw new RuntimeException("Unexpected I/O error", e);
        } finally {
            try {
                os.close();
            } catch (IOException ignored) {
                System.err.print("IGNORED: error while closing output stream: ");
                ignored.printStackTrace();
            }
        }
    }
}