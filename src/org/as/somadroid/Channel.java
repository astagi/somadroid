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
 * Module name: Channel
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.as.somadroid;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.util.Log;

public class Channel {
	
    private HashMap <String,String> attributes;
    private ArrayList <Playlist> playlists_fast;
    private ArrayList <Playlist> playlists_slow;
    private Bitmap image_bmp;
    private SongsHistory songs_history;
    private String local_img_dir;
    
    private Song last_song;
	
    public Channel()
    {
        this.playlists_fast = new ArrayList <Playlist> ();
        this.playlists_slow = new ArrayList <Playlist> ();
        this.attributes = new HashMap <String,String> ();
        this.songs_history = new SongsHistory();
        this.local_img_dir = "";
    }
	
    public void addAttribute(String id, String value)
    {
        this.attributes.remove(id);
        this.attributes.put(id, value);
        this.attributeAddedHandler(id, value);
        
        Log.i("array_fast","" + this.playlists_fast.size());
        Log.i("array_slow","" + this.playlists_slow.size());
        Log.i("array_attributes","" + this.attributes.size());

    }
	
    public SongsHistory getHistory()
    {
        return this.songs_history;
    }
	
    public String getAttribute(String id)
    {
        return this.attributes.get(id);
    }
	
    protected void attributeAddedHandler(String attribute, String value)
    {
        if(attribute.compareTo("image") == 0)
            this.buildImage();
        if(attribute.compareTo("slowpls") == 0)
            this.addSlowPlaylist(new Playlist(value));
        if(attribute.compareTo("fastpls") == 0)
            this.addFastPlaylist(new Playlist(value));
        if(attribute.compareTo("lastPlaying") == 0)
        {
            String[] song = value.split(" - ");
            this.songs_history.add(song[0], song[1]);
            this.last_song = new Song(song[0], song[1]);
        }
    }
	
    private void addFastPlaylist(Playlist pls)
    {
        if(this.playlists_fast.size() < 4)
            this.playlists_fast.add(pls);
    }
    
    private void addSlowPlaylist(Playlist pls)
    {
        if(this.playlists_slow.size() < 4)
            this.playlists_slow.add(pls);
    }
    
    public Song getLastSong()
    {
        return this.last_song;
    }
	
    public Playlist getPlaylist(String type, int id)
    {
        if(type == "fast")
            return playlists_fast.get(id);
        else
            return playlists_slow.get(id);
    }
    
	
    protected void buildImage()
    {
        if(this.image_bmp == null)
        {
            String localName = this.getLocalName();
            
            if(!Utils.existsImageInPhone(localName))
            {
                this.image_bmp = Utils.loadBitmap(this.getAttribute("image"));
                Utils.saveImageToPhone(this.image_bmp, localName);
            }
            else
            {
                this.image_bmp = Utils.loadBitmapFromFile(localName);
            }
            
            this.local_img_dir = Utils.getAbsImagePath(localName);
        }
    }
	
    public String getImagePath()
    {
        return this.local_img_dir;
    }
	
    public Bitmap getImage()
    {
        return this.image_bmp;
    }
	
    protected String getLocalName()
    {
        return this.getAttribute("title").replaceAll(" ", "_") + ".png";
    }
	

}
