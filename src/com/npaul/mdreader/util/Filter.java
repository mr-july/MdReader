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


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Interface to implement new filters to apply on an HTML string.
 *
 * @author Cedric Bosdonnat <cedric.bosdonnat@free.fr>
 *
 */
public abstract class Filter {

    /**
     * Do the actual filtering.
     *
     * @param in the provided HTML
     * @return the filtered HTML
     */
    public abstract CharSequence filter(CharSequence in);

    protected CharSequence appendToHead(CharSequence in, String content) {
        StringBuffer out = new StringBuffer();

        Matcher m = Pattern.compile("</head>", Pattern.CASE_INSENSITIVE).matcher(in);
        int pos;
        String toAppend = content;
        if (m.find()) {
            pos = m.start();
        } else {
            m = Pattern.compile("<html>", Pattern.CASE_INSENSITIVE).matcher(in);
            pos = 0;
            if (m.find()) {
                pos = m.end();
            }
            toAppend = "<head>" + content + "</head>";
        }
        out.append(in.subSequence(0, pos));
        out.append(toAppend);
        out.append(in.subSequence(pos, in.length()));

        return out;
    }

    protected CharSequence appendToBody(CharSequence in, String content) {
        StringBuffer out = new StringBuffer();

        Matcher m = Pattern.compile("</body>", Pattern.CASE_INSENSITIVE).matcher(in);
        int pos = in.length();
        if (m.find()) {
            pos = m.start();
        }
        out.append(in.subSequence(0, pos));
        out.append(content);
        out.append(in.subSequence(pos, in.length()));

        return out;
    }
}
