package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Robert Kühne
 */
public class PatternStreamMatcher implements StreamMatcher {

    private List<Pattern> patterns;

    public PatternStreamMatcher() {
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

    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
    }
}
