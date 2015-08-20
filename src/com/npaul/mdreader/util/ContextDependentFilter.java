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


/**
 * Interface to implement new context-dependent filters to apply on an
 * HTML string.
 *
 * @author Igor Lyubimov <igor.lyubimov@gmail.com>
 */
public abstract class ContextDependentFilter extends Filter
{

  /**
   * current context, which can be used e.g. to retrieve resources
   */
  protected Context context;


  /**
   * private constructor
   */
  private ContextDependentFilter ()
  {
    // empty (prevent creation without parameter)
  }


  /**
   * constructor, populating the context
   *
   * @param context current context
   */
  public ContextDependentFilter (Context context)
  {
    this.context = context;
  }
}
