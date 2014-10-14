package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Kühne on 11.10.2014.
 */
public class TarStreamTypeMatcher implements StreamTypeMatcher {

    @Override
    public int neededHeaderSizeForDetection() {
        return 512;
    }

    @Override
    public boolean matches(byte[] header, String filename) {

        return TarArchiveInputStream.matches(header, header.length);
    }

    @Override
    public ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException {

        return new TarArchiveInputStream(input);
    }
}
