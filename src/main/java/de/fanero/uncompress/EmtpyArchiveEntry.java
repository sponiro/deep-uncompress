package de.fanero.uncompress;

import org.apache.commons.compress.archivers.ArchiveEntry;

import java.util.Date;

/**
* Created by Robert Kühne on 05.10.2014.
*/
public class EmtpyArchiveEntry implements ArchiveEntry {

    private static EmtpyArchiveEntry instance = new EmtpyArchiveEntry();

    public static EmtpyArchiveEntry getInstance() {
        return instance;
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
