package de.fanero.uncompress;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.io.IOException;

/**
* Created by Robert Kühne on 05.10.2014.
*/
public class EmptyArchiveInputStream extends ArchiveInputStream {

    private static ArchiveInputStream INSTANCE = new EmptyArchiveInputStream();

    public static ArchiveInputStream getInstance() {
        return INSTANCE;
    }

    private EmptyArchiveInputStream() {
    }

    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return null;
    }

    @Override
    public int read() throws IOException {
        return -1;
    }
}
