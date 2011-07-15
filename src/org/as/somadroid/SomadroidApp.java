
package org.as.somadroid;

import java.util.ArrayList;

import android.app.Application;


public class SomadroidApp extends Application {

    public RadioController radio_controller = new RadioController(new Radio());
    public static final ChannelsFactory channel_factory = new ChannelsFactory();
    private ArrayList<SomaActivity> activities = new ArrayList<SomaActivity>();
    private int last_channel_seen;
    
    public void addActivity(SomaActivity a)
    {
        if(this.activities.size() < 2)
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
        //this.activities.remove(a);
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
