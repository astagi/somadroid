package org.as.somadroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class RadioWidget extends LinearLayout {

    private TextView song_author;
    private TextView song_title;
    private Button play_button;
	private Channel channel_to_play;
	
    public RadioWidget(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.radiowidget, this);
        this.play_button = (Button)this.findViewById(R.id.play_button);
        this.song_author = (TextView)this.findViewById(R.id.current_song_auth);
        this.song_title = (TextView)this.findViewById(R.id.current_song_title);
        
        this.play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                if (GlobalSpace.radio.isPlaying())
                {
                    RadioWidget.this.play_button.setText("Play");
                    RadioWidget.this.offMe();
                    GlobalSpace.radio.stop();   
                }
                else
                {
                	RadioWidget.this.play_button.setText("Stop");
                    GlobalSpace.radio.setChannel(RadioWidget.this.channel_to_play);
                    RadioWidget.this.updateMe();
                    GlobalSpace.radio.play();
                }
            }
        });
        
        this.initialize();
    }

    public void setChannelToPlay(Channel channel_to_play)
    {
    	this.channel_to_play = channel_to_play;
    }
    
    public void updateMe()
    {
        if(GlobalSpace.radio.getChannel() == null)
            return;
    	
        this.song_author.setText(GlobalSpace.radio.getChannel().getAttribute("lastPlaying"));
        this.song_title.setText(GlobalSpace.radio.getChannel().getAttribute("lastPlaying"));
    }
    
    private void offMe()
    {
        this.song_author.setText("----");
        this.song_title.setText("----");
    }
    
    private void initialize(){
    	
        this.updateMe();
    	
	    if (GlobalSpace.radio.isPlaying())
        {
	        this.play_button.setText("Stop");
	    }
	    else
	    {
	        this.play_button.setText("Play");
	    }
    }


}
