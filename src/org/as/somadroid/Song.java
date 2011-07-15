

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
