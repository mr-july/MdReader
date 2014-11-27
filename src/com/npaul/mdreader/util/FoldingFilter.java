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
 * Use JQuery to add folding feature to titles.
 *
 * This aims at easing the readability of long articles on mobile devices.
 * All content will be folded be default and only H1 titles will be visible.
 * Clicking on a title will unfold its content.
 *
 * @author Cedric Bosdonnat <cedric.bosdonnat@free.fr>
 *
 */
public class FoldingFilter extends Filter {

    @Override
    public CharSequence filter(CharSequence in) {
        if (in == null)
            return in;

        CharSequence out = addContentDivs(in);
        out = includeCss(out);
        out = includeJQuery(out);

        String script = "<script>"
                      + "$(\"h1\").click(function() {"
                      + "    var content = $(this).get(0).nextElementSibling;"
                      + "    $(content).slideToggle(\"fast\");"
                      + "    $(this).get(0).classList.toggle(\"open_section\");"
                      + "});"
                      + "$(\".content\").hide()"
                      + "</script>";
        out = appendToBody(out, script);
        return out;
    }

    protected CharSequence addContentDivs(CharSequence in) {
        StringBuffer out = new StringBuffer();

        Matcher endMatch = Pattern.compile("</h1>", Pattern.CASE_INSENSITIVE).matcher(in);
        Matcher startMatch = Pattern.compile("<h1>", Pattern.CASE_INSENSITIVE).matcher(in);
        int pos = 0;
        while (pos < in.length()) {
            if (endMatch.find(pos)) {
                int start = endMatch.end();
                int end = in.length();
                out.append(in.subSequence(pos, start));
                out.append("<div class=\"content\">");
                if (startMatch.find(start)) {
                    end = startMatch.start();
                }
                out.append(in.subSequence(start, end));
                out.append("</div>");
                pos = end;
            } else {
                out.append(in.subSequence(pos, in.length()));
                pos = in.length();
            }
        }

        return out;
    }

    protected CharSequence includeCss(CharSequence in) {
        String css = "<style>"
                   + "h1 {"
                   + "    background: url(\"file:///android_asset/show.png\") no-repeat scroll left center rgba(0, 0, 0, 0);"
                   + "    border-bottom: 1px solid #E2E3E4;"
                   + "    padding-left: 26px;"
                   + "    position: relative;"
                   + "    line-height: 1.3;"
                   + "}"
                   + "h1.open_section {"
                   + "    background: url(\"file:///android_asset/hide.png\") no-repeat scroll left center rgba(0, 0, 0, 0);"
                   + "}"
                   + "</style>";

        return appendToHead(in, css);
    }

    protected CharSequence includeJQuery(CharSequence in) {
        String include = "<script src=\"file:///android_asset/jquery-2.1.0.min.js\"></script>";
        if (in.toString().indexOf(include) == -1)
            return appendToBody(in, include);
        return in;
    }

}
