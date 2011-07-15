
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
                this.files.add(new PlayListFile(rd.readLine().split("=")[1], 
                                                rd.readLine().split("=")[1], 
                                                rd.readLine().split("=")[1]));
	      
            rd.close();
		    
        } catch (IOException e) {
            e.printStackTrace();
        }		    
    }

}
