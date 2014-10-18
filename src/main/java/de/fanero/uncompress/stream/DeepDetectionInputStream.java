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
package de.fanero.uncompress.stream;

import com.google.common.base.Preconditions;
import de.fanero.uncompress.matcher.StreamDetector;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;

/**
 * @author Robert Kühne
 */
public class DeepDetectionInputStream extends ArchiveInputStream {

    private ArchiveInputStream inputStream;
    private ArchiveInputStream entryIn;
    private StreamDetector streamDetector;
    private boolean deepNextEntry;

    public DeepDetectionInputStream(StreamDetector streamDetector, ArchiveInputStream inputStream) throws IOException {

        Preconditions.checkNotNull(streamDetector);
        Preconditions.checkNotNull(inputStream);

        this.streamDetector = streamDetector;
        this.inputStream = inputStream;
        this.entryIn = this.inputStream;
    }

    @Override
    public ArchiveEntry getNextEntry() throws IOException {

        if (deepNextEntry) {

            ArchiveEntry nextEntry = entryIn.getNextEntry();
            if (nextEntry == null) {
                deepNextEntry = false;
            } else {
                return nextEntry;
            }
        }

        ArchiveEntry nextEntry = inputStream.getNextEntry();
        if (nextEntry == null) {

            // no more entries
            // make delegated methods predictable
            entryIn = inputStream;
            return null;
        } else {

            BufferedArchiveInputStream bufferedArchiveInputStream = new BufferedArchiveInputStream(inputStream);
            ArchiveInputStream archiveInputStream = streamDetector.detectAndCreateInputStream(bufferedArchiveInputStream, nextEntry);

            if (archiveInputStream == null) {
                entryIn = bufferedArchiveInputStream;
                return nextEntry;
            } else {
                entryIn = new DeepDetectionInputStream(streamDetector, archiveInputStream);
                deepNextEntry = true;
                return entryIn.getNextEntry();
            }
        }
    }

    @Override
    public int read(byte[] b) throws IOException {
        return entryIn.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return entryIn.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return entryIn.skip(n);
    }

    @Override
    public int available() throws IOException {
        return entryIn.available();
    }

    @Override
    public void close() throws IOException {
        entryIn.close();
    }

    @Override
    public void mark(int readlimit) {
        entryIn.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        entryIn.reset();
    }

    @Override
    public boolean markSupported() {
        return entryIn.markSupported();
    }
}
