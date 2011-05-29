package org.as.somadroid;

import android.app.Dialog;
import android.app.ListActivity;

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
    
}
