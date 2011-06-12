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
 * Module name: SomadroidApp
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.as.somadroid;

import java.util.ArrayList;

import android.app.Application;


public class SomadroidApp extends Application {

    public RadioController radio_controller = new RadioController(new Radio());
    public ChannelsFactory channel_factory = new ChannelsFactory();
    private ArrayList<SomaActivity> activities = new ArrayList<SomaActivity>();
    private int last_channel_seen;
    
    public void addActivity(SomaActivity a)
    {
        this.activities.add(a);
    }
    
    public void exit()
    {
        for(int i = 0; i < this.activities.size(); i++)
            this.activities.get(i).cleanExit();
        
        System.exit(0);
    }
    
    public void removeActivity(SomaActivity a)
    {
        this.activities.remove(a);
    }
	
    public void setLastChSeen(int last_channel)
    {
        this.last_channel_seen = last_channel;
    }

    public int getLastChSeen()
    {
        return this.last_channel_seen;
    }
	
	
}
