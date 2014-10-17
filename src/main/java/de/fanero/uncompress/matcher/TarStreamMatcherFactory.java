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
package de.fanero.uncompress.matcher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Robert Kühne
 */
public class TarStreamMatcherFactory implements StreamMatcherFactory {

    @Override
    public int neededHeaderSizeForDetection() {
        return 512;
    }

    @Override
    public MatchResult matches(byte[] header, ArchiveEntry archiveEntry) {

        return TarArchiveInputStream.matches(header, header.length) ? MatchResult.MATCH : MatchResult.NO_MATCH;
    }

    @Override
    public ArchiveInputStream createStream(InputStream input, ArchiveEntry archiveEntry) throws IOException {

        return new TarArchiveInputStream(input);
    }
}