package org.as.somadroid;

import java.util.ArrayList;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;

public class RadioController implements Controller {
    
    private Radio radio;
    private MediaPlayer player = new MediaPlayer();
    private ArrayList <RadioView> radioview = new ArrayList <RadioView>();
    Handler handler = new Handler();

    
    public RadioController(Radio radio)
    {
        this.radio = radio;
    }
    
    public void play(Channel ch)
    {
        this.radio.setChannel(ch);
        this.play();
    }
    
    public void play()
    {
        radio.setIsPlaying(true);

        
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if(player == null)
                    player = MediaPlayer.create(Somadroid.app_context(), radio.getUri());
                else
                {
                    try {
                        player.stop();
                        player = MediaPlayer.create(Somadroid.app_context(), radio.getUri());
                    } catch (Exception e) 
                    {
                        radio.setIsPlaying(false);
                    }
                }
                
                player.setOnPreparedListener(new OnPreparedListener() { 
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        RadioController.this.inform();
                    }
                });
            }
                 
         }, 100);
        
    }
    
    public void stop()
    {
        player.stop();
        radio.setIsPlaying(false);
        this.inform();
    }

    public void attach(RadioView obsEl) {
        this.radioview.add(obsEl);       
        obsEl.updateStatus(radio.isPlaying(), radio.getChannel());
    }

    public void detach(RadioView obsEl) {
        this.radioview.remove(obsEl);
    }

    @Override
    public void inform() {
        for(int i = 0; i < this.radioview.size(); i++)
            this.radioview.get(i).updateStatus(radio.isPlaying(), radio.getChannel());
    }

    public boolean isPlaying() {
        return this.radio.isPlaying();
    }

}
