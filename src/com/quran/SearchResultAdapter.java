package com.quran;

import android.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.quran.*;
import java.util.*;
import android.text.*;

public class SearchResultAdapter extends ArrayAdapter<SearchResult>
{

	LayoutInflater inflater;

	public SearchResultAdapter(Context context, List<SearchResult> lsr)
	{
		super(context, R.layout.selection_dialog_item_layout, R.id.selection_dialog_item_textview_ayah, lsr);
		inflater = LayoutInflater.from(context);
	}

	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		final SearchResult result = getItem(position);

		holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.search_res_item_layout, null);
		holder.idAyah = (TextView) convertView.findViewById(R.id.search_res_ayah_id);
		holder.ayah = (TextView)convertView.findViewById(R.id.search_res_ayah_text);
		convertView.setTag(holder);
		
		holder.idAyah.setText(result.getAyahinfo());
		holder.ayah.setText(Html.fromHtml( result.getAyah()));
		holder.ayah.setOnLongClickListener(new TextView.OnLongClickListener(){
				public boolean onLongClick(View p1)
				{
					Intent intent = new Intent(p1.getContext(), PageActivity.class);
					int idayah = result.getIdAyah();
					int idsourat = result.getIdSourat();
					intent.putExtra(Constant.CALL_PARAM_TYPE, Constant.PAGE_CALL_TYPE_AYAH);
					intent.putExtra(Constant.CALL_PARAM_NUM_AYAH, idayah);
					intent.putExtra(Constant.CALL_PARAM_NUM_SOURAT, idsourat);
					p1.getContext().startActivity(intent);
					return false;
				}
			});
		return convertView;
	}

	private class ViewHolder
	{	
		TextView idAyah ;
		TextView ayah;
	}
}
