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


import android.content.Context;

import java.util.ArrayList;

import com.commonsware.cwac.anddown.AndDown;


/**
 * Formats the Markdown into HTML and adds some bits to make it more beautiful.
 *
 * @author Cedric Bosdonnat <cedric.bosdonnat@free.fr>
 *
 */
public class Formatter
{
  /**
   * current context, which can be used e.g. to retrieve resources
   */
  protected Context context;


  /**
   * additional filters, applied to rendered HTML code
   */
  private final ArrayList<Filter> filters;


  /**
   * private constructor
   */
  private Formatter ()
  {
    filters = new ArrayList<Filter> ();
  }


  /**
   * constructor, populating the context
   *
   * @param context current context
   */
  public Formatter (Context context)
  {
    this ();
    this.context = context;

    if (Preferences.isExtraHtmlStylesAllowed (context))
      addFilter (new StyleFilter (context));

    if (Preferences.isHtmlHeaderFoldingAllowed (context))
      addFilter (new FoldingFilter (context));
  }


  /**
   * Convert given MarkDown code to HTML
   *
   * @param markdown     MarkDown code to be used
   *
   * @return generated HTML code
   */
  public CharSequence format (String markdown)
  {
    long start = System.currentTimeMillis ();

    CharSequence out =
      AndDown.markdownToHtml (markdown, getAllowedMarkDownExtensions ());

    // Apply the filters on the output.
    // Note that the order of the filters is important
    for (Filter filter: filters)
      out = filter.filter (out);

    long end = System.currentTimeMillis ();

    System.out.println ("Rendering time (ms): " + (end - start));
    return out;
  }


  /**
   * Add filter, which should be applied after MarkDown to HTML conversion
   *
   * @param filter filter to be applied
   */
  private void addFilter (Filter filter)
  {
    filters.add (filter);
  }


  /**
   * Gets MarkDown extensions, selected in preferences
   *
   * @return bit mask of selected MarkDown extensions as used
   *         by the AndDown library
   */
  private int getAllowedMarkDownExtensions ()
  {
    return Preferences.getAllowedMarkDownExtensions (context);
  }
}
