
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
