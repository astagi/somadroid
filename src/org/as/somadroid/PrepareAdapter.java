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
 * Module name: PrepareAdapter
 * Description: a subactivity to prepare the listview for the main activity
 * Date: 06/05/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.as.somadroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.SimpleAdapter;

public class PrepareAdapter extends AsyncTask<Void,Void,SimpleAdapter > {
    
    private ProgressDialog dialog;
    private boolean output_visible;
    private Somadroid soma;
    
    
    public PrepareAdapter(Somadroid soma, boolean output_visible)
    {
        super();
        this.soma = soma;
        this.output_visible = output_visible;
    }
    
    @Override
    protected void onPreExecute() {

        if(!this.output_visible)
            return;
        
        dialog = new ProgressDialog(soma);
        dialog.setMessage(soma.getString(R.string.loading_stations));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        
    }

    @Override
    protected SimpleAdapter doInBackground(Void... params) {
        return soma.getNewAdapter();
    }

    protected void onPostExecute(SimpleAdapter new_adapter) {
        
        if(new_adapter==null)
        {
            if(this.output_visible)
                dialog.dismiss();
            
            soma.onFail();
        }
        
        else
        {
            //Get the top position from the first visible element
            int idx = soma.getListView().getFirstVisiblePosition();
            View vfirst = soma.getListView().getChildAt(0);
            int pos = 0;
            
            if (vfirst != null) 
                pos = vfirst.getTop();
           
            //Set list infos
            soma.setAdapterAndNotify(new_adapter);
            //Restore the position
            soma.getListView().setSelectionFromTop(idx, pos);
            
            if(this.output_visible)
                dialog.dismiss();
            
            soma.doTheAutoRefresh(Consts.REFRESH_DELAY);

        }
        

    }
}
