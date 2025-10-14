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
package org.freedesktop.dbus.utils;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Sample ErrorHandlers for XML Parsing.
 *
 * @author hypfvieh
 */
public class XmlErrorHandlers {

    /**
     * XML Error Handler which will silently ignore all thrown Exceptions.
     *
     * @author hypfvieh
     * @since v1.0.3 - 2018-01-10
     */
    public static class XmlErrorHandlerQuiet implements ErrorHandler {

        @Override
        public void warning(SAXParseException _exception) throws SAXException {
        }

        @Override
        public void error(SAXParseException _exception) throws SAXException {
        }

        @Override
        public void fatalError(SAXParseException _exception) throws SAXException {
        }

    }

    /**
     * XML Error Handler which will throw RuntimeException if any Exception was thrown.
     *
     * @author hypfvieh
     * @since v1.0.3 - 2018-01-10
     */
    public static class XmlErrorHandlerRuntimeException implements ErrorHandler {

        @Override
        public void warning(SAXParseException _exception) throws SAXException {
            throw new RuntimeException(_exception);
        }

        @Override
        public void error(SAXParseException _exception) throws SAXException {
            throw new RuntimeException(_exception);
        }

        @Override
        public void fatalError(SAXParseException _exception) throws SAXException {
            throw new RuntimeException(_exception);
        }

    }
}
