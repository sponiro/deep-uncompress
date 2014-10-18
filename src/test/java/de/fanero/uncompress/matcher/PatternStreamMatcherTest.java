package de.fanero.uncompress.matcher;

import de.fanero.uncompress.factory.ArchiveInputStreamFactory;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Robert Kühne
 */
public class PatternStreamMatcherTest {

    @Test
    public void testPositiveMatch() throws Exception {

        ArchiveInputStreamFactory factory = Mockito.mock(ArchiveInputStreamFactory.class);
        PatternStreamMatcher matcher = new PatternStreamMatcher(factory);
        Pattern pattern = Pattern.compile(".*\\.tar");

        matcher.addPattern(pattern);

        assertThat(matcher.matches(new byte[]{}, new TestArchiveEntry("myfile.tar")), is(StreamMatcher.MatchResult.MATCH));
    }

    @Test
    public void testNoMatch() throws Exception {

        ArchiveInputStreamFactory factory = Mockito.mock(ArchiveInputStreamFactory.class);
        PatternStreamMatcher matcher = new PatternStreamMatcher(factory);
        Pattern pattern = Pattern.compile(".*\\.tar");

        matcher.addPattern(pattern);

        assertThat(matcher.matches(new byte[]{}, new TestArchiveEntry("myfile.zip")), is(StreamMatcher.MatchResult.NO_MATCH));
    }

    private static class TestArchiveEntry implements ArchiveEntry {

        private final String name;

        private TestArchiveEntry(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public boolean isDirectory() {
            return false;
        }

        @Override
        public Date getLastModifiedDate() {
            return null;
        }
    }
}