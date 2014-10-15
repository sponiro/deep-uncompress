/*
* Copyright (C) 2014 Robert Kühne
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Robert Kühne
 */
public interface StreamTypeMatcher {

    public static enum MatchResult {
        /**
         * A match has been found - stop processing
         */
        MATCH,
        /**
         * No match has been found - continue processing
         */
        NO_MATCH,
        /**
         * No match has been found - stop processing
         */
        STOP_MATCHING
    }

    int neededHeaderSizeForDetection();

    MatchResult matches(byte[] header, ArchiveEntry archiveEntry);

    ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException;
}
