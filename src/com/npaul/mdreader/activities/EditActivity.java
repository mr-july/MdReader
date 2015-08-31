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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.npaul.mdreader.R;


/**
 * An activity to help editing Markdown documents
 *
 * @author Nathan Paul
 * @version 1.1
 *
 */
public class EditActivity extends ConfirmableActivity
{

  final Context context = this;


  private EditText textArea;


  private File file;


  private String filename;


  private boolean textChanged = false;


  /**
   * Used to surround items in the edit field with the parameters set
   *
   * @param text
   *             The characters to surround the text with
   */
  private void editTextHelper (String text)
  {
    int start = textArea.getSelectionStart ();
    int end = textArea.getSelectionEnd ();

    textArea.getText ().insert (end, text);
    textArea.getText ().insert (start, text);
    textArea.setSelection (end + text.length ());
  }


  /**
   * Used to help the initial setup of the UI. It adds <code>listener</code>s
   * to all of the buttons in the text field bar
   */
  private void initButtons ()
  {
    final Button btn_tab = (Button)findViewById (R.id.btn_tab);
    final Button btn_bold = (Button)findViewById (R.id.btn_bold);
    final Button btn_italic = (Button)findViewById (R.id.btn_italic);
    final Button btn_url = (Button)findViewById (R.id.btn_url);
    final Button btn_code = (Button)findViewById (R.id.btn_code);
    final Button btn_hash = (Button)findViewById (R.id.btn_hash);

    btn_tab.setOnClickListener (new View.OnClickListener ()
    {

      @Override
      public void onClick (View v)
      {
        int start = textArea.getSelectionStart ();
        int end = textArea.getSelectionEnd ();
        textArea.getText ().replace (start, start, "\t");
        textArea.setSelection (end + 1);
      }
    });

    btn_bold.setOnClickListener (new View.OnClickListener ()
    {

      @Override
      public void onClick (View v)
      {
        editTextHelper ("**");
      }
    });

    btn_italic.setOnClickListener (new View.OnClickListener ()
    {

      @Override
      public void onClick (View v)
      {
        editTextHelper ("_");
      }
    });

    btn_hash.setOnClickListener (new View.OnClickListener ()
    {

      @Override
      public void onClick (View v)
      {
        textArea.getText ().insert (textArea.getSelectionStart (), "#");
      }
    });

    btn_url.setOnClickListener (new View.OnClickListener ()
    {

      @Override
      public void onClick (View v)
      {
        // set up the text fields for entry
        final EditText text = new EditText (context);
        text.setHint (R.string.link_text_hint);

        // Switch off auto-suggest for this field
        final EditText url = new EditText (context);
        url.setHint (R.string.link_url_hint);
        url.setInputType (InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        url.setOnFocusChangeListener (new OnFocusChangeListener ()
        {

          @Override
          public void onFocusChange (View v, boolean hasFocus)
          {
            url.setText ("http://");
            url.setSelection (url.getText ().length () - 1);
          }

        });

        // build a view for this entry
        LinearLayout layout = new LinearLayout (context);
        layout.setOrientation (LinearLayout.VERTICAL);
        layout.addView (text);
        layout.addView (url);

        // get start and end selection points
        final int start = textArea.getSelectionStart ();
        final int end = textArea.getSelectionEnd ();

        // build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder (context);
        builder.setTitle (R.string.enter_url);
        builder.setView (layout).setPositiveButton (R.string.ok,
          new DialogInterface.OnClickListener ()
          {

            @Override
            public void onClick (DialogInterface dialog,
              int which)
            {
              textArea.getText ().replace (
                start,
                end,
                "[" + text.getText () + "](" +
                url.getText () + ")");
              textArea.setSelection (start + 4 + text.length () +
                url.length ());
            }
          });

        // workaround to show keyboard
        AlertDialog ad = builder.create ();
        ad.getWindow ()
          .setSoftInputMode (
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        ad.show ();

      }
    });

    btn_code.setOnClickListener (new View.OnClickListener ()
    {

      @Override
      public void onClick (View v)
      {
        editTextHelper ("`");
      }
    });

  }


  /**
   * Initialises the text area by adding a <code>listener</code> for when the
   * contents are modified
   */
  private void initTextArea ()
  {
    textArea.addTextChangedListener (new TextWatcher ()
    {

      @Override
      public void afterTextChanged (Editable s)
      {
        // do nothing
      }


      @Override
      public void beforeTextChanged (CharSequence s, int start, int count,
        int after)
      {
        // do nothing
      }


      @Override
      public void onTextChanged (CharSequence s, int start, int before,
        int count)
      {
        if (!textChanged)
        {
          textChanged = true;
          setTitle ("*" + getTitle ());
        }

      }

    });
  }


  /**
   * Called by the Android system when the activity is opened by an intent
   *
   * @param intent
   *               The intent passed to the application
   */
  public void newIntent (Intent intent)
  {
    setIntent (intent);
  }

  /*
   * (non-Javadoc)
   *
   * @see android.app.Activity#onCreate(android.os.Bundle)
   */

  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);

    setContentView (R.layout.activity_edit);
    textArea = (EditText)findViewById (R.id.editTextArea);

    Intent intent = getIntent ();
    String scheme = intent.getScheme ();
    if (scheme.equals ("content"))
    {
      setTitle (R.string.dowloaded_content_title);
    }
    else
      if (scheme.equals ("file"))
      {
        Uri uri = intent.getData ();
        file = new File (uri.getPath ());
        filename = file.getName ();
        setTitle (filename);
      }
    readInData (intent);

    initTextArea ();
    initButtons ();

    // Don't show the application icon
    getActionBar ().setDisplayOptions (ActionBar.DISPLAY_SHOW_TITLE);
  }

  /*
   * (non-Javadoc)
   *
   * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
   */

  @Override
  public boolean onCreateOptionsMenu (Menu menu)
  {
    getMenuInflater ().inflate (R.menu.activity_edit, menu);
    return true;
  }


  /**
   * Responds to a menu press
   *
   * @param item selected menu item
   *
   * @return
   * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
   */
  @Override
  public boolean onOptionsItemSelected (MenuItem item)
  {
    switch (item.getItemId ())
    {

      case R.id.menu_done:
        changesConfirmed = true;
        finish ();
        break;

      default:
        return super.onOptionsItemSelected (item);
    }
    return true;
  }


  /**
   * Reads in the data from the intent and displays it in the text area
   *
   * @param intent
   *               The intent passed to the activity
   */
  private void readInData (Intent intent)
  {
    String result = new String ();
    if (intent.getExtras () != null && intent.getExtras ().containsKey ("text"))
    {
      result = intent.getExtras ().getString ("text");
    }
    else
    {
      try
      {
        InputStream in = getContentResolver ().openInputStream (
          intent.getData ());
        BufferedReader reader = new BufferedReader (
          new InputStreamReader (in));
        String line = reader.readLine ();
        while (line != null)
        {
          result += "\n" + line;
          line = reader.readLine ();
        }
      }
      catch (FileNotFoundException e)
      {

      }
      catch (IOException e)
      {

      }
    }
    textArea.setText (result);
  }


  /**
   * Overrides the finish function to ensure that the changes in edit text
   * have been saved
   */
  @Override
  public void finish ()
  {
    if (textChanged)
    {
      if (changesConfirmed == null)
        confirmChangesDialog ();
      else
      {
        if (changesConfirmed)
        {
          Intent data = new Intent ();
          data.setData (getIntent ().getData ());
          data.putExtra ("text", textArea.getText ().toString ());
          setResult (RESULT_OK, data);
        }
        else
          setResult (RESULT_CANCELED);

        super.finish ();
      }
    }
    else
      super.finish ();
  }
}
