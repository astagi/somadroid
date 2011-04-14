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
 * Module name: Somadroid
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.as.somadroid;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import org.as.somadroid.R;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Somadroid extends ListActivity {
	
    static final ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();   
    private ListView myView;
    private static boolean created = false;
    private PrepareAdapter pa = new PrepareAdapter(!created);
    private static Context context;
    private static final ChannelsFactory channel_factory = new ChannelsFactory();
    
    private static final Handler handler = new Handler();
    
    private void doTheAutoRefresh(long time) {
        handler.removeMessages(0);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if(!pa.isCancelled())
                    pa.cancel(true);
                pa = new PrepareAdapter(false);
                pa.execute();
            }
                 
         }, time);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        handler.removeMessages(0);
        setContentView(R.layout.custom_list_view);
        myView = (ListView)this.getListView();
        
        if(created){   
            this.setAdapterAndNotify(this.getNewAdapter());
        }
        
        pa.execute();
        created = true;

    }
    
    public SimpleAdapter getNewAdapter()
    {
    	
        if(!channel_factory.createChannels())
        {
            return null;
        }
   		
        ArrayList <Channel> chans2 = channel_factory.getChannels();  
	
        if(chans2 != null)
            populateRadioList(chans2);

        SimpleAdapter adapter = new SimpleAdapter(Somadroid.this,list,
        	    R.layout.custom_row_view,
        	    new String[] {"radio_logo", "radio_title","radio_listeners","radio_song"},
        	    new int[] { R.id.img, R.id.title,R.id.listeners, R.id.currentplay}
        ); 	
        
        return adapter;
    }
    
    public void setAdapterAndNotify(SimpleAdapter adapter)
    {
        Somadroid.this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public static Context app_context()
    {
        return context;
    }
    
    private void populateRadioList(ArrayList <Channel> arr) {
    	
        list.clear();
    	
        for( int i = 0; i < arr.size(); i++ )
        {			
            HashMap<String,Object> temp = new HashMap<String,Object>();
            temp.put("radio_logo", arr.get(i).getImagePath());
            temp.put("radio_title",arr.get(i).getAttribute("title"));
            temp.put("radio_listeners", "Genre:" + arr.get(i).getAttribute("genre"));
            temp.put("radio_song", arr.get(i).getAttribute("lastPlaying"));
            temp.put("channel", arr.get(i));
            list.add(temp);	
        }
    }
    
    public void showInternetProblemMessage()
    {	
        Toast.makeText(this, "Internet problems", Toast.LENGTH_LONG).show();
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) 
    {
        super.onListItemClick(l, v, position, id);
        HashMap o = (HashMap)this.getListAdapter().getItem(position);
        Channel ch = (Channel)o.get("channel");
        Intent intent = new Intent(this, PlayRadio.class);
        
        GlobalSpace.channel_for_activity = ch;
        GlobalSpace.radio.setContext(this);
        
        startActivityForResult(intent, 0);

    }

    public class PrepareAdapter extends AsyncTask<Void,Void,SimpleAdapter > {
       
        private ProgressDialog dialog;
        private boolean output_visible;
        
        
        public PrepareAdapter(boolean output_visible)
        {
            super();
            this.output_visible = output_visible;
        }
        
        @Override
        protected void onPreExecute() {

            if(!this.output_visible)
                return;
        	
            dialog = new ProgressDialog(Somadroid.this);
            dialog.setMessage("Loading stations...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
            
        }

        @Override
        protected SimpleAdapter doInBackground(Void... params) {
            return Somadroid.this.getNewAdapter();
        }

        protected void onPostExecute(SimpleAdapter new_adapter) {
        	
            if(new_adapter==null)
            {
                dialog.dismiss();
                Somadroid.this.showInternetProblemMessage();
            }
        	
            else
            {
                //Get the top position from the first visible element
                int idx = Somadroid.this.myView.getFirstVisiblePosition();
                View vfirst = Somadroid.this.myView.getChildAt(0);
                int pos = 0;
                if (vfirst != null) pos = vfirst.getTop();
	        	
                //Set list infos
                Somadroid.this.setAdapterAndNotify(new_adapter);

                //Restore the position
                Somadroid.this.myView.setSelectionFromTop(idx, pos);
	        	
                if(this.output_visible)
                    dialog.dismiss();
	       		
                GlobalSpace.notify.notifyRadio();
            }
        	
            doTheAutoRefresh(Consts.REFRESH_DELAY);

        }
    }
    
}