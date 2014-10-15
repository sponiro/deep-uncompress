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

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import de.fanero.uncompress.matcher.ZipStreamTypeMatcher;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Robert Kühne
 */
public class DeepArchiveInputStreamTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private InputStream load(String resourceName) {
        return new BufferedInputStream(DeepArchiveInputStreamTest.class.getResourceAsStream(resourceName));
    }

    @Test
    public void testBuilderEmptyInputStream() throws Exception {

        DeepDetectionInputStreamBuilder builder = new DeepDetectionInputStreamBuilder();

        thrown.expect(NullPointerException.class);
        builder.build(null);
    }

    @Test
    public void testBuilderNoRegisteredStreamDetectors() throws IOException {

        DeepDetectionInputStreamBuilder builder = new DeepDetectionInputStreamBuilder();
        builder.build(load("file.zip"));
    }

    @Test
    public void testSimpleZip() throws Exception {

        DeepDetectionInputStreamBuilder builder = new DeepDetectionInputStreamBuilder();
        builder.register(new ZipStreamTypeMatcher());
        ArchiveInputStream inputStream = builder.build(load("file.zip"));

        ArchiveEntry nextEntry = inputStream.getNextEntry();
        assertThat(nextEntry, is(not(nullValue())));
        assertThat(nextEntry.getName(), is("file"));

        nextEntry = inputStream.getNextEntry();
        assertThat(nextEntry, is(nullValue()));
    }

    @Test
    public void testSimpleZip2() throws Exception {

        DeepDetectionInputStreamBuilder builder = new DeepDetectionInputStreamBuilder();
        ArchiveEntry nextEntry;
        try (ArchiveInputStream inputStream = builder.build(load("file.zip"))) {

            nextEntry = inputStream.getNextEntry();
            assertThat(nextEntry, is(not(nullValue())));
            assertThat(nextEntry.getName(), is(""));
        }
    }

    @Test
    public void testTwoFilesZip() throws Exception {

        DeepDetectionInputStreamBuilder builder = new DeepDetectionInputStreamBuilder();
        builder.register(new ZipStreamTypeMatcher());
        try (ArchiveInputStream inputStream = builder.build(load("twofiles.zip"))) {

            Set<String> names = new HashSet<>();

            ArchiveEntry entry;
            while ((entry = inputStream.getNextEntry()) != null) {

                String content = content(inputStream);
                assertThat(content, is(entry.getName() + "\n"));
                names.add(entry.getName());
            }

            assertThat(names.size(), is(2));
        }
    }

    private String content(InputStream in) throws IOException {

        InputStreamReader inputStreamReader = new InputStreamReader(in, Charsets.US_ASCII);
        return CharStreams.toString(inputStreamReader);
    }
}