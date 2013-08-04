package com.quran;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;
import android.util.Log;
import java.util.List;
import android.content.Context;

public class SearchResultActivity extends Activity
{

	TextView searchResultHeader;
	ListView searchResultListView;
	List<SearchResult> searchResultList;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String searchText = intent.getStringExtra(Constant.CALL_PARAM_SEARCH_TEXT);
		int searchMode = intent.getIntExtra(Constant.CALL_PARAM_SEARCH_MODE, Constant.QURAN_SEARCH_MODE_AYAT);
		boolean searchComplete = intent.getBooleanExtra(Constant.CALL_PARAM_SEARCH_COMPLETE, false);
		boolean searchConsiderAlif = intent.getBooleanExtra(Constant.CALL_PARAM_SEARCH_CONSIDER_ALIF, false);
		boolean selectAyahKalima = intent.getBooleanExtra(Constant.CALL_PARAM_SEARCH_SELECT_AYAH_KALIMA, false);
		
		searchResultList = new ArrayList<SearchResult>();

        setContentView(R.layout.search_res_list_layout);

		searchResultListView = (ListView) findViewById(R.id.search_res_lis_list);
		searchResultHeader = (TextView) findViewById(R.id.search_res_lis_header);

		String header = "";

		//search in quran
		if (searchMode == Constant.QURAN_SEARCH_MODE_AYAT)
		{

			searchResultList  = searchAyat(searchText, searchComplete, searchConsiderAlif );
			header = "عدد الآيات الموجودة";
		}
		if (searchMode == Constant.QURAN_SEARCH_MODE_KALIMAT)
		{

			searchKalimat(searchText, searchComplete, searchConsiderAlif );
			header = "عدد الكلمات الموجودة";
		}

		header += " " + searchResultList.size();

		searchResultHeader.setText(header);

		SearchResultAdapter adapter = new SearchResultAdapter(this, searchResultList);
		searchResultListView.setAdapter(adapter);
		
		if(selectAyahKalima ){
			
		}
    }

	
	public void selectAyah()
	{
		for(SearchResult result : searchResultList){
			Selection.addSelection(Integer.decode(result.getIdSourat()), Integer.decode(result.getIdSourat()));
		}
	}
	
	public List<SearchResult> searchAyat(String text, boolean searchComplete, boolean considerAlif)
	{

		List<SearchResult> searchResultList= new ArrayList<SearchResult>();

		String query = "select ayah2.id, ayah2.id_sourat, id_ayah, ayah, sourat ";
		query += "from ayah2, sourat where ayah2.id_sourat = sourat.id_sourat ";
		query += "and ";
		if (searchComplete)
		{
			query += ((!considerAlif) ? getFiltre("ayah2"): "ayah2") + " like '" + ((considerAlif) ? convertLetterA(text): text) + "'";
		}
		else
		{
			query += ((!considerAlif) ? getFiltre("ayah2"): "ayah2") + " like '%" + ((considerAlif) ? convertLetterA(text): text) + "%'";
		}

		Cursor cur = Connection.db.rawQuery(query, new String [] {});

		int numRes = cur.getCount();

		if (numRes > 0)
		{
			cur.moveToFirst();
			while (cur.isAfterLast() == false)
			{
				String ayahinfo ="سورة";
				ayahinfo += " " + cur.getString(4) + " ";
				ayahinfo += "آية";
				ayahinfo += " " + cur.getString(2);
				searchResultList.add(new SearchResult(cur.getString(1), cur.getString(2), cur.getString(3), ayahinfo));
				cur.moveToNext();
			}
		}
		
		return searchResultList;
	}

	public String getFiltre(String champ)
	{
		String a = "أ";
		String b = "آ";
		String c = "إ";
		String d = "ٱ";
		String x = "ا";

		String filtre = "replace(replace(replace(replace(" + champ + ",'" + d + "','" + x + "'),'" + a + "','" + x + "'),'" + b + "','" + x + "'),'" + c + "','" + x + "')";

		return filtre;
	}

	public String convertLetterA(String text)
	{
		String a = "أ";
		String b = "آ";
		String c = "إ";
		String d = "ٱ";
		String x = "ا";

		String convText = text.replace(a, x).replace(b, x).replace(c, x).replace(d, x);

		return convText;
	}



	public void searchKalimat(String text, boolean searchComplete, boolean considerAlif)
	{

		this.searchResultList.clear();

		text = text.replace("*", "%");
		String query = "select distinct ayah2.id, ayah2.id_sourat, ayah2.id_ayah, ayah2.ayah2, sourat.sourat ";
		query += "from ayah2, sourat, kalima where ayah2.id_sourat = sourat.id_sourat and ayah2.id_sourat=kalima.id_sourat and ayah2.id_ayah=kalima.id_ayah and ";
		
		if (searchComplete)
		{
			query += ((!considerAlif) ? getFiltre("kalima"): "kalima") + " like '" + ((considerAlif) ?convertLetterA(text): text) + "'";
		}
		else
		{
			query += ((!considerAlif) ? getFiltre("kalima"): "kalima") + " like '%" + ((considerAlif) ?convertLetterA(text): text) + "%'";
		}
		
		Cursor cur = Connection.db.rawQuery(query, new String [] {});

		int numRes = cur.getCount();

		if (numRes > 0)
		{
			cur.moveToFirst();
			while (cur.isAfterLast() == false)
			{
				String ayahinfo ="سورة";
				ayahinfo += " " + cur.getString(4) + " ";
				ayahinfo += "آية";
				ayahinfo += " " + cur.getString(2);
				this.searchResultList.add(new SearchResult(cur.getString(1), cur.getString(2), cur.getString(3), ayahinfo));
				cur.moveToNext();
			}
		}
	}


	public class SearchResultAdapter extends BaseAdapter
	{

		List<SearchResult> lsr;

		LayoutInflater inflater;

		public SearchResultAdapter(Context context, List<SearchResult> lsr)
		{
			inflater = LayoutInflater.from(context);
			this.lsr = lsr;
		}

		public int getCount()
		{
			// TODO: Implement this method
			return lsr.size();
		}

		public Object getItem(int p1)
		{
			// TODO: Implement this method
			return lsr.get(p1);
		}

		public long getItemId(int p1)
		{
			// TODO: Implement this method
			return p1;
		}

		public View getView(final int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.search_res_layout, null);
				holder.idAyah = (TextView) convertView.findViewById(R.id.search_res_ayah_id);
				holder.ayah = (TextView)convertView.findViewById(R.id.search_res_ayah_text);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.idAyah.setText(lsr.get(position).getAyahinfo());
			holder.ayah.setText(lsr.get(position).getAyah());
			holder.ayah.setOnClickListener(new TextView.OnClickListener(){
					public void onClick(View p1)
					{

						Intent intent = new Intent(p1.getContext(), PageActivity.class);
						int idayah = Integer.decode(lsr.get(position).getIdAyah());
						int idsourat = Integer.decode(lsr.get(position).getIdSourat());
						intent.putExtra(Constant.CALL_PARAM_TYPE, Constant.PAGE_CALL_TYPE_AYAH);
						//System.out.println(idayah);
						//System.out.println(idsourat);
						intent.putExtra(Constant.CALL_PARAM_NUM_AYAH, idayah);
						intent.putExtra(Constant.CALL_PARAM_NUM_SOURAT, idsourat);

						p1.getContext().startActivity(intent);
						// TODO: Implement this method
					}
				});
			// TODO: Implement this method
			return convertView;
		}

		private class ViewHolder
		{	
			TextView idAyah ;
			TextView ayah;
		}
	}
}
