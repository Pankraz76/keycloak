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
package org.keycloak.testsuite.util.oauth;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.keycloak.representations.UserInfo;

import java.io.IOException;

public class UserInfoResponse extends AbstractHttpResponse {

    private UserInfo userInfo;

    public UserInfoResponse(CloseableHttpResponse response) throws IOException {
        super(response);
    }

    @Override
    protected void parseContent() throws IOException {
        userInfo = asJson(UserInfo.class);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

}
