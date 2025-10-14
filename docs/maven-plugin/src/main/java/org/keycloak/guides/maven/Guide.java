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
package org.keycloak.guides.maven;

import java.nio.file.Path;

public class Guide {

    private String template;
    private String id;
    private String title;
    private String summary;
    private int priority = Integer.MAX_VALUE;
    private boolean tileVisible = true;
    private Path root;
    private Path path;

    public static String toId(String path) {
        return path.replace("/", "-").replace("\\", "-").replace(".adoc", "");
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isTileVisible() {
        return tileVisible;
    }

    public void setTileVisible(boolean tileVisible) {
        this.tileVisible = tileVisible;
    }

    public Path getRoot() {
        return root;
    }

    public void setRoot(Path root) {
        this.root = root;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
