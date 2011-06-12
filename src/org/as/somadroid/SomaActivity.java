package org.as.somadroid;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

public class SomaActivity extends ListActivity{
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        ((SomadroidApp)this.getApplication()).addActivity(this);
    }
    
    protected void showAbout() {
        View view = LayoutInflater.from(this).inflate(R.layout.about, null);
        Dialog about = Utils.createSimpleDialog(this, view, this.getString(R.string.app_name));
        about.show();
    }
    
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ((SomadroidApp)this.getApplication()).removeActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }*/
    
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
