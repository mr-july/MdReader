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

public class StyleFilter extends Filter {

    @Override
    public CharSequence filter(CharSequence in) {
        String css = "<style>"
                + "p, li, pre, th, td {"
                + "    font-size: 16pt;"
                + "}"
                + "h1 {"
                + "    font-size: 22pt;"
                + "}"
                + "li {"
                + "    margin-left: 5pt;"
                + "}"
                + "li p {"
                + "    margin: 0px"
                + "}"
                + "ul li {"
                + "    list-style-type: square;"
                + "}"
                + "table {"
                + "    margin-top: 10px;"
                + "    margin-bottom: 10px;"
                + "    margin-left: auto;"
                + "    margin-right: auto;"
                + "    width: 90%;"
                + "    border-collapse: collapse;"
                + "    border-spacing: 0;"
                + "}"
                + "th, td {"
                + "    border: 1px solid #DDDDDD;"
                + "    vertical-align: top;"
                + "    text-align: left;"
                + "}"
                + "blockquote, pre {"
                + "    border-left: 3px solid #ccc;"
                + "    margin-left: 10pt;"
                + "    padding-left: 10pt;"
                + "}"
                + "</style>";

        return appendToHead(in, css);
    }

}
