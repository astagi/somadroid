
package org.as.somadroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class RadioWidget extends LinearLayout implements RadioView, ChannelView {

    private TextView channel_title;
    private TextView song_author;
    private TextView song_title;
    private ImageButton play_button;
	private Channel channel_to_play;
	private int n_channel_to_play;
	private RadioNotification radio_notification;
	private boolean play = false;
	private SomaActivity activity;
	
	
    public RadioWidget(SomaActivity activity) {
        
        super(activity);
        
        this.activity = activity;
        
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.radiowidget, this);
        
        this.play_button = (ImageButton)this.findViewById(R.id.play_button);
        this.channel_title = (TextView)this.findViewById(R.id.current_channel);
        this.song_author = (TextView)this.findViewById(R.id.current_song_auth);
        this.song_title = (TextView)this.findViewById(R.id.current_song_title);

        
        this.radio_notification = new RadioNotification(activity);
        
        this.play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                if (((SomadroidApp)RadioWidget.this.activity.getApplication()).radio_controller.isPlaying())
                {
                    v.setEnabled(false);
                    ((SomadroidApp)RadioWidget.this.activity.getApplication()).radio_controller.stop();
                }
                else
                {
                    v.setEnabled(false);
                    ((SomadroidApp)RadioWidget.this.activity.getApplication()).radio_controller.play(RadioWidget.this.activity, RadioWidget.this.channel_to_play);
                    ((SomadroidApp)RadioWidget.this.activity.getApplication()).setLastChSeen(n_channel_to_play);
                }
            }
        });
        
        ((SomadroidApp)RadioWidget.this.activity.getApplication()).radio_controller.attach(this);
    }


    private void offMe()
    {
        this.channel_title.setText(activity.getString(R.string.off_text));
        this.song_author.setText(activity.getString(R.string.off_text));
        this.song_title.setText(activity.getString(R.string.off_text));
        radio_notification.notifyStop();
    }


    @Override
    public void updateStatus(boolean isPlaying, Channel currentCh) {
        
        this.play = isPlaying;
        
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
        
        RadioWidget.this.play_button.setEnabled(true);
        
    }

    @Override
    public void updateChannel(Channel currentCh) {
        
        if(!this.play)
            return;
        
        Song last = currentCh.getLastSong();
        String radioTitle = currentCh.getAttribute("title");
        this.radio_notification.notifyPlay(radioTitle,last);
        this.channel_title.setText(radioTitle);
        this.song_author.setText(last.getAuthor());
        this.song_title.setText(last.getTitle());
        
    }


    public void setChannelToPlay(Channel channel, int n_channel_to_play) {
        this.channel_to_play = channel;
        this.n_channel_to_play = n_channel_to_play;
    }

}
