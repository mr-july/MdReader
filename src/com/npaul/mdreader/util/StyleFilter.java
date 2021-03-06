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
import com.npaul.mdreader.R;


public class StyleFilter extends ContextDependentFilter
{

  /**
   * constructor, populating the context
   *
   * @param context current context
   */
  public StyleFilter (Context context)
  {
    super (context);
  }


  @Override
  public CharSequence filter (CharSequence in)
  {
    String css = context.getResources ().getString (R.string.INLINE_CSS);
    return appendToHead (in, css);
  }
}
