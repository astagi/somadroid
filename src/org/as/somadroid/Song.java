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
 * Module name: Song
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/


package org.as.somadroid;

import java.util.Calendar;

public class Song {
	
    private String title;
    private String author;
    private String time;
	
    public Song(String author, String title)
    {
        this.author = author;
        this.title = title;
        Calendar c = Calendar.getInstance();
        this.time = Utils.formatCalendarTime(c.get(Calendar.HOUR),c.get(Calendar.MINUTE));
    }

    public String getTitle() 
    {
        return title;
    }

    public String getAuthor() 
    {
        return author;
    }
	
    public String getTime() 
    {
        return time;
    }
	
    public boolean equals(Object song)
    {
        if(song == null)
            return false;
		
        Song othersong=(Song)song;
		
        if(this.getAuthor().compareTo(othersong.getAuthor())==0
        	    && this.getTitle().compareTo(othersong.getTitle())==0)
            return true;
        return false;
    }

}
