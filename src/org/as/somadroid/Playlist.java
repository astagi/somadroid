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
 * Module name: Playlist
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.as.somadroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Playlist {
	
	private int numentries;
	private ArrayList <PlayListFile> files;
	
	public Playlist(String fileuri)
	{
		this.files = new ArrayList <PlayListFile> ();
		this.readPlsFile(fileuri);
	}
	
	public PlayListFile getFile(int id)
	{
		return files.get(id);
	}
	
	private void readPlsFile(String fileuri)
	{		
		InputStream is = Utils.StreamFromUrl(fileuri);
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    String line;
    
	    try
	    {
		    line = rd.readLine();
		    line = rd.readLine();
		    
		    this.numentries = Integer.parseInt(line.split("=")[1]);
		    
		    for(int i = 0; i < this.numentries; i++)
		    {
		    	this.files.add(new PlayListFile(rd.readLine().split("=")[1], rd.readLine().split("=")[1], rd.readLine().split("=")[1]));
		    }
	      
		    rd.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}		    
	}

}
