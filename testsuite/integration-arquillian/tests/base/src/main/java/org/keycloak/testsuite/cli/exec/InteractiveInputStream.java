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
import java.util.LinkedList;

class InteractiveInputStream extends InputStream {

    private LinkedList<Byte> queue = new LinkedList<>();

    private Thread consumer;

    private boolean closed;

    @Override
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public synchronized int read(byte[] b, int off, int len) throws IOException {

        Byte current = null;
        int rc = 0;
        try {
            consumer = Thread.currentThread();

            do {
                current = queue.poll();
                if (current == null) {
                    if (rc > 0) {
                        return rc;
                    } else {
                        do {
                            if (closed) {
                                return -1;
                            }
                            wait();
                        }
                        while ((current = queue.poll()) == null);
                    }
                }

                b[off + rc] = current;
                rc++;
            } while (rc < len);

        } catch (InterruptedException e) {
            throw new InterruptedIOException("Signalled to exit");
        } finally {
            consumer = null;
        }
        return rc;
    }

    @Override
    public long skip(long n) throws IOException {
        return super.skip(n);
    }

    @Override
    public int available() throws IOException {
        return super.available();
    }

    @Override
    public synchronized void mark(int readlimit) {
        super.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        super.reset();
    }

    @Override
    public boolean markSupported() {
        return super.markSupported();
    }

    @Override
    public synchronized int read() throws IOException {
        // when input is available pass it on
        Byte current;
        try {
            consumer = Thread.currentThread();

            while ((current = queue.poll()) == null) {
                // we don't check for closed before making sure
                // that there is nothing more to read
                if (closed) {
                    return -1;
                }
                wait();
            }

        } catch (InterruptedException e) {
            throw new InterruptedIOException("Signalled to exit");
        } finally {
            consumer = null;
        }
        return current;
    }

    @Override
    public synchronized void close() {
        closed = true;
        if (consumer != null) {
            consumer.interrupt();
        }
    }

    public synchronized void pushBytes(byte [] buff) {
        for (byte b : buff) {
            queue.add(b);
        }
        notify();
    }
}