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

public class Channel {
	
    private HashMap <String,String> attributes;
    private ArrayList <Playlist> playlists;
    private Bitmap image_bmp;
    private SongsHistory songs_history;
    private String local_img_dir;
	
    public Channel()
    {
        this.playlists = new ArrayList <Playlist> ();
        this.attributes = new HashMap <String,String> ();
        this.songs_history = new SongsHistory();
        this.local_img_dir = "";
    }
	
    public void addAttribute(String id, String value)
    {
        this.attributes.put(id, value);
        this.attributeAddedHandler(id, value);
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
        if(attribute.compareTo("lastPlaying") == 0)
        {
            String[] song = value.split(" - ");
            this.songs_history.add(song[0], song[1]);
        }
    }
	
    public void addPlaylist(Playlist pls)
    {
        this.playlists.add(pls);
    }
	
    public Playlist getFastlPlaylist(int id)
    {
        return playlists.get(id);
    }
	
    protected void buildImage()
    {
        if(this.image_bmp == null)
        {
            this.image_bmp = Utils.loadBitmap(this.getAttribute("image"));
            this.local_img_dir = Utils.saveImageToPhone(this.image_bmp, this.getLocalName());
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
