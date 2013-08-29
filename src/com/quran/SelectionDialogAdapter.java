
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.support.v4.view.ViewPager;

public class SelectionDialogAdapter extends ArrayAdapter<Select>
{
	LayoutInflater mInflater;
	PageActivity pageActivity;;

	public SelectionDialogAdapter(List<Select> selects, PageActivity pageActivity)
	{
		super(pageActivity, R.layout.selection_dialog_item_layout, R.id.selection_dialog_item_textview_ayah, selects);
		this.pageActivity = pageActivity;
		mInflater = LayoutInflater.from(this.getContext());
	}

	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = new ViewHolder();
		Select sel = getItem(position);

		if (sel.selectionType != Selection.SELECTION_TYPE_SEPARATOR)
		{
			convertView = mInflater.inflate(R.layout.selection_dialog_item_layout, null);
			convertView.setTag(holder);
		}
		else
		{
			convertView  = mInflater.inflate(R.layout.section_div, parent, false);
		}

		if (sel.selectionType != Selection.SELECTION_TYPE_SEPARATOR)
		{	
			holder.ayah = (TextView)convertView.findViewById(R.id.selection_dialog_item_textview_ayah);
			holder.numval = (TextView)convertView.findViewById(R.id.selection_dialog_item_textview_numval);
			holder.sumkal = (TextView)convertView.findViewById(R.id.selection_dialog_item_textview_sumkal);
			holder.sumhar = (TextView)convertView.findViewById(R.id.selection_dialog_item_textview_sumhar);

			//add view hier
			int numval = Connection.getNumVal(sel.kalimaDeb, sel.kalimaFin);
			int sumkal = Connection.getSumKal(sel.kalimaDeb, sel.kalimaFin);
			int sumhar = Connection.getSumHar(sel.kalimaDeb, sel.kalimaFin);

			String numvalstr = "القيمة العددية : " ;
			numvalstr += numval + " = " + (numval / 19) + " * 19 " + (((numval % 19) > 0) ?" + " + (numval % 19): "");
			String sumkalstr = "عدد الكلمات : " ;
			sumkalstr += sumkal;
			String sumharstr = "عدد حروف الكلمات : " ;
			sumharstr += sumhar;

			String ayah="";

			if (sel.selectionType == Selection.SELECTION_TYPE_AYAH)
			{
				ayah = sel.ayah.getAyah2() + "(" + sel.ayah.getId_ayah() + ") [" + Connection.getSouratNameFromId(sel.ayah.getId_sourat()) + "]";
			}
			else
			if (sel.selectionType == Selection.SELECTION_TYPE_GLYPH)
			{
				ayah = Connection.getKalimateText(sel.kalimaDeb, sel.kalimaFin);
			}

			holder.ayah.setText(ayah);
			holder.ayah.setTextSize(Config.getTextSize());
			holder.numval.setText(numvalstr);
			holder.numval.setTextSize(Config.getTextSize());
			holder.sumkal.setText(sumkalstr);
			holder.sumkal.setTextSize(Config.getTextSize());
			holder.sumhar.setText(sumharstr);
			holder.sumhar.setTextSize(Config.getTextSize());
			holder.ayah.setOnClickListener(new TextView.OnClickListener(){
					public void onClick(View p1)
					{
						int numPage = Connection.getNumPage(getItem(position).kalimaDeb.getId_sourat(), getItem(position).kalimaDeb.getId_ayah());
						showPage2(numPage);
					}
				});

			if (sel.selectionType == Selection.SELECTION_TYPE_AYAH)
			{	
				holder.ayah.setOnLongClickListener(new TextView.OnLongClickListener(){
						public boolean onLongClick(View p1)
						{
							Intent intent = new Intent(p1.getContext(), AyahEditorActivity.class);
							intent.putExtra(Constant.CALL_PARAM_NUM_AYAH, getItem(position).kalimaDeb.getId_ayah());
							intent.putExtra(Constant.CALL_PARAM_NUM_SOURAT, getItem(position).kalimaDeb.getId_sourat());
							p1.getContext().startActivity(intent);
							return false;
						}
					});
			}
		}
		return convertView;
	}

	static class ViewHolder
	{
		TextView ayah;
		TextView numval;
		TextView sumkal;
		TextView sumhar;
    }

	public void showPage(int numPage)
	{
		Intent intent = new Intent(this.getContext(), PageActivity.class);
		intent.putExtra(Constant.CALL_PARAM_NUM_PAGE, numPage);
		intent.putExtra(Constant.CALL_PARAM_TYPE, Constant.PAGE_CALL_TYPE_PAGE);
		this.getContext().startActivity(intent);
	}

	public void showPage2(int numPage)
	{
		int current_item = PageActivity.getPosFromArPage(numPage);
		pageActivity.getViewPager().setCurrentItem(current_item);
	}
}

