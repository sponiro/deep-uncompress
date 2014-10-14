package de.fanero.uncompress;

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
