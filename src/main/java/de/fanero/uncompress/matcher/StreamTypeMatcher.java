package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Robert Kühne
 */
public interface StreamTypeMatcher {

    int neededHeaderSizeForDetection();

    boolean matches(byte[] header, String filename);

    ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException;
}
