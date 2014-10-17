package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

/**
 * @author Robert Kühne
 */
public class TarStreamMatcher implements StreamMatcher {

    @Override
    public int neededHeaderSizeForDetection() {
        return 512;
    }

    @Override
    public MatchResult matches(byte[] header, ArchiveEntry archiveEntry) {

        return TarArchiveInputStream.matches(header, header.length) ? MatchResult.MATCH : MatchResult.NO_MATCH;
    }
}
