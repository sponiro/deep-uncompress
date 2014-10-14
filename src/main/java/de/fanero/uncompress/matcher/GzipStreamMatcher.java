package de.fanero.uncompress.matcher;

import de.fanero.uncompress.OneEntryArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Robert Kühne
 */
public class GzipStreamMatcher implements StreamTypeMatcher {

    @Override
    public int neededHeaderSizeForDetection() {

        return 2;
    }

    @Override
    public boolean matches(byte[] header, String filename) {

        return GzipCompressorInputStream.matches(header, header.length);
    }

    @Override
    public ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException {

        return new OneEntryArchiveInputStream(new GzipCompressorInputStream(input), archiveEntry);
    }
}
