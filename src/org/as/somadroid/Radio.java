

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
