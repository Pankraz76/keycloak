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
package org.keycloak.testframework.remote.timeoffset;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.keycloak.common.util.Time;

import java.io.IOException;
import java.util.Map;

public class TimeOffSet {
    private int currentOffset;
    private final String KEY_OFFSET = "offset";
    private final String TIME_OFFSET_ENDPOINT = "/testing-timeoffset";
    private final HttpClient httpClient;
    private final String serverUrl;

    public TimeOffSet(HttpClient httpClient, String serverUrl, int initOffset) {
        this.httpClient = httpClient;
        this.serverUrl = serverUrl;
        if (initOffset != 0) {
            set(initOffset);
        }
        currentOffset = initOffset;
    }

    public void set(int offset) throws RuntimeException {
        currentOffset = offset;

        // set for tests
        Time.setOffset(currentOffset);

        // set for KC server
        var time = Map.of(KEY_OFFSET, currentOffset);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(time);

            HttpPut request = new HttpPut(serverUrl + TIME_OFFSET_ENDPOINT);
            request.setEntity(new StringEntity(json));
            request.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != Response.Status.OK.getStatusCode()) {
                var statusLine = response.getStatusLine();
                throw new WebApplicationException(String.format("Unexpected response status for TimeOffSet: %d %s", statusLine.getStatusCode(), statusLine.getReasonPhrase()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int get() {
        return currentOffset;
    }

    public boolean hasChanged() {
        return currentOffset != 0;
    }
}
