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
package org.keycloak.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CollectionUtilTest {

    @Test
    public void joinInputNoneOutputEmpty() {
        final ArrayList<String> strings = new ArrayList<>();
        final String retval = CollectionUtil.join(strings, ",");
        Assert.assertEquals("", retval);
    }

    @Test
    public void joinInput2SeparatorNull() {
        final ArrayList<String> strings = new ArrayList<>();
        strings.add("foo");
        strings.add("bar");
        final String retval = CollectionUtil.join(strings, null);
        Assert.assertEquals("foonullbar", retval);
    }

    @Test
    public void joinInput1SeparatorNotNull() {
        final ArrayList<String> strings = new ArrayList<>();
        strings.add("foo");
        final String retval = CollectionUtil.join(strings, ",");
        Assert.assertEquals("foo", retval);
    }

  @Test
  public void joinInput2SeparatorNotNull() {
    final ArrayList<String> strings = new ArrayList<>();
    strings.add("foo");
    strings.add("bar");
    final String retval = CollectionUtil.join(strings, ",");
    Assert.assertEquals("foo,bar", retval);
  }

  @Test
  public void testEmptyCollection() {
    List<String> list = new ArrayList<>();

    assertThat(CollectionUtil.isEmpty(list), is(true));
    assertThat(CollectionUtil.isNotEmpty(list), is(false));

    list.add("something");

    assertThat(CollectionUtil.isEmpty(list), is(false));
    assertThat(CollectionUtil.isNotEmpty(list), is(true));

    Set<Object> set = new HashSet<>();

    assertThat(CollectionUtil.isEmpty(set), is(true));
    assertThat(CollectionUtil.isNotEmpty(set), is(false));

    set.add("something");

    assertThat(CollectionUtil.isEmpty(set), is(false));
    assertThat(CollectionUtil.isNotEmpty(set), is(true));
  }

    @Test
    public void equalsCollectionTest() {
        Assert.assertFalse(CollectionUtil.collectionEquals(Arrays.asList(1, 3, 2), Arrays.asList(1, 3)));
        Assert.assertFalse(CollectionUtil.collectionEquals(Arrays.asList("A", "C"), Arrays.asList("A", "C", "B")));
        Assert.assertFalse(CollectionUtil.collectionEquals(Arrays.asList(1, 3, 2, 3), Arrays.asList(1, 2, 3, 2)));
        Assert.assertTrue(CollectionUtil.collectionEquals(Arrays.asList(1, 3, 3), Arrays.asList(3, 1, 3)));
    }
}
