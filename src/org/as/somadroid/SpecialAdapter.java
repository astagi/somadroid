
package org.as.somadroid;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class SpecialAdapter extends SimpleAdapter {
	
    private int[] colors = new int[] { 0xFF8cba18, 0xFF5e7d08 };

    public SpecialAdapter(Context context, ArrayList<HashMap<String, Object>> list, int resource, String[] from, int[] to) {
        super(context, list, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}