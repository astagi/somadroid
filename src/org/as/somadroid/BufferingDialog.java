package org.as.somadroid;

import android.app.Activity;
import android.app.ProgressDialog;

public class BufferingDialog {

    ProgressDialog dialog;
    Activity activity;
    
    public BufferingDialog(Activity activity)
    {
        this.activity = activity;
    }
    
    public void start()
    {
        dialog = ProgressDialog.show(BufferingDialog.this.activity, "", "Buffering...", true);
    }
    
    public void stop()
    {
        if(dialog != null)
            dialog.dismiss();
    }
    
}
