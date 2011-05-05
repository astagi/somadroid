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
 * Module name: Radio
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/


package org.as.somadroid;

import android.net.Uri;

public class Radio {
	

    private Channel current_ch = null;
    private Uri uri_context = null;
    private boolean is_playing = false;

    
    public boolean isPlayingChannel(Channel ch)
    {
        if (this.current_ch == null)
            return false;
		
        if (ch.getAttribute("title").compareTo(this.current_ch.getAttribute("title"))==0)
            return true;
		
        return false;
    }
	
    public Channel getChannel()
    {
        return this.current_ch;
    }
	
    public void setChannel(Channel ch)
    {
        this.current_ch = ch;
        this.uri_context = Uri.parse(this.current_ch.getPlaylist("slow",0).getFile(0).getUrl());
    }
	
    public void setIsPlaying(boolean is_playing)
    {
        this.is_playing = is_playing;
    }
    
    public boolean isPlaying()
    {
        return this.is_playing;
    }
	
    
    public Uri getUri()
    {
        return this.uri_context;
    }

}
