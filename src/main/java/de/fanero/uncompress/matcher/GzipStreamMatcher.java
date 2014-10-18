package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * @author Robert Kühne
 */
public class GzipStreamMatcher implements StreamMatcher {

    @Override
    public int neededHeaderSizeForDetection() {

        return 2;
    }

    @Override
    public MatchResult matches(byte[] header, ArchiveEntry archiveEntry) {

        return GzipCompressorInputStream.matches(header, header.length) ? MatchResult.MATCH : MatchResult.NO_MATCH;
    }
}
