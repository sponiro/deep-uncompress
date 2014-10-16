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
package de.fanero.uncompress;

import de.fanero.uncompress.matcher.StreamDetectorImpl;
import de.fanero.uncompress.matcher.StreamMatcher;
import de.fanero.uncompress.matcher.StreamMatcherFactory;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert Kühne
 */
public class DeepDetectionInputStreamBuilder {

    private List<StreamMatcherFactory> detectors;

    public DeepDetectionInputStreamBuilder() {
        this.detectors = new ArrayList<>();
    }

    public DeepDetectionInputStreamBuilder register(StreamMatcherFactory fileTypeDetector) {

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
