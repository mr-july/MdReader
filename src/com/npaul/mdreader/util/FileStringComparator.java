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
import java.io.File;
import java.util.Comparator;
import java.util.Locale;

/**
 * Used to compare 2 file objects and sort them into alphabetical order
 *
 * @author Nathan Paul
 * @version 1.0
 */
public class FileStringComparator implements Comparator<File> {
    private boolean isDirectoryFirst;
    
    private FileStringComparator () {
      // empty
    }
    
    public FileStringComparator (Context context) {
        isDirectoryFirst = Preferences.isDirectoryFirst (context);
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(File lhs, File rhs) {
        int res = 0;
        String s1 = lhs.getName(),s2 = rhs.getName();
        boolean isD1 = lhs.isDirectory (), isD2 = rhs.isDirectory ();
        
        if ((isD1 || isD2) && !(isD1 && isD2) && isDirectoryFirst)
            res = isD1 ? -1 : 1;
        
        //we need to use Locale.ENGLISH to ensure that international users get their files sorted in an English fashion
        if (res == 0)
            res = s1.toLowerCase(Locale.ENGLISH).compareTo(
                s2.toLowerCase(Locale.ENGLISH));
        
        return res;
    }

}
