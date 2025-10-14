/*
 * Copyright 2009 Red Hat, Inc. and/or its affiliates
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
 * Solaris passwd
 * <p>
 * struct passwd {
 * char    *pw_name;
 * char    *pw_passwd;
 * uid_t   pw_uid;
 * gid_t   pw_gid;
 * char    *pw_age;
 * char    *pw_comment;
 * char    *pw_gecos;
 * char    *pw_dir;
 * char    *pw_shell;
 * };
 *
 * @author Sebastian Sdorra
 */
public class SolarisPasswd extends passwd {
    public String pw_age;

    public String pw_comment;

    public String pw_gecos;

    public String pw_dir;

    public String pw_shell;


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
        fieldOrder.addAll(Arrays.asList("pw_age", "pw_comment", "pw_gecos",
                "pw_dir", "pw_shell"));
        return fieldOrder;
    }

}
