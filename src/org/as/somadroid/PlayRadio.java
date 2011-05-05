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
 * Module name: PlayRadio
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/


package org.as.somadroid;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import org.as.somadroid.R;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PlayRadio extends ListActivity implements ChannelView{

    private TextView radio_title;
    private TextView radio_dj;
    private TextView radio_description;
    private ImageView radio_logo;
    private LinearLayout radio_w_layout;
    
    private Channel channel;
    private RadioWidget radio_w;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        channel = ((SomadroidApp)this.getApplication()).channel_for_activity;
        ((SomadroidApp)this.getApplication()).channel_factory.addChannelAndView(this, channel);
        this.radio_w = new RadioWidget(this);
        this.radio_w.setChannelToPlay(channel);
        setContentView(R.layout.list_view_songs);
        this.populateRadioList();
        this.getElementsFromLayout();
        
    }
    
    protected void getElementsFromLayout()
    {
        this.radio_title = (TextView)this.findViewById(R.id.radio_title);    	
        this.radio_dj = (TextView)this.findViewById(R.id.radio_dj);
        this.radio_description = (TextView)this.findViewById(R.id.radio_description);
        this.radio_logo = (ImageView)this.findViewById(R.id.img_current_radio);
        this.radio_w_layout = (LinearLayout)this.findViewById(R.id.radiowidget);
        
        this.radio_w_layout.addView(this.radio_w);
		
        this.radio_logo.setImageBitmap(this.channel.getImage());
		
        this.radio_title.setText(this.channel.getAttribute("title"));
        this.radio_dj.setText("Dj: " + this.channel.getAttribute("dj"));
        this.radio_description.setText(this.channel.getAttribute("description"));
    }
    
    
    private void populateRadioList() {
    	
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();   

    	
        ArrayList <Song> songs = this.channel.getHistory().getSongs();

        for( int i = 0; i < songs.size() ; i++ )
        {
            HashMap<String,Object> temp = new HashMap<String,Object>();
            temp.put("song_time",  songs.get(i).getTime());
            temp.put("song_auth",  songs.get(i).getAuthor());
            temp.put("song_title", songs.get(i).getTitle());

            list.add(temp);
        }
		
        SpecialAdapter adapter_songs = new SpecialAdapter(this,list,
        	    R.layout.list_view_row_songs,
        	    new String[] {"song_time", "song_auth","song_title"},
        	    new int[] { R.id.song_time, R.id.song_auth, R.id.song_title}
        );
        
        this.setListAdapter(adapter_songs);
        adapter_songs.notifyDataSetChanged();
    }

    public void updateMe() {
        this.populateRadioList();
    }

    @Override
    public void updateChannel(Channel currentCh) {
        this.populateRadioList();
        
    }

    
}