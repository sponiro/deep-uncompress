package de.fanero.uncompress;

import org.apache.commons.compress.archivers.ArchiveEntry;

import java.util.Date;

/**
 * @author Robert Kühne
 */
public class EmtpyArchiveEntry implements ArchiveEntry {

    private static EmtpyArchiveEntry INSTANCE = new EmtpyArchiveEntry();

    public static EmtpyArchiveEntry getInstance() {
        return INSTANCE;
    }

    private EmtpyArchiveEntry() {
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public Date getLastModifiedDate() {
        return null;
    }
}
