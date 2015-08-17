/*
 * This file is part of MdReader.
 * 
 * MdReader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MdReader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MdReader.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.npaul.mdreader.util;

import java.util.ArrayList;

import com.commonsware.cwac.anddown.AndDown;


/**
 * Formats the Markdown into HTML and adds some bits to make it more beautiful.
 *
 * @author Cedric Bosdonnat <cedric.bosdonnat@free.fr>
 *
 */
public class Formatter {

    private final ArrayList<Filter> filters;

    public Formatter() {
        filters  = new ArrayList<Filter>();
    }

    public CharSequence format(String markdown) {
        long start = System.currentTimeMillis();

        // TODO: make MarkDown extensions configurable
        int ext = AndDown.Extensions.HOEDOWN_EXT_AUTOLINK |
          AndDown.Extensions.HOEDOWN_EXT_FENCED_CODE |
          AndDown.Extensions.HOEDOWN_EXT_FOOTNOTES |
          AndDown.Extensions.HOEDOWN_EXT_HIGHLIGHT |
          AndDown.Extensions.HOEDOWN_EXT_STRIKETHROUGH |
          AndDown.Extensions.HOEDOWN_EXT_SUPERSCRIPT |
          AndDown.Extensions.HOEDOWN_EXT_TABLES;

        CharSequence out = AndDown.markdownToHtml (markdown, ext);

        // Apply the filters on the output.
        // Note that the order of the filters is important
        for (Filter filter : filters) {
            out = filter.filter(out);
        }
        long end = System.currentTimeMillis();

        System.out.println("Rendering time (ms): " + (end - start));
        return out;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }
}
