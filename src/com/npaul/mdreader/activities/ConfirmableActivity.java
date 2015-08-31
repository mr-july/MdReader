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
package com.npaul.mdreader.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import com.npaul.mdreader.R;



/**
 * An activity, supporting changes confirmation
 *
 * @author Igor Lyubimov <igor.lyubimov@gmail.com>
 */
public class ConfirmableActivity extends BaseActivity
{
  /**
   * flag which indicates if changes was accepted by user:
   * {@code null} - unknown;
   * {@code true} - user has accepted changes;
   * {@code false} - user has discarded changes.
   */
  protected Boolean changesConfirmed = null;


  /**
   * Show changes confirmation dialog, save user choice and finish the activity.
   * Normally should be called from overridden finish function.
   * @see EditActivity#finish() for usage example
   */
  protected void confirmChangesDialog ()
  {
    AlertDialog.Builder adb = new AlertDialog.Builder (this);
    adb.setTitle (R.string.save_changes_title);
    adb.setMessage (R.string.save_changes_message).
      setNegativeButton (R.string.no,
      new DialogInterface.OnClickListener ()
    {
      @Override
      public void onClick (DialogInterface dialog, int which)
      {
        changesConfirmed = false;
        finish ();
      }
    }).
      setPositiveButton (R.string.yes,
      new DialogInterface.OnClickListener ()
    {
      @Override
      public void onClick (DialogInterface dialog, int which)
      {
        changesConfirmed = true;
        finish ();
      }
    }).show ();
  }

}
