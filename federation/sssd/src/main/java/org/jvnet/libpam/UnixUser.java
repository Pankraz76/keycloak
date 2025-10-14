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
package org.jvnet.libpam;

import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;
import org.jvnet.libpam.impl.CLibrary.group;
import org.jvnet.libpam.impl.CLibrary.passwd;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.jvnet.libpam.impl.CLibrary.libc;

/**
 * Represents an Unix user. Immutable.
 *
 * @author Kohsuke Kawaguchi
 */
public class UnixUser {
    private final String userName, gecos, dir, shell;
    private final int uid, gid;
    private final Set<String> groups;

    /*package*/ UnixUser(String userName, passwd pwd) throws PAMException {
        this.userName = userName;
        this.gecos = pwd.getPwGecos();
        this.dir = pwd.getPwDir();
        this.shell = pwd.getPwShell();
        this.uid = pwd.getPwUid();
        this.gid = pwd.getPwGid();

        int sz = 4; /*sizeof(gid_t)*/

        int ngroups = 64;
        Memory m = new Memory(ngroups * sz);
        IntByReference pngroups = new IntByReference(ngroups);
        try {
            if (libc.getgrouplist(userName, pwd.getPwGid(), m, pngroups) < 0) {
                // allocate a bigger memory
                m = new Memory(pngroups.getValue() * sz);
                if (libc.getgrouplist(userName, pwd.getPwGid(), m, pngroups) < 0)
                    // shouldn't happen, but just in case.
                    throw new PAMException("getgrouplist failed");
            }
            ngroups = pngroups.getValue();
        } catch (LinkageError e) {
            // some platform, notably Solaris, doesn't have the getgrouplist function
            ngroups = libc._getgroupsbymember(userName, m, ngroups, 0);
            if (ngroups < 0)
                throw new PAMException("_getgroupsbymember failed");
        }

        groups = new HashSet<String>();
        for (int i = 0; i < ngroups; i++) {
            int gid = m.getInt(i * sz);
            group grp = libc.getgrgid(gid);
            if (grp == null) {
                continue;
            }
            groups.add(grp.gr_name);
        }
    }

    public UnixUser(String userName) throws PAMException {
        this(userName, passwd.loadPasswd(userName));
    }

    /**
     * Copy constructor for mocking. Not intended for regular use. Only for testing.
     * This signature may change in the future.
     */
    protected UnixUser(String userName, String gecos, String dir, String shell, int uid, int gid, Set<String> groups) {
        this.userName = userName;
        this.gecos = gecos;
        this.dir = dir;
        this.shell = shell;
        this.uid = uid;
        this.gid = gid;
        this.groups = groups;
    }

    /**
     * Gets the unix account name. Never null.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the UID of this user.
     */
    public int getUID() {
        return uid;
    }

    /**
     * Gets the GID of this user.
     */
    public int getGID() {
        return gid;
    }

    /**
     * Gets the gecos (the real name) of this user.
     */
    public String getGecos() {
        return gecos;
    }

    /**
     * Gets the home directory of this user.
     */
    public String getDir() {
        return dir;
    }

    /**
     * Gets the shell of this user.
     */
    public String getShell() {
        return shell;
    }

    /**
     * Gets the groups that this user belongs to.
     *
     * @return never null.
     */
    public Set<String> getGroups() {
        return Collections.unmodifiableSet(groups);
    }

    public static boolean exists(String name) {
        return libc.getpwnam(name) != null;
    }
}
