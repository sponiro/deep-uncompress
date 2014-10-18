package de.fanero.uncompress.glue;

import de.fanero.uncompress.factory.ArchiveInputStreamFactory;
import de.fanero.uncompress.matcher.StreamMatcher;

/**
 * @author Robert Kühne
 */
public interface StreamMatcherFactory extends StreamMatcher, ArchiveInputStreamFactory {
}
