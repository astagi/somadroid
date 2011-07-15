package org.as.somadroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;


public class ChannelsFactory implements Controller {
	
    private Document channels_xml;
    private Document changes_xml;
    private int times_faliure;
    private ArrayList <Channel> chans = null;
    private ArrayList <ChannelAndView> channelsview = new ArrayList <ChannelAndView> ();
	private final int MAX_FALIURES = 5;
	private boolean first = true;
    private String chsource = Consts.CHANNELS_URL;
    
    private class ChannelAndView {

        private ChannelView chw; 
        private Channel ch;
        
        public ChannelAndView(ChannelView chw, Channel ch) {
            this.ch = ch;
            this.chw = chw;
        }
        
        public ChannelView getChannelView()
        {
            return this.chw;
        }
        
        public Channel getChannel()
        {
            return this.ch;
        }

    };
    
    
    public ChannelsFactory()
    {
        this.channels_xml = null;
    }
    
    public void addChannelAndView(ChannelView chw, Channel ch)
    {
        this.channelsview.add(new ChannelAndView(chw, ch));
    }
    
    public void removeController(ChannelView chw)
    {
        this.channelsview.remove(chw);
    }
	
    public boolean createChannels()
    { 
        this.feedChannels();
        return this.createChannelsList();
    }
	

    private boolean createChannelsList()
    {
        ArrayList <Channel> chans_aux = null;
        
        if(this.chans != null)
        {
            chans_aux = new ArrayList <Channel> ();
            
            for(int i = 0; i < this.chans.size(); i++)
                chans_aux.add(new Channel());
            
            Collections.copy(chans_aux, this.chans);
        }
		
        try{
		
            NodeList nodeLst = this.channels_xml.getElementsByTagName("channel");
            String res = null;
            
            if(nodeLst.getLength() < 20)
            {
                return false;
            }
			
            if(this.chans == null)
            {
                this.chans = new ArrayList <Channel> ();               
                
                for ( int i = 0; i < nodeLst.getLength() ; i++ )
                    this.chans.add(new Channel());
                
                Log.i("NULL","NULL");
            }
			
            for ( int i = 0; i < nodeLst.getLength() ; i++ )
            {
                Node fstNode = nodeLst.item(i);
	
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
			    	
                    NodeList n = fstNode.getChildNodes();
			      
                    for ( int j = 1; j < n.getLength() ; j++ )
                    {  
                        try{
                            res = n.item(j).getChildNodes().item(0).getNodeValue();
                        }catch (Exception ex){
                            res = "Information not avaible.";
                        }
                        
                        this.chans.get(i).addAttribute(n.item(j).getNodeName(), "" + res);
                    }
	
                }
            }    
            
            if(this.first)
            {
                this.chsource = Consts.REFRESH_URL;
                this.first = false;
            }
            
            if(this.chans == null && chans_aux != null)
            {
                this.chans = new ArrayList <Channel> ();
                
                for(int i = 0; i < chans_aux.size(); i++)
                    this.chans.add(new Channel());
                
                Collections.copy(this.chans,chans_aux);
            }
			
            return true;
            
        }catch(Exception e){
            if(this.chans != null && chans_aux != null)
            {
                this.chans = new ArrayList <Channel> ();
                
                for(int i = 0; i < chans_aux.size(); i++)
                    this.chans.add(new Channel());
                
                Collections.copy(this.chans,chans_aux);
            }
            return false;
        }
        
    }
    
    @Override
    public void inform(){
        for(int i = 0; i < this.channelsview.size(); i++)
        {
            Channel to_send = this.channelsview.get(i).getChannel();
            this.channelsview.get(i).getChannelView().updateChannel(to_send);
        }
    }

    public ArrayList <Channel> getChannels()
    {
        return this.chans;
    }
	
    public boolean isFailing()
    {
        return this.times_faliure == this.MAX_FALIURES;
    }
    
    private void feedChannels()
    {
        this.channels_xml = Utils.XMLFromUrl(this.chsource );
        
        if(this.channels_xml == null)
        {
            this.times_faliure = (this.times_faliure + 1) % this.MAX_FALIURES ;
            
            if(this.chans == null)
                this.times_faliure = this.MAX_FALIURES;           
        }
        else
            this.times_faliure = 0;
    }

    public int getNChannels() {
        return this.chans.size();
    }

}
