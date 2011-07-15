

package org.as.somadroid;

public class PlayListFile {
	
    private String title;
    private String url;
    private String length;
	
    public PlayListFile(String url, String title, String length)
    {
        this.title = title;
        this.url = url;
        this.length = length;
    }
	
    public String getTitle() 
    {
        return title;
    }
	
    public void setTitle(String title) 
    {
        this.title = title;
    }
	
    public String getUrl() 
    {
        return url;
    }
	
    public void setUrl(String url) 
    {
        this.url = url;
    }
	
    public String getLength() 
    {
        return length;
    }
	
    public void setLength(String length) 
    {
        this.length = length;
    }
	
}
