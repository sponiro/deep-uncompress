/*
* Copyright (C) 2014 Robert Kühne
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package de.fanero.uncompress.stream;

import de.fanero.uncompress.stream.EmtpyArchiveEntry;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Robert Kühne
 */
public class EmtpyArchiveEntryTest {

    @Test
    public void testGetName() throws Exception {

        EmtpyArchiveEntry entry = EmtpyArchiveEntry.getInstance();

        assertThat(entry.getName(), is(""));
    }

    @Test
    public void testGetSize() throws Exception {

        EmtpyArchiveEntry entry = EmtpyArchiveEntry.getInstance();

        assertThat(entry.getSize(), is(0L));
    }

    @Test
    public void testIsDirectory() throws Exception {

        EmtpyArchiveEntry entry = EmtpyArchiveEntry.getInstance();

        assertThat(entry.isDirectory(), is(false));
    }

    @Test
    public void testGetLastModifiedDate() throws Exception {

        EmtpyArchiveEntry entry = EmtpyArchiveEntry.getInstance();

        assertThat(entry.getLastModifiedDate(), is(nullValue()));
    }
}