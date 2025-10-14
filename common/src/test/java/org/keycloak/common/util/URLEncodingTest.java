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
package org.keycloak.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class URLEncodingTest {

    @Test
    public void testUrlEncoding() {
        Assert.assertEquals("some", Encode.urlEncode("some"));
        Assert.assertEquals("330cbb52-c3eb-4c4a-9f23-77a8094cd969", Encode.urlEncode("330cbb52-c3eb-4c4a-9f23-77a8094cd969"));
        Assert.assertEquals("sp%C3%A9cial.char", Encode.urlEncode("spécial.char"));
        Assert.assertEquals("sp%C3%A9cial.ch%2Far.%C5%BE%C3%BD%C3%A1%C3%A1", Encode.urlEncode("spécial.ch/ar.žýáá"));
    }

    @Test
    public void testUrlDecoding() {
        Assert.assertEquals("some", Encode.urlDecode("some"));
        Assert.assertEquals("330cbb52-c3eb-4c4a-9f23-77a8094cd969", Encode.urlDecode("330cbb52-c3eb-4c4a-9f23-77a8094cd969"));
        Assert.assertEquals("spécial.char", Encode.urlDecode("sp%C3%A9cial.char"));
        Assert.assertEquals("spécial.ch/ar.žýáá", Encode.urlDecode("sp%C3%A9cial.ch%2Far.%C5%BE%C3%BD%C3%A1%C3%A1"));
    }
}
