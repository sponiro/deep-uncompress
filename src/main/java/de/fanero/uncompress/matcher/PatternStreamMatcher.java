package de.fanero.uncompress.matcher;

import de.fanero.uncompress.factory.ArchiveInputStreamFactory;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Robert Kühne
 */
public class PatternStreamMatcher implements StreamMatcherFactory {

    private final ArchiveInputStreamFactory archiveInputStreamFactory;
    private List<Pattern> patterns;

    public PatternStreamMatcher(ArchiveInputStreamFactory archiveInputStreamFactory) {
        this.archiveInputStreamFactory = archiveInputStreamFactory;
        this.patterns = new ArrayList<>();
    }

    @Override
    public int neededHeaderSizeForDetection() {

        return 0;
    }

    @Override
    public MatchResult matches(byte[] header, ArchiveEntry archiveEntry) {

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(archiveEntry.getName());
            if (matcher.matches()) {
                return MatchResult.MATCH;
            }
        }

        return MatchResult.NO_MATCH;
    }

    @Override
    public ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException {

        return archiveInputStreamFactory.createStream(input, archiveEntry);
    }

    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
    }
}
