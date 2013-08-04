package com.quran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.graphics.Color;

public class ShowNumValAdapter extends BaseAdapter
{
	private Context context;
	private final List<String[]> values;
	
	public ShowNumValAdapter(Context context, List<String[]> values)
	{
		this.context = context;
		this.values = values;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		
		if (convertView == null)
		{
			
			gridView = new View(context);
			
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.show_num_val_layout, null);
			LinearLayout root = (LinearLayout)gridView.findViewById(R.id.show_num_val_layout_root);
			
			
			if((position%2) == 0){
				root.setBackgroundColor(Color.argb(200, 0, 224, 44));
			}
			// set value into textview
			TextView sqnumvaltv = new TextView(context);
			sqnumvaltv.setText(values.get(position)[0]);
			sqnumvaltv.setTextSize(20);
			sqnumvaltv.setWidth((int)Config.getQuranPageWidth()/2);
			sqnumvaltv.setTextDirection(TextView.TEXT_DIRECTION_RTL);
			sqnumvaltv.setGravity(Gravity.RIGHT);
			root.addView(sqnumvaltv);
			// set value into textview
			TextView sqharftv =  new TextView(context);
			sqharftv.setText(values.get(position)[1]);
			sqharftv.setTextSize(20);
			sqharftv.setWidth((int)Config.getQuranPageWidth()/2);
			root.addView(sqharftv);
		}
		else
		{
			gridView = convertView;
		}
		
		return gridView;
	}
	
	@Override
	public int getCount()
	{
		return values.size();
	}
	
	@Override
	public String[] getItem(int position) {
        return values.get(position);
    }
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}	
}
