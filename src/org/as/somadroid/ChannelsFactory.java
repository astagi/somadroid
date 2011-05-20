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
 * Module name: ChannelFactory
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/


package org.as.somadroid;

import java.util.concurrent.CopyOnWriteArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ChannelsFactory implements Controller {
	
    private Document channels_xml;
    private int times_faliure;
    private CopyOnWriteArrayList <Channel> chans = null;
    private CopyOnWriteArrayList <ChannelAndView> channelsview = new CopyOnWriteArrayList <ChannelAndView> ();
	private final int MAX_FALIURES = 5;
    
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
		
        CopyOnWriteArrayList <Channel> chans_aux = this.chans;
		
        try{
		
            NodeList nodeLst = this.channels_xml.getElementsByTagName("channel");
            String res = null;
			
            if(this.chans == null)
            {
                this.chans = new CopyOnWriteArrayList <Channel> ();
			
                for ( int i = 0; i < nodeLst.getLength() ; i++ )
                    this.chans.add(new Channel());
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
			
            return true;
            
        }catch(Exception e){
            this.chans = chans_aux;
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

    public CopyOnWriteArrayList <Channel> getChannels()
    {
        return this.chans;
    }
	
    public boolean isFailing()
    {
        return this.times_faliure == this.MAX_FALIURES;
    }
    
    private void feedChannels()
    {
        this.channels_xml = Utils.XMLFromUrl(Consts.CHANNELS_URL);
        
        if(this.channels_xml == null)
        {
            this.times_faliure = (this.times_faliure + 1) % this.MAX_FALIURES ;
            
            if(this.chans == null)
                this.times_faliure = this.MAX_FALIURES;           
        }
        else
            this.times_faliure = 0;
    }

}
