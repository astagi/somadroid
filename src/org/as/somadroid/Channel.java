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
        String attribute = this.attributes.get(id);
        
        if(attribute == null)
            return "...";
        
        return attribute;
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
