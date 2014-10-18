package de.fanero.uncompress.matcher;

import de.fanero.uncompress.factory.ArchiveInputStreamFactory;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Robert Kühne
 */
public class MatcherStreamFactory implements StreamMatcherFactory {

    private final StreamMatcher matcher;
    private final ArchiveInputStreamFactory factory;

    public MatcherStreamFactory(StreamMatcher matcher, ArchiveInputStreamFactory factory) {
        this.matcher = matcher;
        this.factory = factory;
    }

    @Override
    public ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException {

        return factory.createStream(input, archiveEntry);
    }

    @Override
    public int neededHeaderSizeForDetection() {

        return matcher.neededHeaderSizeForDetection();
    }

    @Override
    public MatchResult matches(byte[] header, ArchiveEntry archiveEntry) {

        return matcher.matches(header, archiveEntry);
    }
}
