package org.as.somadroid;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.SimpleAdapter;

public class PrepareAdapter extends AsyncTask<Void,Void,SimpleAdapter > {
    
    private ProgressDialog dialog;
    private boolean output_visible;
    private Somadroid soma;
    
    
    public PrepareAdapter(Somadroid soma, boolean output_visible)
    {
        super();
        this.soma = soma;
        this.output_visible = output_visible;
    }
    
    protected void onFail()
    {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        soma.retryFeed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        soma.cleanExit();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(soma);
        builder.setMessage("Connection problems..Do you want to retry?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", dialogClickListener);
        builder.setNegativeButton("No", dialogClickListener).show();
    }
    
    @Override
    protected void onPreExecute() {

        if(!this.output_visible)
            return;
        
        dialog = new ProgressDialog(soma);
        dialog.setMessage("Loading stations...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        
    }

    @Override
    protected SimpleAdapter doInBackground(Void... params) {
        return soma.getNewAdapter();
    }

    protected void onPostExecute(SimpleAdapter new_adapter) {
        
        if(new_adapter==null)
        {
            if(this.output_visible)
            {
                dialog.dismiss();
                this.onFail();
                return;
            }
        }
        
        else
        {
            //Get the top position from the first visible element
            int idx = soma.getListView().getFirstVisiblePosition();
            View vfirst = soma.getListView().getChildAt(0);
            int pos = 0;
            
            if (vfirst != null) 
                pos = vfirst.getTop();
           
            //Set list infos
            soma.setAdapterAndNotify(new_adapter);
            //Restore the position
            soma.getListView().setSelectionFromTop(idx, pos);
            
            if(this.output_visible)
                dialog.dismiss();
            

        }
        
        soma.doTheAutoRefresh(Consts.REFRESH_DELAY);

    }
}
