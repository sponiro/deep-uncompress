package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

/**
 * @author Robert Kühne
 */
public class ZipStreamMatcher implements StreamMatcher {

    @Override
    public int neededHeaderSizeForDetection() {

        return 4;
    }

    @Override
    public MatchResult matches(byte[] header, ArchiveEntry archiveEntry) {

        return ZipArchiveInputStream.matches(header, header.length) ? MatchResult.MATCH : MatchResult.NO_MATCH;
    }
}
