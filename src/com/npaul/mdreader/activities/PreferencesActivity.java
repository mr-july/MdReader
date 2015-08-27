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


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.npaul.mdreader.R;
import com.npaul.mdreader.util.Preferences;


/**
 * Activity to set the application preferences (Settings)
 *
 * @author Igor Lyubimov <igor.lyubimov@gmail.com>
 */
public class PreferencesActivity extends BaseActivity
{
  /**
   * bit mask to be set if rendering-related parameters was changed
   */
  public static final int RENDER_PARAMS_CHANGED = (1 << 3);


  /**
   * listener for application configuration changes
   */
  private SharedPreferences.OnSharedPreferenceChangeListener listener;


  /**
   * result code of preference activity will be set according to changed
   * preferences
   */
  private int resultCode = RESULT_CANCELED;


  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);

    setContentView (R.layout.activity_preferences);
    setTitle (R.string.title_activity_preferences);

    // Display the fragment as the main content.
    FragmentManager mFragmentManager = getFragmentManager ();
    FragmentTransaction mFragmentTransaction = mFragmentManager
      .beginTransaction ();
    PrefsFragment mPrefsFragment = new PrefsFragment ();
    mFragmentTransaction.replace (android.R.id.content, mPrefsFragment);
    mFragmentTransaction.commit ();

    listener = new SharedPreferences.OnSharedPreferenceChangeListener ()
    {
      public void onSharedPreferenceChanged (SharedPreferences prefs, String key)
      {
        if (Preferences.isRenderingRelatedPrefs (key))
        {
          resultCode |= RENDER_PARAMS_CHANGED;
          setResult (resultCode);
        }
      }
    };

    PreferenceManager.getDefaultSharedPreferences (this).
      registerOnSharedPreferenceChangeListener (listener);

//  We could have condensed the 5 lines into 1 line of code. 		
//		getFragmentManager().beginTransaction()
//				.replace(android.R.id.content, new PrefsFragment()).commit();
  }

  /**
   * Fragment, handling the preferences setting
   */
  public static class PrefsFragment extends PreferenceFragment
  {
    @Override
    public void onCreate (Bundle savedInstanceState)
    {
      super.onCreate (savedInstanceState);

      // Load the preferences from an XML resource
      addPreferencesFromResource (R.xml.preferences);
    }
  }
}
