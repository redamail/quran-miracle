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
	Button selectButton;
	List<SearchResult> searchResultList;
	SearchResultAdapter adapter ;
	DragSortListView searchResultListView;
	int searchMode;
	String searchText;
	
	private DragSortListView.DropListener onDrop =
	new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to)
		{
			SearchResult item = adapter.getItem(from);
			adapter.remove(item);
			adapter.insert(item, to);
		}
	};

	private DragSortListView.RemoveListener onRemove = 
	new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which)
		{
			adapter.remove(adapter.getItem(which));
			updateHeader();
		}
	};
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		searchText = intent.getStringExtra(Constant.CALL_PARAM_SEARCH_TEXT);
		searchMode = intent.getIntExtra(Constant.CALL_PARAM_SEARCH_MODE, Constant.QURAN_SEARCH_MODE_AYAT);
		boolean searchComplete = intent.getBooleanExtra(Constant.CALL_PARAM_SEARCH_COMPLETE, false);
		boolean searchConsiderAlif = intent.getBooleanExtra(Constant.CALL_PARAM_SEARCH_CONSIDER_ALIF, false);
		searchResultList = new ArrayList<SearchResult>();
        setContentView(R.layout.search_res_layout);

		searchResultListView = (DragSortListView) findViewById(R.id.search_res_lis_list);

		searchResultListView.setDropListener(onDrop);
		searchResultListView.setRemoveListener(onRemove);

		searchResultHeader = (TextView) findViewById(R.id.search_res_lis_header);

		//search in quran
		if (searchMode == Constant.QURAN_SEARCH_MODE_AYAT)
		{
			searchAyat(searchText, searchComplete, searchConsiderAlif);
		}
		if (searchMode == Constant.QURAN_SEARCH_MODE_KALIMAT)
		{
			searchKalimat(searchText, searchComplete, searchConsiderAlif);
		}

		updateHeader();
		selectButton = (Button)findViewById(R.id.search_res_lis_select_button);
		selectButton.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View p1)
				{
					selectResults();
				}
		});
		
		adapter = new SearchResultAdapter(this, searchResultList);
		searchResultListView.setAdapter(adapter);
    }
	
	public void updateHeader(){
		searchResultHeader.setText(getHeaderText());
		searchResultHeader.invalidate();
	}
	
	public String getHeaderText(){
		
		String header = "";
		
		if (searchMode == Constant.QURAN_SEARCH_MODE_AYAT)
		{
			header = "عدد الآيات الموجودة";
		}
		if (searchMode == Constant.QURAN_SEARCH_MODE_KALIMAT)
		{
			header = "عدد الكلمات الموجودة";
		}

		header += " " + searchResultList.size();
		header += " لبحث " + searchText;
		return header;
	}

	public void searchAyat(String text, boolean searchComplete, boolean considerAlif)
	{
		searchResultList.clear();
		searchResultList= new ArrayList<SearchResult>();
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
				searchResultList.add(new SearchResult(cur.getInt(1), cur.getInt(2), cur.getString(3), ayahinfo, 0 , null));
				//if(selectAyahKalima) Selection.addSelection(cur.getInt(2), cur.getInt(3), cur.getInt(6));
				cur.moveToNext();
			}
		}
	}
	
	public void selectResults(){
		for(SearchResult result : searchResultList){
			Selection.addSelection(result.getIdSourat(), result.getIdAyah(), result.getIdKalima());
		}
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
		searchResultList.clear();
		//if (selectAyahKalima) Selection.cancelSelection();
		text = text.replace("*", "%");
		String query = "select distinct ayah2.id, ayah2.id_sourat, ayah2.id_ayah, ayah2.ayah2, sourat.sourat, kalima.id_kalima, kalima.kalima ";
		query += "from ayah2, sourat, kalima where (ayah2.id_sourat = sourat.id_sourat and ayah2.id_sourat=kalima.id_sourat and ayah2.id_ayah=kalima.id_ayah) ";

		if (searchComplete)
		{
			String[] split = text.split(" ");
			String textplus = "";
			String textmoins = "";
			for (int i = 0; i < split.length; i++)
			{	
				if (split[i].startsWith("+"))
				{
					split[i] = split[i].replace("+", "");
					textplus += " or " + ((!considerAlif) ? getFiltre("kalima"): "kalima") + " like '" + ((considerAlif) ?convertLetterA(split[i]): split[i]) + "'";
				}
				else if (split[i].startsWith("-"))
				{
					split[i] = split[i].replace("-", "");
					textmoins += " and " + ((!considerAlif) ? getFiltre("kalima"): "kalima") + " not like '" + ((considerAlif) ?convertLetterA(split[i]): split[i]) + "'";	
				}
				else
				{
					textplus += " or " + ((!considerAlif) ? getFiltre("kalima"): "kalima") + " like '" + ((considerAlif) ?convertLetterA(split[i]): split[i]) + "'";
				}
			}
			query += " and (1<>1 " + textplus + ") and (1=1 " + textmoins + ")";
		}
		else
		{
			String[] split = text.split(" ");
			String textplus = "";
			String textmoins = "";
			for (int i = 0; i < split.length; i++)
			{	
				if (split[i].startsWith("+"))
				{
					split[i] = split[i].replace("+", "");
					textplus += " or " + ((!considerAlif) ? getFiltre("kalima"): "kalima") + " like '%" + ((considerAlif) ?convertLetterA(split[i]): split[i]) + "%'";
				}
				else if (split[i].startsWith("-"))
				{
					split[i] = split[i].replace("-", "");
					textmoins += " and " + ((!considerAlif) ? getFiltre("kalima"): "kalima") + " not like '%" + ((considerAlif) ?convertLetterA(split[i]): split[i]) + "%'";	
				}
				else
				{
					textplus += " or " + ((!considerAlif) ? getFiltre("kalima"): "kalima") + " like '%" + ((considerAlif) ?convertLetterA(split[i]): split[i]) + "%'";
				}
			}
			query += " and (1<>1 " + textplus + ") and (1=1 " + textmoins + ")";
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
				String ayah = cur.getString(6) + " | " + cur.getString(3).replace(cur.getString(6),"<b>"+cur.getString(6)+"</b>");
				this.searchResultList.add(new SearchResult(cur.getInt(1), cur.getInt(2), ayah, ayahinfo, cur.getInt(5),  cur.getString(6)));
				cur.moveToNext();
			}
		}
	}


}
