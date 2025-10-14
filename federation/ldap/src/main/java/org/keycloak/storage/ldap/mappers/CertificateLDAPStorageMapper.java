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
package org.keycloak.storage.ldap.mappers;

import org.keycloak.common.util.PemUtils;
import org.keycloak.component.ComponentModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.idm.query.Condition;
import org.keycloak.storage.ldap.idm.query.internal.EqualCondition;
import org.keycloak.storage.ldap.idm.query.internal.LDAPQuery;

public class CertificateLDAPStorageMapper extends UserAttributeLDAPStorageMapper {

  public static final String IS_DER_FORMATTED = "is.der.formatted";

  public CertificateLDAPStorageMapper(ComponentModel mapperModel, LDAPStorageProvider ldapProvider) {
    super(mapperModel, ldapProvider);
  }

  @Override
  public void beforeLDAPQuery(LDAPQuery query) {
    super.beforeLDAPQuery(query);

    String ldapAttrName = getLdapAttributeName();

    if (isDerFormatted()) {
      for (Condition condition : query.getConditions()) {
        if (condition instanceof EqualCondition &&
            condition.getParameterName().equalsIgnoreCase(ldapAttrName)) {
          EqualCondition equalCondition = ((EqualCondition) condition);
          equalCondition.setValue(PemUtils.pemToDer(equalCondition.getValue().toString()));
        }
      }
    }
  }

  private boolean isDerFormatted() {
    return mapperModel.get(IS_DER_FORMATTED, false);
  }
}

