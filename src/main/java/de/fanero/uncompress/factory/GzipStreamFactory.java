package de.fanero.uncompress.factory;

import de.fanero.uncompress.stream.OneEntryArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Robert Kühne
 */
public class GzipStreamFactory implements ArchiveInputStreamFactory {

    @Override
    public ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException {

        return new OneEntryArchiveInputStream(new GzipCompressorInputStream(input), archiveEntry);
    }
}
