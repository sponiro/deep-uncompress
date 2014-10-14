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
package de.fanero.uncompress;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Robert Kühne
 */
public class OneEntryArchiveInputStreamTest {

    @Test
    public void testGetNextEntryFirstCall() throws Exception {

        ArchiveEntry archiveEntry = EmtpyArchiveEntry.getInstance();
        OneEntryArchiveInputStream stream = new OneEntryArchiveInputStream(new ByteArrayInputStream(new byte[3]), archiveEntry);

        assertThat(stream.getNextEntry(), is(archiveEntry));
    }

    @Test
    public void testGetNextEntrySecondCall() throws Exception {

        ArchiveEntry archiveEntry = EmtpyArchiveEntry.getInstance();
        OneEntryArchiveInputStream stream = new OneEntryArchiveInputStream(new ByteArrayInputStream(new byte[3]), archiveEntry);

        assertThat(stream.getNextEntry(), is(archiveEntry));
        assertThat(stream.getNextEntry(), is(nullValue()));
    }

    @Test
    public void testCloseOfInputStream() throws Exception {

        ArchiveEntry archiveEntry = EmtpyArchiveEntry.getInstance();
        InputStream inputStream = Mockito.mock(InputStream.class);

        OneEntryArchiveInputStream stream = new OneEntryArchiveInputStream(inputStream, archiveEntry);
        stream.getNextEntry();

        verify(inputStream, times(1)).close();
    }

    @Test
    public void testReadDelegation() throws Exception {

        ArchiveEntry archiveEntry = EmtpyArchiveEntry.getInstance();
        InputStream inputStream = Mockito.mock(InputStream.class);

        OneEntryArchiveInputStream stream = new OneEntryArchiveInputStream(inputStream, archiveEntry);
        stream.read();
        verify(inputStream, times(1)).read();
    }
}