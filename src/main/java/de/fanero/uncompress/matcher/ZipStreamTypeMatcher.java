package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Kühne on 03.10.2014.
 */
public class ZipStreamTypeMatcher implements StreamTypeMatcher {

    @Override
    public int neededHeaderSizeForDetection() {

        return 4;
    }

    @Override
    public boolean matches(byte[] header, String filename) {

        return ZipArchiveInputStream.matches(header, header.length);
    }

    @Override
    public ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException {

        return new ZipArchiveInputStream(input);
    }
}
