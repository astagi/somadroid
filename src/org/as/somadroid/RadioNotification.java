package org.as.somadroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class RadioNotification {
	
    private NotificationManager mNotificationManager;

    private Notification notification;

    private PendingIntent contentIntent;
    private final int NOTIFICATION_ID = 1;
    private Context current_activity;
	
    public RadioNotification(Context current_activity)
    {
        String ns = Context.NOTIFICATION_SERVICE;
        this.current_activity = current_activity;
        this.mNotificationManager = (NotificationManager) this.current_activity.getSystemService(ns);
        
        int icon = R.drawable.notification_icon;
        CharSequence tickerText = "SomaFm is playing";
        long when = System.currentTimeMillis();

        this.notification = new Notification(icon, tickerText, when);
        
        Intent notificationIntent = new Intent(this.current_activity, PlayRadio.class);
        this.contentIntent = PendingIntent.getActivity(this.current_activity, 0, notificationIntent, 0);
    }

    public void notifyPlay(Song current_song) {
        this.updateNotification(current_song.getAuthor(), current_song.getTitle());
    }

    public void notifyStop() {
        this.deleteNotification();
    }
    
    protected void updateNotification(String contentTitle, String contentText){
        this.notification.setLatestEventInfo(this.current_activity.getApplicationContext(), contentTitle, contentText, contentIntent);
        this.mNotificationManager.notify(NOTIFICATION_ID, this.notification);
    }
    
    protected void deleteNotification(){
        this.mNotificationManager.cancel(NOTIFICATION_ID);
    }

}
