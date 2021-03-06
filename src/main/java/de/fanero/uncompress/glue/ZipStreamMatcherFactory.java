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
package de.fanero.uncompress.glue;

import de.fanero.uncompress.factory.ZipStreamFactory;
import de.fanero.uncompress.glue.MatcherStreamFactory;
import de.fanero.uncompress.matcher.ZipStreamMatcher;

/**
 * @author Robert Kühne
 */
public class ZipStreamMatcherFactory extends MatcherStreamFactory {

    public ZipStreamMatcherFactory() {
        super(new ZipStreamMatcher(), new ZipStreamFactory());
    }
}
