package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Robert Kühne
 */
public interface StreamDetector {

    ArchiveInputStream detectAndCreateInputStream(InputStream in, ArchiveEntry archiveEntry) throws IOException;
}
