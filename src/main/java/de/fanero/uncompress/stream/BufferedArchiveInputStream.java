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

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author Robert Kühne
 */
public class BufferedArchiveInputStream  extends ArchiveInputStream {

    private final ArchiveInputStream archiveInputStream;
    private BufferedInputStream buffered;

    public BufferedArchiveInputStream(ArchiveInputStream archiveInputStream) {

        this.archiveInputStream = archiveInputStream;
        this.buffered = new BufferedInputStream(archiveInputStream);
    }


    @Override
    public ArchiveEntry getNextEntry() throws IOException {

        ArchiveEntry nextEntry = archiveInputStream.getNextEntry();
        this.buffered = new BufferedInputStream(archiveInputStream);
        return nextEntry;
    }

    @Override
    public int read() throws IOException {
        return buffered.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return buffered.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return buffered.skip(n);
    }

    @Override
    public int available() throws IOException {
        return buffered.available();
    }

    @Override
    public void mark(int readlimit) {
        buffered.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        buffered.reset();
    }

    @Override
    public boolean markSupported() {
        return buffered.markSupported();
    }

    @Override
    public void close() throws IOException {
        buffered.close();
    }
}
