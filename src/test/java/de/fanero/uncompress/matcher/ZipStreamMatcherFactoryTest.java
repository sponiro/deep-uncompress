package de.fanero.uncompress.matcher;

import de.fanero.uncompress.stream.DeepDetectionInputStreamBuilder;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertThat;

public class ZipStreamMatcherFactoryTest extends AbstractStreamTypeMatcherTestHelper {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testEmptyZip() throws Exception {

        File file = folder.newFile();

        try (ZipOutputStream z = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
        }

        assertEntries(file);
    }

    @Test
    public void testZip() throws Exception {

        File file = folder.newFile();

        try (ZipOutputStream z = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            addFiles(z, "flash", "blueblur");
        }

        assertEntries(file, "flash", "blueblur");
    }

    @Test
    public void testDeep1EmptyZip() throws Exception {

        File file = folder.newFile();

        try (ZipOutputStream z = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {

            addFiles(z, "w.zip");
            ZipOutputStream w = new ZipOutputStream(z);
            w.finish();
        }

        assertEntries(file);
    }

    @Test
    public void testDeep1OneFileZip() throws Exception {

        File file = folder.newFile();

        try (ZipOutputStream z = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {

            addFiles(z, "w.zip");
            ZipOutputStream w = new ZipOutputStream(z);
            addFiles(w, "a");
            w.finish();
        }

        assertEntries(file, "a");
    }

    @Test
    public void testDeep1Zip() throws Exception {

        File file = folder.newFile();

        try (ZipOutputStream z = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {

            addFiles(z, "a", "b");

            addFiles(z, "w.zip");
            ZipOutputStream w = new ZipOutputStream(z);
            addFiles(w, "c", "d");
            w.finish();
        }

        assertEntries(file, "a", "b", "c", "d");
    }

    protected void assertEntries(File file, final String... filenames) throws IOException {

        DeepDetectionInputStreamBuilder builder = new DeepDetectionInputStreamBuilder();
        builder.register(new ZipStreamMatcherFactory());

        try (ArchiveInputStream inputStream = builder.build(new BufferedInputStream(new FileInputStream(file)))) {

            Set<String> entries = readEntries(inputStream);
            HashSet<String> set = new HashSet<>(Arrays.asList(filenames));
            assertThat(entries, CoreMatchers.<Set<String>>is(set));
        }
    }
}