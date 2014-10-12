package de.fanero.uncompress;

import de.fanero.uncompress.matcher.StreamDetectorImpl;
import de.fanero.uncompress.matcher.StreamTypeMatcher;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert Kühne on 05.10.2014.
 */
public class DeepArchiveInputStreamBuilder {

    private List<StreamTypeMatcher> detectors;

    public DeepArchiveInputStreamBuilder() {
        this.detectors = new ArrayList<>();
    }

    public DeepArchiveInputStreamBuilder register(StreamTypeMatcher fileTypeDetector) {

        detectors.add(fileTypeDetector);

        return this;
    }

    public ArchiveInputStream build(InputStream inputStream) throws IOException {

        StreamDetectorImpl streamDetector = new StreamDetectorImpl(detectors);

        ArchiveInputStream archiveInputStream = streamDetector.detectAndCreateInputStream(inputStream, EmtpyArchiveEntry.getInstance());

        if (archiveInputStream == null) {
            archiveInputStream = new OneEntryArchiveInputStream(inputStream, EmtpyArchiveEntry.getInstance());
        }

        return new DeepDetectionInputStream(streamDetector, archiveInputStream);
    }
}
