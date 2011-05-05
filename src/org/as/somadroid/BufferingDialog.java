/*
        Developed by Andrea Stagi <http://4spills.blogspot.com/>

        Somadroid: a free SomaFM Client for Android phones (http://somafm.com/)
        Copyright (C) 2010 Andrea Stagi <http://4spills.blogspot.com/>

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/***
 * 
 * Module name: BufferingDialog
 * Description: simply to show an easy dialog during buffering
 * Date: 06/05/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.as.somadroid;

import android.app.Activity;
import android.app.ProgressDialog;

public class BufferingDialog {

    ProgressDialog dialog;
    Activity activity;
    
    public BufferingDialog(Activity activity)
    {
        this.activity = activity;
    }
    
    public void start()
    {
        dialog = ProgressDialog.show(BufferingDialog.this.activity, "", "Buffering...", true);
    }
    
    public void stop()
    {
        if(dialog != null)
            dialog.dismiss();
    }
    
}
