package de.fanero.uncompress.matcher;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import de.fanero.uncompress.stream.DeepDetectionInputStreamBuilder;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThat;

public class TarStreamMatcherFactoryTest extends AbstractStreamTypeMatcherTestHelper {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testOneFileTar() throws Exception {

        File file = folder.newFile();

        try (TarArchiveOutputStream t = new TarArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            addFiles(t, "a");
        }

        assertEntries(file, "a");
    }

    @Test
    public void testTwoFileTar() throws Exception {

        File file = folder.newFile();

        try (TarArchiveOutputStream t = new TarArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            addFiles(t, "a", "b");
        }

        assertEntries(file, "a", "b");
    }

    @Test
    public void testZipInTar() throws Exception {

        File file = folder.newFile();

        try (TarArchiveOutputStream t = new TarArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            addFiles(t, "a", "b");

            File tempFile = createZipFile("c", "d");
            TarArchiveEntry archiveEntry = new TarArchiveEntry("w.zip");
            archiveEntry.setSize(tempFile.length());
            t.putArchiveEntry(archiveEntry);

            ByteSource byteSource = Files.asByteSource(tempFile);
            byteSource.copyTo(t);
            t.closeArchiveEntry();
        }

        assertEntries(file, "a", "b", "c", "d");
    }

    private void addFiles(TarArchiveOutputStream t, String... filenames) throws IOException {

        for (String filename : filenames) {

            TarArchiveEntry entry = new TarArchiveEntry(filename);
            entry.setSize(1);
            t.putArchiveEntry(entry);
            t.write(new byte[]{0x01});
            t.closeArchiveEntry();
        }
    }

    private File createZipFile(String... filenames) throws IOException {

        File file = folder.newFile();

        try (ZipArchiveOutputStream z = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {

            for (String filename : filenames) {
                z.putArchiveEntry(new ZipArchiveEntry(filename));
                z.closeArchiveEntry();
            }
        }

        return file;
    }

    protected void assertEntries(File file, final String... filenames) throws IOException {

        DeepDetectionInputStreamBuilder builder = new DeepDetectionInputStreamBuilder();
        builder.register(new TarStreamMatcherFactory());
        builder.register(new ZipStreamMatcherFactory());

        try (ArchiveInputStream inputStream = builder.build(new BufferedInputStream(new FileInputStream(file)))) {

            Set<String> entries = readEntries(inputStream);
            HashSet<String> set = new HashSet<>(Arrays.asList(filenames));
            assertThat(entries, CoreMatchers.<Set<String>>is(set));
        }
    }

}