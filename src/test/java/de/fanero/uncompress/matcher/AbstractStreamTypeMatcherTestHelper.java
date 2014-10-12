package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Robert Kühne on 11.10.2014.
 */
public class AbstractStreamTypeMatcherTestHelper {

    protected void addFiles(ZipOutputStream z, String... filenames) throws IOException {

        for (String filename : filenames) {
            z.putNextEntry(new ZipEntry(filename));
        }
    }

    protected Set<String> readEntries(ArchiveInputStream archiveInputStream) throws IOException {

        Set<String> names = new HashSet<>();

        ArchiveEntry entry;
        while ((entry = archiveInputStream.getNextEntry()) != null) {
            names.add(entry.getName());
        }
        return names;
    }
}
