package org.as.somadroid;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

public class SomaActivity extends ListActivity{
    
    protected void showAbout() {
        Dialog about = new Dialog(this);
        about.setContentView(R.layout.about);  
        about.setTitle("Somadroid");
        about.setCancelable(true);
        about.show();
    }
    
    protected void cleanExit() {
        ((SomadroidApp) this.getApplication()).radio_controller.stop();
    }
    
    public void lockOrtientation(){
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    
    public void unlockOrtientation(){
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR); 
    }
    
}
