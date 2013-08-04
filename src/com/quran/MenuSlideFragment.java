
package com.quran;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.database.Cursor;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.view.MotionEvent;
import android.os.SystemClock;
import android.widget.Toast;
import android.util.Log;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Adapter;
import android.widget.AutoCompleteTextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.CheckBox;
import java.io.*;

public class MenuSlideFragment extends Fragment
{

	int numPage = 0;
	int idSouratSel;
	View itemMenu;
	EditText quickGoText;
	Button quickGoButton;
	Button quickGoButtonAyah;
	ListView listeSouratList;
	EditText searchText;
	Button searchButtonAyat;
	Button searchButtonKalimat;
	Button autocompleteButton;
	List<Sourat> lsr;
	Spinner spsouratlist;
	Spinner spayahlist;
	AutoCompleteTextView autocomplete;
	CheckBox completeSearchCheck;
	CheckBox considerAlifSearchCheck;
	CheckBox selectAyahKalimaCheck;
	
	public MenuSlideFragment(int pos)
	{
		super();
		this.numPage = pos;
		setRetainInstance(true);
	}

	public MenuSlideFragment()
	{
		super();
		setRetainInstance(true);
		System.out.println("Constructeur vide appelé : " + numPage);
		//System.out.println(1/0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		//System.out.println(savedInstanceState);

		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null)
		{
			System.out.println(savedInstanceState.containsKey("numPage"));
			numPage  = savedInstanceState.getInt("numPage");
		}
		else
		{
			//savedInstanceState.putInt("numPage ", numPage);
		}
		
		itemMenu = null;

		if (numPage == MenuActivity.QUICK_GO_PAGE)
		{

			itemMenu = inflater.inflate(R.layout.quick_go_layout, container, false);

			quickGoText = (EditText)itemMenu.findViewById(R.id.quick_go_text);

			quickGoButton = (Button) itemMenu.findViewById(R.id.quick_go_button);

			quickGoButton.setOnClickListener(new Button.OnClickListener(){
					public void onClick(View p1)
					{
						int numpage = Integer.decode(quickGoText.getText().toString());
						showPage(numpage);
					}
				});

			fillSouratList();

			List<String> listsouratstring = new ArrayList<String>();

			for (Sourat sourat : lsr)
			{
				listsouratstring.add(sourat.getId_sourat() + "-" + sourat.getSourat());
			}

			spsouratlist = (Spinner) itemMenu.findViewById(R.id.quick_go_spinner_sourat);
			spsouratlist.setTextDirection(Spinner.TEXT_DIRECTION_RTL);
			ArrayAdapter<String> souratAdapter = new ArrayAdapter<String>(itemMenu.getContext(),
																		  android.R.layout.simple_spinner_item, listsouratstring);
			souratAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spsouratlist.setAdapter(souratAdapter);
			spayahlist = (Spinner) itemMenu.findViewById(R.id.quick_go_spinner_ayah);
			spayahlist.setTextDirection(Spinner.TEXT_DIRECTION_RTL);

			spsouratlist.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					public void onNothingSelected(AdapterView<?> p1)
					{
						// TODO: Implement this method
					}

					public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
					{

						List<String> listayahstring = new ArrayList<String>();
						int max_ayah = Connection.getMaxAyah(pos + 1);
						for (int i = 1; i <= max_ayah; i++)
						{
							listayahstring.add("آية" + " " + i);
						}

						ArrayAdapter<String> ayahAdapter = new ArrayAdapter<String>(itemMenu.getContext(),
																					android.R.layout.simple_spinner_item, listayahstring);
						ayahAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spayahlist.setAdapter(ayahAdapter);

					}		
				});

			quickGoButtonAyah = (Button) itemMenu.findViewById(R.id.quick_go_button_ayah);

			quickGoButtonAyah.setOnClickListener(new Button.OnClickListener(){
					public void onClick(View p1)
					{
						int numpage = Connection.getNumPage(spsouratlist.getSelectedItemPosition() + 1, spayahlist.getSelectedItemPosition() + 1);
						showPage(numpage);
					}
				});

			/*
			 ArrayAdapter<String> autocompleteAdapter = new ArrayAdapter<String>(itemMenu.getContext(),
			 android.R.layout.simple_dropdown_item_1line, listsouratstring);
			 autocomplete = (AutoCompleteTextView) itemMenu.findViewById(R.id.quick_go_autocomplete);
			 autocomplete.setAdapter(autocompleteAdapter );
			 */
			//On récupère le tableau de String créé dans le fichier string.xml
			//String[] tableauString = getResources().getStringArray(R.array.tableau);

			//On récupère l'AutoCompleteTextView que l'on a créé dans le fichier main.xml
			List<String> listsouratautocomplete = new ArrayList<String>();

			for (Sourat sourat : lsr)
			{
				listsouratautocomplete .add(sourat.getSourat());
			}

			final AutoCompleteTextView autoComplete = (AutoCompleteTextView) itemMenu.findViewById(R.id.quick_go_autocomplete);

			//On crée la liste d'autocomplétion à partir de notre tableau de string appelé tableauString
			//android.R.layout.simple_dropdown_item_1line permet de définir le style d'affichage de la liste
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
																	android.R.layout.simple_dropdown_item_1line, listsouratautocomplete);
			//On affecte cette liste d'autocomplétion à notre objet d'autocomplétion
			autoComplete.setAdapter(adapter);

			autoComplete.addTextChangedListener(new TextWatcher(){

					public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
					{
						//System.out.println(p1);
					}

					public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
					{
						//System.out.println(p1);
						int souratId = Connection.getCompleteSouratId(p1.toString());
						//System.out.println(souratId);
						if (souratId > -1)
						{
							spsouratlist.setSelection(souratId);
						}
					}

					public void afterTextChanged(Editable p1)
					{
						//System.out.println(p1);
					}
				});	

		}
		if (numPage == MenuActivity.QURAN_SEARCH_PAGE)
		{

			itemMenu = inflater.inflate(R.layout.quran_search_layout, container, false);

			searchText = (EditText) itemMenu.findViewById(R.id.quran_search_text);

			searchButtonAyat = (Button) itemMenu.findViewById(R.id.quran_search_button_ayat);

			completeSearchCheck = (CheckBox) itemMenu.findViewById(R.id.quran_search_complete_check);

			considerAlifSearchCheck = (CheckBox) itemMenu.findViewById(R.id.quran_search_alif_check);
			
			considerAlifSearchCheck = (CheckBox) itemMenu.findViewById(R.id.quran_search_alif_check);
			
			selectAyahKalimaCheck= (CheckBox) itemMenu.findViewById(R.id.quran_search_select_ayah_kalima);
			
			searchButtonAyat.setOnClickListener(new Button.OnClickListener(){
					public void onClick(View p1)
					{
						String text = searchText.getText().toString();
						if (!text.isEmpty())
						{
							boolean completeSearchChecked = completeSearchCheck.isChecked();
							boolean considerAlifSearchChecked = considerAlifSearchCheck.isChecked();
							boolean selectAyahKalimaChecked = selectAyahKalimaCheck.isChecked();
							search(text, Constant.QURAN_SEARCH_MODE_AYAT, completeSearchChecked, considerAlifSearchChecked, selectAyahKalimaChecked);	
						}
					}
				});

			searchButtonKalimat = (Button) itemMenu.findViewById(R.id.quran_search_button_kalimat);

			searchButtonKalimat.setOnClickListener(new Button.OnClickListener(){
					public void onClick(View p1)
					{
						String text = searchText.getText().toString();
						if (!text.isEmpty())
						{
							boolean completeSearchChecked = completeSearchCheck.isChecked();
							boolean considerAlifSearchChecked = considerAlifSearchCheck.isChecked();
							boolean selectAyahKalimaChecked = selectAyahKalimaCheck.isChecked();
							search(text, Constant.QURAN_SEARCH_MODE_KALIMAT, completeSearchChecked , considerAlifSearchChecked, selectAyahKalimaChecked);	
						}
					}
				});
		}
		if (numPage == MenuActivity.SOURAT_LIST_PAGE)
		{

			itemMenu = inflater.inflate(R.layout.liste_sourat_layout, container, false);

			listeSouratList = (ListView) itemMenu.findViewById(R.id.liste_sourat_list);
			fillSouratList();
			ListSouratAdapter adapter = new ListSouratAdapter(this.getActivity(), lsr);
			listeSouratList.setAdapter(adapter);
			listeSouratList.setOnItemClickListener(new ListView.OnItemClickListener(){

					public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
					{	
						idSouratSel = lsr.get(p3).getId_sourat();
						showSourat();
					}
				});
		}

		return itemMenu;
	}

	public void showSourat()
	{
		Intent intent = new Intent(this.getActivity(), PageActivity.class);
		intent.putExtra(Constant.CALL_PARAM_NUM_SOURAT, idSouratSel);
		intent.putExtra(Constant.CALL_PARAM_TYPE, Constant.PAGE_CALL_TYPE_SOURAT);
		startActivity(intent);
	}

	public void showPage(int numpage)
	{
		Intent intent = new Intent(this.getActivity(), PageActivity.class);
		intent.putExtra(Constant.CALL_PARAM_NUM_PAGE, numpage);
		intent.putExtra(Constant.CALL_PARAM_TYPE, Constant.PAGE_CALL_TYPE_PAGE);
		System.out.println(numpage);
		startActivity(intent);
	}

	public void search(String text, int searchMode, boolean completeSearch, boolean considerAlif, boolean selectAyahKalima)
	{
		Intent intent = new Intent(this.getActivity(), SearchResultActivity.class);
		intent.putExtra(Constant.CALL_PARAM_SEARCH_TEXT, text);
		intent.putExtra(Constant.CALL_PARAM_SEARCH_MODE, searchMode);
		intent.putExtra(Constant.CALL_PARAM_SEARCH_COMPLETE, completeSearch);
		intent.putExtra(Constant.CALL_PARAM_SEARCH_CONSIDER_ALIF, considerAlif);
		intent.putExtra(Constant.CALL_PARAM_SEARCH_SELECT_AYAH_KALIMA, selectAyahKalima);
		
		startActivity(intent);
	}

	public void fillSouratList()
	{

		List<Sourat> listSourat = new ArrayList<Sourat>();

		File file = new File(Constant.QURAN_ADRESS);
		System.out.println(file.getAbsolutePath() +"  "+ file.exists());
		Cursor cur = Connection.db.rawQuery("select id, id_sourat, sourat, num_value, sum_harf, sum_ayah, sum_kalima from sourat", new String [] {});
		cur.moveToFirst();
		while (cur.isAfterLast() == false)
		{
			listSourat.add(new Sourat(cur.getInt(0), cur.getInt(1), cur.getString(2), cur.getInt(3), cur.getInt(4), cur.getInt(5), cur.getInt(6)));
			cur.moveToNext();
		}
		this.lsr = listSourat;
		cur.close();
	}

	public class ListSouratAdapter extends BaseAdapter
	{

		List<Sourat> lsr;

		LayoutInflater inflater;

		public ListSouratAdapter(Context context, List<Sourat> lsr)
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
				convertView = inflater.inflate(R.layout.sourat_layout, null);
				holder.infoSourat = (TextView) convertView.findViewById(R.id.sourat_info);
				holder.sourat = (TextView)convertView.findViewById(R.id.sourat_text);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.infoSourat.setText(lsr.get(position).getInfo());
			holder.sourat.setText(lsr.get(position).getId_sourat() + " - " + lsr.get(position).getSourat());
			return convertView;
		}

		public class ViewHolder
		{	
			TextView infoSourat ;
			TextView sourat;
		}
	}
}
