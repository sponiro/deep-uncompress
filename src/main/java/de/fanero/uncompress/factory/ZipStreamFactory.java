package de.fanero.uncompress.factory;

import de.fanero.uncompress.matcher.ArchiveInputStreamFactory;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Robert Kühne
 */
public class ZipStreamFactory implements ArchiveInputStreamFactory {

    @Override
    public ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException {

        return new ZipArchiveInputStream(input);
    }
}
