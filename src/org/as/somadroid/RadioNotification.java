
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
        CharSequence tickerText = current_activity.getString(R.string.notification_title);
        long when = System.currentTimeMillis();

        this.notification = new Notification(icon, tickerText, when);
        this.notification.flags |= this.notification.FLAG_NO_CLEAR;
        Intent notificationIntent = new Intent(this.current_activity, this.current_activity.getClass());
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.contentIntent = PendingIntent.getActivity(this.current_activity, 0, notificationIntent, 0);
    }

    public void notifyPlay(String radioTitle, Song current_song) {
        this.updateNotification(radioTitle, current_song.getAuthor(), current_song.getTitle());
    }

    public void notifyStop() {
        this.deleteNotification();
    }
    
    protected void updateNotification(String radioTitle, String contentTitle, String contentText){
        String contentNowPlaying = contentTitle + " - " + contentText;
        this.notification.setLatestEventInfo(this.current_activity.getApplicationContext(), radioTitle, contentNowPlaying, contentIntent);
        this.mNotificationManager.notify(NOTIFICATION_ID, this.notification);
    }
    
    protected void deleteNotification(){
        this.mNotificationManager.cancel(NOTIFICATION_ID);
    }

}
