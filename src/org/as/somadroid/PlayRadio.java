

package org.as.somadroid;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PlayRadio extends SomaActivity implements ChannelView{

    private TextView listeners;
    private TextView radio_dj;
    private TextView radio_description;
    private ImageView radio_logo;
    private ImageButton btn_next;
    private ImageButton btn_prev;
    private LinearLayout radio_w_layout;
    
    private Channel channel;
    private RadioWidget radio_w;
    int n_channel = 0;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
                
        try{
            n_channel = this.getIntent().getExtras().getInt("ch_number");
            channel = ((SomadroidApp)this.getApplication()).channel_factory.getChannels().get(n_channel);
        }catch(NullPointerException ex)
        {
            n_channel = ((SomadroidApp)this.getApplication()).getLastChSeen();
            ArrayList<Channel> chs = ((SomadroidApp)this.getApplication()).channel_factory.getChannels();
            channel = chs.get(n_channel);
        }
        
        ((SomadroidApp)this.getApplication()).channel_factory.addChannelAndView(this, channel);
        this.radio_w = new RadioWidget(this);
        this.radio_w.setChannelToPlay(channel, n_channel);
        setContentView(R.layout.list_view_songs);
        this.populateRadioList();
        this.getElementsFromLayout();
        this.updateElements();
        this.setTitle(this.getString(R.string.app_name) + " : " + channel.getAttribute("title"));
        this.registerForContextMenu(this.getListView());
        
    }
    
    public void changeChannel()
    {
        channel = ((SomadroidApp)this.getApplication()).channel_factory.getChannels().get(n_channel);
        this.radio_w.setChannelToPlay(channel, n_channel);
        this.populateRadioList();
        this.updateElements();
        this.setTitle(this.getString(R.string.app_name) + " : " + channel.getAttribute("title"));
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info;
        try {
            info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            return;
        }
        
        HashMap o = (HashMap)this.getListAdapter().getItem(info.position);
        Song song = (Song)o.get("song");

        menu.setHeaderTitle(song.getAuthor() + " - " + song.getTitle());
        menu.add(0, 0, 0, this.getString(R.string.amazon_search));
        menu.add(0, 1, 0, this.getString(R.string.google_search));
          
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        
        AdapterView.AdapterContextMenuInfo info;
        HashMap obj;
        Song song;

        try {
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            return false;
        }
            
        switch (item.getItemId()) {
            case 0:
                obj = (HashMap)this.getListAdapter().getItem(info.position);
                song = (Song)obj.get("song");
                this.openBrowser(Utils.songInAmazon(song));
                return true;
            case 1:
                obj = (HashMap)this.getListAdapter().getItem(info.position);
                song = (Song)obj.get("song");
                this.openBrowser(Utils.songInGoogle(song));
                return true;
            case R.id.about:
                this.showAbout();
                return true;
            case R.id.exit:
                ((SomadroidApp)this.getApplication()).exit();
                return true;
        }
        return false;
    }
    
    protected void getElementsFromLayout()
    {
        this.listeners = (TextView)this.findViewById(R.id.listeners);    	
        this.radio_dj = (TextView)this.findViewById(R.id.radio_dj);
        this.radio_description = (TextView)this.findViewById(R.id.radio_description);
        this.radio_logo = (ImageView)this.findViewById(R.id.img_current_radio);
        this.radio_w_layout = (LinearLayout)this.findViewById(R.id.radiowidget);
        this.radio_w_layout.addView(this.radio_w, LayoutParams.FILL_PARENT);
        
        this.btn_next = (ImageButton)this.findViewById(R.id.nxt_btn);
        this.btn_prev = (ImageButton)this.findViewById(R.id.pre_btn);
		
 
    }
    
    public void updateElements()
    {
        this.radio_logo.setImageBitmap(this.channel.getImage());
        
        this.listeners.setText(this.getString(R.string.radio_listeners) + ": " + this.channel.getAttribute("listeners"));
        this.radio_dj.setText(this.getString(R.string.radio_dj) + ": " + this.channel.getAttribute("dj"));
        
        String description = this.channel.getAttribute("description");
        
        if(description.length() > 37)
            description = description.substring(0, 37);

        this.radio_description.setText(description + "..." + this.getString(R.string.expand));
        
        this.btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                int size = ((SomadroidApp)PlayRadio.this.getApplication()).channel_factory.getChannels().size();
                
                PlayRadio.this.n_channel =  (PlayRadio.this.n_channel + 1) % size;
                PlayRadio.this.changeChannel();
                
            }
            
        });
        
        this.btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                
                int size = ((SomadroidApp)PlayRadio.this.getApplication()).channel_factory.getChannels().size();

                PlayRadio.this.n_channel --;
                
                if(PlayRadio.this.n_channel < 0)
                    PlayRadio.this.n_channel = size - 1;
                
                PlayRadio.this.changeChannel();
                
            }
            
        });
        
        this.radio_dj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {}
            
        });
        
        this.radio_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                View view = LayoutInflater.from(PlayRadio.this).inflate(R.layout.radiodesc, null);
                TextView txt_desc = (TextView)view.findViewById(R.id.radio_desc_more); 
                
                String description = PlayRadio.this.channel.getAttribute("description");
                
                if(description.length() > 37)
                    description = description.substring(0, 37);
                
                txt_desc.setText(description);
                Dialog radio_desc = Utils.createSimpleDialog(PlayRadio.this, view, PlayRadio.this.channel.getAttribute("title"));
                radio_desc.show();
            }
            
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.playradiomenu, menu);
        
        return true;
    }
    
    protected void cleanExit() {
        super.cleanExit();
        this.finish();
    }
    
    private void populateRadioList() {
    	
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();   

    	
        ArrayList <Song> songs = this.channel.getHistory().getSongs();

        for( int i = 0; i < songs.size() ; i++ )
        {
            HashMap<String,Object> temp = new HashMap<String,Object>();
            temp.put("song_time",  songs.get(i).getTime());
            temp.put("song_auth",  songs.get(i).getAuthor());
            temp.put("song_title", songs.get(i).getTitle());
            temp.put("song", songs.get(i));

            list.add(temp);
        }
		
        SpecialAdapter adapter_songs = new SpecialAdapter(this,list,
        	    R.layout.list_view_row_songs,
        	    new String[] {"song_time", "song_auth","song_title"},
        	    new int[] { R.id.song_time, R.id.song_auth, R.id.song_title}
        );
        
        this.setListAdapter(adapter_songs);
        adapter_songs.notifyDataSetChanged();
    }

    public void updateMe() {
        this.populateRadioList();
    }

    @Override
    public void updateChannel(Channel currentCh) {
        this.populateRadioList();
        
    }
    
    public void openBrowser(Uri uri)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}