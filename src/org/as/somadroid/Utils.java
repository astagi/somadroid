
package org.as.somadroid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Utils {
	
    private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder db = null;
	
    public static String formatCalendarTime(int hour, int minutes)
    {
        String time = "";
        if (hour < 10)
            time += "0" + hour;
        else
            time += hour;
		
        time += ":";
		
        if (minutes < 10)
            time += "0" + minutes;
        else
            time += minutes;
		
        return time;
    }
	
    public static boolean existsImageInPhone(String filename)
    {
        FileInputStream fos = null;
        
        try {
            fos = Somadroid.app_context().openFileInput(filename);
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
    public static String saveImageToPhone(Bitmap bm, String filename)
    {

        FileOutputStream fos = null;
		
        try {
            fos = Somadroid.app_context().openFileOutput(filename, Context.MODE_PRIVATE);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return Somadroid.app_context().getFilesDir() + "/" + filename;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "";
		
    }
    
    public static String getAbsImagePath(String filename)
    {
        return Somadroid.app_context().getFilesDir() + "/" + filename;
    }
	
    public static Bitmap loadBitmap(String url) 
    {	
        Bitmap bmImg = BitmapFactory.decodeStream(StreamFromUrl(url));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmImg, 90, 90, false);
        return resizedBitmap;
    }
    
    public static Bitmap loadBitmapFromFile(String localName) {
        
        FileInputStream fos = null;
        
        try{   
            fos = Somadroid.app_context().openFileInput(localName);
        } catch (FileNotFoundException e) {    
            e.printStackTrace();
            return null;
        }

        Bitmap bmImg = BitmapFactory.decodeStream(fos);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmImg, 90, 90, false);
        
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return resizedBitmap;
    }
    
    public static AlertDialog createSimpleDialog(Context ct, View view, String title)
    {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        
        builder.setView(view);
        builder.setTitle(title);
        builder.setCancelable(true);
        
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        
        builder.setPositiveButton("Ok", dialogClickListener);
        return builder.create();
    }
	
    public static InputStream StreamFromUrl(String exturl)
    {
		
        URL url;
        try {
            url = new URL(exturl);
            HttpURLConnection urlConnection;
            urlConnection = (HttpURLConnection) url.openConnection();
            return urlConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
        
    }

    
    public static Uri songInAmazon(Song song)
    {
        return Uri.parse("http://somafm.com/buy/multibuy.cgi?mode=amazon&title="
                + song.getTitle() + "&artist=" + song.getAuthor());
    }
    
    public static Uri songInGoogle(Song song)
    {
        String search_str = song.getTitle() + " " + song.getAuthor();
        return Uri.parse("http://www.google.com/search?q=" + search_str);
    }
	
    public static Document XMLFromUrl(String xmlurl)
    {
        Document doc;
        try {
            dbf.setCoalescing(true);
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
		
        try {
            InputStream chxml = Utils.StreamFromUrl(xmlurl);
            if(chxml!=null){
                doc = db.parse(chxml);
                doc.getDocumentElement().normalize();
                return doc;
            }else
                return null;
    		
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
