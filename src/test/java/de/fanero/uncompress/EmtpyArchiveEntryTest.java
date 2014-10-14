package de.fanero.uncompress;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Robert Kühne
 */
public class EmtpyArchiveEntryTest {

    @Test
    public void testGetName() throws Exception {

        EmtpyArchiveEntry entry = EmtpyArchiveEntry.getInstance();

        assertThat(entry.getName(), is(""));
    }

    @Test
    public void testGetSize() throws Exception {

        EmtpyArchiveEntry entry = EmtpyArchiveEntry.getInstance();

        assertThat(entry.getSize(), is(0L));
    }

    @Test
    public void testIsDirectory() throws Exception {

        EmtpyArchiveEntry entry = EmtpyArchiveEntry.getInstance();

        assertThat(entry.isDirectory(), is(false));
    }

    @Test
    public void testGetLastModifiedDate() throws Exception {

    }
}