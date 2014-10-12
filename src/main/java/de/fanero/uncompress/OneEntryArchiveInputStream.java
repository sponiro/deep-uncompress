package de.fanero.uncompress;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
* Created by Robert Kühne on 03.10.2014.
*/
public class OneEntryArchiveInputStream extends ArchiveInputStream {

    private InputStream in;
    private ArchiveEntry archiveEntry;

    public OneEntryArchiveInputStream(InputStream in, ArchiveEntry archiveEntry) {
        this.in = in;
        this.archiveEntry = archiveEntry;
    }

    @Override
    public ArchiveEntry getNextEntry() throws IOException {

        ArchiveEntry entry = archiveEntry;
        archiveEntry = null;
        in = EmptyArchiveInputStream.getInstance();
        return entry;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return in.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return in.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return in.skip(n);
    }

    @Override
    public int available() throws IOException {
        return in.available();
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    @Override
    public void mark(int readlimit) {
        in.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        in.reset();
    }

    @Override
    public boolean markSupported() {
        return in.markSupported();
    }
}
