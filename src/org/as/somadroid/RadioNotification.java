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
    private PlayRadio current_activity;
	
    public RadioNotification(PlayRadio current_activity)
    {
        String ns = Context.NOTIFICATION_SERVICE;
        this.current_activity = current_activity;
        this.mNotificationManager = (NotificationManager) this.current_activity.getSystemService(ns);
        
        int icon = R.drawable.notification_icon;
        CharSequence tickerText = "Hello";
        long when = System.currentTimeMillis();

        this.notification = new Notification(icon, tickerText, when);
        
        Intent notificationIntent = new Intent(this.current_activity, PlayRadio.class);
        this.contentIntent = PendingIntent.getActivity(this.current_activity, 0, notificationIntent, 0);
    }

    public void notifyPlay(Channel currentCh) {
        this.updateNotification(currentCh.getAttribute("lastPlaying"), currentCh.getAttribute("lastPlaying"));
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
