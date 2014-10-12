package de.fanero.uncompress.matcher;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Robert Kühne on 05.10.2014.
 */
public class StreamDetectorImpl implements StreamDetector {

    private final List<StreamTypeMatcher> detectors;
    private final int headerSize;

    public StreamDetectorImpl(List<StreamTypeMatcher> detectors) {
        this.detectors = detectors;
        this.headerSize = findMaxHeaderSize(0, detectors);
    }

    @Override
    public ArchiveInputStream detectAndCreateInputStream(InputStream in, ArchiveEntry archiveEntry) throws IOException {

        if (!in.markSupported()) {
            throw new IllegalArgumentException("Mark is not supported.");
        }

        byte[] header = readHeader(in, headerSize);

        String name = "";
        if (archiveEntry != null) {
            name = Strings.nullToEmpty(archiveEntry.getName());
        }

        for (StreamTypeMatcher detector : detectors) {
            if (detector.matches(header, name)) {
                return detector.createStream(detectors, in, archiveEntry);
            }
        }

        return null;
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

    private int findMaxHeaderSize(int headerSize, List<StreamTypeMatcher> detectors) {

        for (StreamTypeMatcher detector : detectors) {
            headerSize = Math.max(detector.neededHeaderSizeForDetection(), headerSize);
        }
        return headerSize;
    }
}
