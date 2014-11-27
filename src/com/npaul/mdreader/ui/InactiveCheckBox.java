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
package com.npaul.mdreader.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;

/**
 * Checkbox that doesn't react to touch events.
 *
 * @author Cedric Bosdonnat <cedric.bosdonnat@free.fr>
 *
 */
public class InactiveCheckBox extends CheckBox {

    public InactiveCheckBox(Context context) {
        super(context);
    }

    public InactiveCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InactiveCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Just don't handle the touch event in this control
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
