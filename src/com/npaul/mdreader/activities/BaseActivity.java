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


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.npaul.mdreader.R;
import com.npaul.mdreader.util.Preferences;


/**
 * An activity, which contains functions for theme changes through preferences
 *
 * @author Igor Lyubimov <igor.lyubimov@gmail.com>
 */
public class BaseActivity extends Activity
{
  /**
   * Constant used to retrieve result code of started preference activity
   */
  public static final int PREFERENCE_CODE = 2;


  /**
   * theme to be used for activity
   */
  protected int theme = R.style.AppBaseTheme;


  /*
   * (non-Javadoc)
   *
   * @see android.app.Activity#onCreate(android.os.Bundle)
   */
  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);

    int configuredTheme = getConfiguredTheme ();

    if (theme != configuredTheme)
    {
      theme = configuredTheme;
      setTheme (theme);
    }

  }


  /*
   * (non-Javadoc)
   *
   * @see android.app.Activity#onRestart()
   */
  @Override
  protected void onRestart ()
  {
    super.onRestart ();
    int configuredTheme = getConfiguredTheme ();

    if (theme != configuredTheme)
      recreate ();
  }


  /**
   * Show preferences activity
   */
  protected void showPreferences ()
  {
    Intent intent = new Intent (this, PreferencesActivity.class);
    startActivityForResult (intent, PREFERENCE_CODE);
  }


  /**
   * get the theme, configured in preferences
   *
   * @return the theme, configured in preferences
   */
  protected int getConfiguredTheme ()
  {
    return Preferences.isLightTheme (this) ?
      R.style.AppBaseTheme_Light : R.style.AppBaseTheme;
  }
}
