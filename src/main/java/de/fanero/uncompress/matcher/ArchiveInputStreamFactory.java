package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Robert Kühne
 */
public interface ArchiveInputStreamFactory {

    ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException;
}
