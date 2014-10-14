package de.fanero.uncompress;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Robert Kühne
 */
public class EmptyArchiveInputStreamTest {

    @Test
    public void testArchiveEntry() throws Exception {

        ArchiveInputStream stream = EmptyArchiveInputStream.getInstance();

        assertThat(stream.getNextEntry(), is(nullValue()));
        assertThat(stream.getNextEntry(), is(nullValue()));
    }

    @Test
    public void testRead() throws Exception {

        ArchiveInputStream stream = EmptyArchiveInputStream.getInstance();

        assertThat(stream.read(), is(-1));
    }

    @Test
    public void testReadWithBuffer() throws Exception {

        ArchiveInputStream stream = EmptyArchiveInputStream.getInstance();

        byte[] buffer = new byte[2];
        assertThat(stream.read(buffer), is(-1));
    }

    @Test
    public void testReadWithBuffer2() throws Exception {

        ArchiveInputStream stream = EmptyArchiveInputStream.getInstance();

        byte[] buffer = new byte[2];
        assertThat(stream.read(buffer, 0, 2), is(-1));
    }
}