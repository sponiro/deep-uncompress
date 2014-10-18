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

import com.google.common.io.ByteStreams;
import de.fanero.uncompress.stream.EmtpyArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Robert Kühne
 */
public class StreamDetectorImpl implements StreamDetector {

    private final List<StreamMatcherFactory> detectors;
    private final int headerSize;

    public StreamDetectorImpl(List<StreamMatcherFactory> detectors) {
        this.detectors = detectors;
        this.headerSize = findMaxHeaderSize(0, detectors);
    }

    @Override
    public ArchiveInputStream detectAndCreateInputStream(InputStream in, ArchiveEntry archiveEntry) throws IOException {

        if (!in.markSupported()) {
            throw new IllegalArgumentException("Mark is not supported.");
        }

        byte[] header = readHeader(in, headerSize);
        archiveEntry = nullToEmpty(archiveEntry);

        for (StreamMatcherFactory detector : detectors) {

            StreamMatcher.MatchResult matchResult = detector.matches(header, archiveEntry);

            switch (matchResult) {
                case MATCH:
                    return detector.createStream(in, archiveEntry);
                case NO_MATCH:
                    break;
                case STOP_MATCHING:
                    return null;
            }
        }
        return null;
    }

    private ArchiveEntry nullToEmpty(ArchiveEntry entry) {
        if (entry == null) {
            return EmtpyArchiveEntry.getInstance();
        } else {
            return entry;
        }
    }

    private byte[] readHeader(InputStream in, int headerSize) throws IOException {

        in.mark(headerSize);
        byte[] header = new byte[headerSize];
        try {
            ByteStreams.readFully(in, header);
        } catch (IOException e) {
            // its ok
        }

        in.reset();
        return header;
    }

    private int findMaxHeaderSize(int headerSize, List<StreamMatcherFactory> detectors) {

        for (StreamMatcher detector : detectors) {
            headerSize = Math.max(detector.neededHeaderSizeForDetection(), headerSize);
        }
        return headerSize;
    }
}
