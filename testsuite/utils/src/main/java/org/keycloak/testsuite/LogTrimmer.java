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
package org.keycloak.testsuite;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple utility class for trimming test output (if successful).
 *
 * Created to shrink down the output for Travis.
 *
 * Created by st on 03/07/17.
 */
public class LogTrimmer {

    private static Pattern TEST_START_PATTERN = Pattern.compile("(\\[INFO\\] )?Running (.*)");
    private static int TEST_NAME_GROUP = 2;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String testRunning = null;
            String line = null;
            Matcher testMatcher = null;
            StringBuilder testText = new StringBuilder();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (testRunning == null) {
                    testMatcher = TEST_START_PATTERN.matcher(line);
                    if (testMatcher.find()) {
                        testRunning = testMatcher.group(TEST_NAME_GROUP);
                        System.out.println(line);
                    } else {
                        System.out.println("-- " + line);
                    }
                } else {
                    if (line.contains("Tests run:")) {
                        if (!(line.contains("Failures: 0") && line.contains("Errors: 0"))) {
                            System.out.println("--------- " + testRunning + " output start ---------");
                            System.out.println(testText.toString());
                            System.out.println("--------- " + testRunning + " output end  ---------");
                        }
                        System.out.println(line);
                        testRunning = null;
                        testText = new StringBuilder();
                    } else {
                        testText.append(testRunning.substring(testRunning.lastIndexOf('.') + 1) + " ++ " + line);
                        testText.append("\n");
                    }
                }
            }
        }
    }
}
