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
 * Module name: RadioNotification
 * Description: the notificator for Somadroid
 * Date: 06/05/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

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
        this.notification.flags |= this.notification.FLAG_NO_CLEAR;
        Intent notificationIntent = new Intent(this.current_activity, this.current_activity.getClass());
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
