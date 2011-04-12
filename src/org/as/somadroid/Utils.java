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
 * Module name: Utils
 * Date: 12/04/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.as.somadroid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
	
	public static boolean saveImageToSdCard(Bitmap bm,String filename)
	{

    	try {
    		File file = new File(Consts.image_dir, filename);
	    	FileOutputStream fos = new FileOutputStream( file );
	    	
	    	bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
	    	fos.flush();
	    	fos.close();
	    
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}
	
	public static String saveImageToPhone(Bitmap bm,String filename)
	{

		FileOutputStream fos = null;
		
		try
		{
			fos = Somadroid.app_context().openFileOutput(filename, Context.MODE_PRIVATE);
			bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			return Somadroid.app_context().getFilesDir() + "/" + filename;
		}
		catch(Exception ex)
		{
	 		return "";					
		}
		
	}
	
	public static Bitmap loadBitmap(String url) 
	{
		
	   Bitmap bmImg = BitmapFactory.decodeStream(StreamFromUrl(url));
	   Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmImg, 80, 80, false);
	   return resizedBitmap;

	}
	
	public static InputStream StreamFromUrl(String exturl)
	{
		
        try {
        	URL url = new URL(exturl);
        	HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        	urlConnection.setConnectTimeout(2000);
        	url.openConnection();
			return urlConnection.getInputStream();

        } catch (Exception ex) {
        	return null;
        }
        
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
