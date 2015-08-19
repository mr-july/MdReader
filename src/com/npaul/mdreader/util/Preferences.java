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


import android.content.SharedPreferences;
import com.commonsware.cwac.anddown.AndDown;
import java.util.HashMap;


/**
 * This class contains helper functions for preferences handling, i.e.
 * conversions between preferences values and data types, used to implement
 * the application business logic
 *
 * @author Igor Lyubimov <igor.lyubimov@gmail.com>
 */
public class Preferences
{
  /**
   * Hash, connecting {@code AndDown.Extensions} elements to preference keys
   */
  private static final HashMap<AndDown.Extensions, String> mdExt2Prefs =
    new HashMap<AndDown.Extensions, String> ();


  static
  {
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_TABLES,
      "preference_use_md_extension_tables");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_FENCED_CODE,
      "preference_use_md_extension_fenced_code");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_FOOTNOTES,
      "preference_use_md_extension_footnotes");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_AUTOLINK,
      "preference_use_md_extension_autolink");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_STRIKETHROUGH,
      "preference_use_md_extension_strikethrough");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_UNDERLINE,
      "preference_use_md_extension_underline");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_HIGHLIGHT,
      "preference_use_md_extension_highlight");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_QUOTE,
      "preference_use_md_extension_quote");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_SUPERSCRIPT,
      "preference_use_md_extension_superscript");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_MATH,
      "preference_use_md_extension_math");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_NO_INTRA_EMPHASIS,
      "preference_use_md_extension_no_intra_emphasis");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_SPACE_HEADERS,
      "preference_use_md_extension_space_headers");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_MATH_EXPLICIT,
      "preference_use_md_extension_math_explicit");
    mdExt2Prefs.put (AndDown.Extensions.HOEDOWN_EXT_DISABLE_INDENTED_CODE,
      "preference_use_md_extension_disable_indented_code");
  }


  /**
   * Gets MarkDown extensions, selected in preferences
   * 
   * @param sharedPref    application shared preferences
   * 
   * @return              bit mask of selected MarkDown extensions as used
   *                      by the AndDown library
   */
  public static int getAllowedMarkDownExtensions (SharedPreferences sharedPref)
  {
    int ext = 0;

    for (AndDown.Extensions e
      : AndDown.Extensions.values ())
      ext = ext | getIntValueForBooleanPreference (sharedPref,
        mdExt2Prefs.get (e), e.getValue ());

    return ext;
  }


  /**
   * Checks if specified boolean preference is set ({@code true}), if so, then
   * returns specified value, otherwise 0.
   * 
   * @param sharedPref    application shared preferences
   * @param prefName      preference name (key)
   * @param value         value to be used if preference is set
   * 
   * @return              specified value if specified boolean preference
   *                      is set, otherwise 0
   */
  public static int getIntValueForBooleanPreference (
    SharedPreferences sharedPref, String prefName, int value)
  {
    int val = 0;

    if (sharedPref.getBoolean (prefName, false))
      val = value;

    return val;
  }
}
