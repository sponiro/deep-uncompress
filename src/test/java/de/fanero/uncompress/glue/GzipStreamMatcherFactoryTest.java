package de.fanero.uncompress.glue;

import de.fanero.uncompress.glue.GzipStreamMatcherFactory;
import de.fanero.uncompress.glue.ZipStreamMatcherFactory;
import de.fanero.uncompress.matcher.AbstractStreamTypeMatcherTestHelper;
import de.fanero.uncompress.stream.DeepDetectionInputStreamBuilder;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
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

/**
 * Created by Robert Kühne on 11.10.2014.
 */
public class GzipStreamMatcherFactoryTest extends AbstractStreamTypeMatcherTestHelper {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testEmptyGzip() throws Exception {

        File file = folder.newFile();

        try (GzipCompressorOutputStream g = new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
        }

        assertEntries(file, "");
    }

    @Test
    public void testGzip() throws Exception {

        File file = folder.newFile();

        try (GzipCompressorOutputStream g = new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            g.write(new byte[]{0x01});
        }

        assertEntries(file, "");
    }

    @Test
    public void testZippedGzip() throws Exception {

        File file = folder.newFile();

        try (ZipOutputStream z = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {

            addFiles(z, "gzip.gzip");

            GzipCompressorOutputStream g = new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            g.finish();
        }

        assertEntries(file, "gzip.gzip");
    }

    @Test
    public void testName() throws Exception {

    }

    protected void assertEntries(File file, final String... filenames) throws IOException {

        DeepDetectionInputStreamBuilder builder = new DeepDetectionInputStreamBuilder();
        builder.register(new GzipStreamMatcherFactory());
        builder.register(new ZipStreamMatcherFactory());

        try (ArchiveInputStream inputStream = builder.build(new BufferedInputStream(new FileInputStream(file)))) {

            Set<String> entries = readEntries(inputStream);
            HashSet<String> set = new HashSet<>(Arrays.asList(filenames));
            assertThat(entries, CoreMatchers.<Set<String>>is(set));
        }
    }
}
