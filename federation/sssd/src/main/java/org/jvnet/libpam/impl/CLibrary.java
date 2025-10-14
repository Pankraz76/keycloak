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

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import org.jvnet.libpam.PAMException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Kohsuke Kawaguchi
 */
public interface CLibrary extends Library {
    /**
     * Comparing http://linux.die.net/man/3/getpwnam
     * and my Mac OS X reveals that the structure of this field isn't very portable.
     * In particular, we cannot read the real name reliably.
     */
    public class passwd extends Structure {
        /**
         * User name.
         */
        public String pw_name;
        /**
         * Encrypted password.
         */
        public String pw_passwd;
        public int pw_uid;
        public int pw_gid;

        // ... there are a lot more fields

        public static passwd loadPasswd(String userName) throws PAMException {
            passwd pwd = libc.getpwnam(userName);
            if (pwd == null) {
                throw new PAMException("No user information is available");
            }
            return pwd;
        }

        public String getPwName() {
            return pw_name;
        }

        public String getPwPasswd() {
            return pw_passwd;
        }

        public int getPwUid() {
            return pw_uid;
        }

        public int getPwGid() {
            return pw_gid;
        }

        public String getPwGecos() {
            return null;
        }

        public String getPwDir() {
            return null;
        }

        public String getPwShell() {
            return null;
        }

        protected List getFieldOrder() {
            return Arrays.asList("pw_name", "pw_passwd", "pw_uid", "pw_gid");
        }
    }

    public class group extends Structure {
        public String gr_name;
        // ... the rest of the field is not interesting for us

        protected List getFieldOrder() {
            return Arrays.asList("gr_name");
        }
    }

    Pointer calloc(int count, int size);

    Pointer strdup(String s);

    passwd getpwnam(String username);

    /**
     * Lists up group IDs of the given user. On Linux and most BSDs, but not on Solaris.
     * See http://www.gnu.org/software/hello/manual/gnulib/getgrouplist.html
     */
    int getgrouplist(String user, int/*gid_t*/ group, Memory groups, IntByReference ngroups);

    /**
     * getgrouplist equivalent on Solaris.
     * See http://mail.opensolaris.org/pipermail/sparks-discuss/2008-September/000528.html
     */
    int _getgroupsbymember(String user, Memory groups, int maxgids, int numgids);

    group getgrgid(int/*gid_t*/ gid);

    group getgrnam(String name);

    // other user/group related functions that are likely useful
    // see http://www.gnu.org/software/libc/manual/html_node/Users-and-Groups.html#Users-and-Groups


    public static final CLibrary libc = Instance.init();

    static class Instance {
        private static CLibrary init() {
            if (Platform.isMac() || Platform.isOpenBSD()) {
                return (CLibrary) Native.loadLibrary("c", BSDCLibrary.class);
            } else if (Platform.isFreeBSD()) {
                return (CLibrary) Native.loadLibrary("c", FreeBSDCLibrary.class);
            } else if (Platform.isSolaris()) {
                return (CLibrary) Native.loadLibrary("c", SolarisCLibrary.class);
            } else if (Platform.isLinux()) {
                return (CLibrary) Native.loadLibrary("c", LinuxCLibrary.class);
            } else {
                return (CLibrary) Native.loadLibrary("c", CLibrary.class);
            }
        }
    }
}
