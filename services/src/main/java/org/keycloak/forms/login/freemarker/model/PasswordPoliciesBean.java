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
package org.keycloak.forms.login.freemarker.model;

import org.keycloak.models.PasswordPolicy;

public class PasswordPoliciesBean {
  private final Integer length;
  private final Integer maxLength;
  private final Integer lowerCase;
  private final Integer upperCase;
  private final Integer specialChars;
  private final Integer digits;
  private final Integer passwordHistory;
  private final Integer forceExpiredPasswordChange;
  private final boolean notUsername;
  private final boolean notEmail;

  public PasswordPoliciesBean(PasswordPolicy policy) {
    this.length = policy.getPolicyConfig("length");
    this.maxLength = policy.getPolicyConfig("maxLength");
    this.lowerCase = policy.getPolicyConfig("lowerCase");
    this.upperCase = policy.getPolicyConfig("upperCase");
    this.specialChars = policy.getPolicyConfig("specialChars");
    this.digits = policy.getPolicyConfig("digits");
    this.passwordHistory = policy.getPolicyConfig("passwordHistory");
    this.forceExpiredPasswordChange = policy.getPolicyConfig("forceExpiredPasswordChange");
    this.notUsername = policy.getPolicies().contains("notUsername");
    this.notEmail = policy.getPolicies().contains("notEmail");
  }

  public Integer getLength() {
    return length;
  }

  public Integer getMaxLength() {
    return maxLength;
  }

  public Integer getLowerCase() {
    return lowerCase;
  }

  public Integer getUpperCase() {
    return upperCase;
  }

  public Integer getSpecialChars() {
    return specialChars;
  }

  public Integer getDigits() {
    return digits;
  }

  public Integer getPasswordHistory() {
    return passwordHistory;
  }

  public Integer getForceExpiredPasswordChange() {
    return forceExpiredPasswordChange;
  }

  public boolean isNotUsername() {
    return notUsername;
  }

  public boolean isNotEmail() {
    return notEmail;
  }
}
