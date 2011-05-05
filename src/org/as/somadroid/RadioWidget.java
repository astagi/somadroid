package org.as.somadroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class RadioWidget extends LinearLayout implements RadioView, ChannelView {

    private TextView song_author;
    private TextView song_title;
    private ImageButton play_button;
	private Channel channel_to_play;
	private RadioNotification radio_notification;
	private boolean play = false;
	private Activity activity;
	
    private BufferingDialog buffer_dialog;
	
	
    public RadioWidget(Activity activity) {
        
        super(activity);
        
        this.activity = activity;
        
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.radiowidget, this);
        
        this.play_button = (ImageButton)this.findViewById(R.id.play_button);
        this.song_author = (TextView)this.findViewById(R.id.current_song_auth);
        this.song_title = (TextView)this.findViewById(R.id.current_song_title);

        
        this.radio_notification = new RadioNotification(activity);
        
        this.play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                if (((SomadroidApp)RadioWidget.this.activity.getApplication()).radio_controller.isPlaying())
                {
                    ((SomadroidApp)RadioWidget.this.activity.getApplication()).radio_controller.stop();   
                }
                else
                {
                    RadioWidget.this.buffer_dialog.start();
                    ((SomadroidApp)RadioWidget.this.activity.getApplication()).radio_controller.play(RadioWidget.this.channel_to_play);
                }
            }
        });
        
        this.buffer_dialog = new BufferingDialog(activity);
        ((SomadroidApp)RadioWidget.this.activity.getApplication()).radio_controller.attach(this);
    }


    private void offMe()
    {
        this.song_author.setText("----");
        this.song_title.setText("----");
        radio_notification.notifyStop();
    }


    @Override
    public void updateStatus(boolean isPlaying, Channel currentCh) {
        
        this.play = isPlaying;
        this.buffer_dialog.stop();
        
        if (!isPlaying)
        {
            ((SomadroidApp)RadioWidget.this.activity.getApplication()).channel_factory.removeController(this);
            RadioWidget.this.play_button.setImageResource(R.drawable.play);
            RadioWidget.this.offMe();
        }
        else
        {
            ((SomadroidApp)RadioWidget.this.activity.getApplication()).channel_factory.addChannelAndView(this, currentCh);
            this.updateChannel(currentCh);
            RadioWidget.this.play_button.setImageResource(R.drawable.stop);
        }
        
    }

    @Override
    public void updateChannel(Channel currentCh) {
        
        if(!this.play)
            return;
        
        Song last = currentCh.getLastSong();
        String radioTitle = currentCh.getAttribute("title");
        this.radio_notification.notifyPlay(radioTitle,last);
        this.song_author.setText(last.getAuthor());
        this.song_title.setText(last.getTitle());
        
    }


    public void setChannelToPlay(Channel channel) {
        this.channel_to_play = channel;
    }

}
