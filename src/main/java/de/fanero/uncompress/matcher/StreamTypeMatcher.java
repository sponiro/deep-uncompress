package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Robert on 30.09.2014.
 */
public interface StreamTypeMatcher {

    int neededHeaderSizeForDetection();

    boolean matches(byte[] header, String filename);

    ArchiveInputStream createStream(List<StreamTypeMatcher> detectors, InputStream input, ArchiveEntry archiveEntry) throws IOException;
}
