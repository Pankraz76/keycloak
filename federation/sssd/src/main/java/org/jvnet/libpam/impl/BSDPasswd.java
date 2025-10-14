/*
 * Copyright 2011 Red Hat, Inc. and/or its affiliates
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
package org.jvnet.libpam.impl;

import org.jvnet.libpam.impl.CLibrary.passwd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FreeeBSD, OpenBSD and MacOS passwd
 * <p>
 * struct passwd {
 * char    *pw_name;
 * char    *pw_passwd;
 * uid_t   pw_uid;
 * gid_t   pw_gid;
 * time_t pw_change;
 * char    *pw_class;
 * char    *pw_gecos;
 * char    *pw_dir;
 * char    *pw_shell;
 * time_t pw_expire;
 * };
 *
 * @author Sebastian Sdorra
 */
public class BSDPasswd extends passwd {
    /* password change time */
    public long pw_change;

    /* user access class */
    public String pw_class;

    /* Honeywell login info */
    public String pw_gecos;

    /* home directory */
    public String pw_dir;

    /* default shell */
    public String pw_shell;

    /* account expiration */
    public long pw_expire;

    @Override
    public String getPwGecos() {
        return pw_gecos;
    }

    @Override
    public String getPwDir() {
        return pw_dir;
    }

    @Override
    public String getPwShell() {
        return pw_shell;
    }

    @Override
    protected List getFieldOrder() {
        List fieldOrder = new ArrayList(super.getFieldOrder());
        fieldOrder.addAll(Arrays.asList("pw_change", "pw_class", "pw_gecos",
                "pw_dir", "pw_shell", "pw_expire"));
        return fieldOrder;
    }

}
