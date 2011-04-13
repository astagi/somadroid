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
 * Module name: SongsHistory
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.as.somadroid;

import java.util.ArrayList;

public class SongsHistory {
	
	private ArrayList <Song> songs;
	
	
	public SongsHistory()
	{
		this.songs = new ArrayList <Song>();
	}
	
	public void add(String auth, String title)
	{
		Song song_fetched = new Song(auth,title);
	
		
		if(songs.size() == 0)
		    this.songs.add(0, song_fetched);
		
		else if(!song_fetched.equals(this.songs.get(0)))
		{
			if(songs.size() == Consts.SONG_LIST_MAXSIZE)
				this.songs.remove(Consts.SONG_LIST_MAXSIZE - 1);
		    this.songs.add(0, song_fetched);
		}
	}
	
	public ArrayList <Song> getSongs()
	{
		return this.songs;
	}

}
