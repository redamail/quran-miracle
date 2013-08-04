package com.quran;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.*;

public class PageSlideFragment extends Fragment
{

	Page page;
	Cursor cur;
	Button pageleft;
	Button pageright;
	long touchDownTime;
	int numPage = -1;
	Glyph glyphDebTemp;
	Glyph glyphFinTemp;
	
	int screenOrientation = -1;
	Quran19Miracle quran19miracle;
	PageActivity pageActivity;

	public PageSlideFragment(int pos, PageActivity pageActivity)
	{
		super();
		this.numPage = pos;
		this.pageActivity = pageActivity;
	}

	public PageSlideFragment()
	{
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);

		if (this.numPage == -1)
		{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
			this.numPage = prefs.getInt("quran_current_quran_page", 1);
		}

		quran19miracle = new Quran19Miracle(numPage);
		setRetainInstance(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancrState)
	{	
		this.screenOrientation = getScreenOrientation();
		Config.setORIENTATION_TYPE(this.screenOrientation);

		String numPageStr = (numPage > 99) ?String.valueOf(numPage): (numPage > 9) ?"0" + String.valueOf(numPage): "00" + String.valueOf(numPage);
		Bitmap image = BitmapFactory.decodeFile(Config.getIMAGE_ADRESS()+"page" + numPageStr + ".png");
		image = image.createScaledBitmap(image, (int)Config.getImageWidth(), (int)Config.getImageHeight(), true);

		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.page_layout, null);

		page = (Page) layout.findViewById(R.id.quranpage);

		page.setImage(image);

		page.setFragment(this);

		View.OnTouchListener touchlistener = new View.OnTouchListener(){

			public boolean onTouch(View v, MotionEvent event)
			{

				boolean handled = true;
				int x = (int)event.getX();
				int y = (int)event.getY();
				int action = event.getAction();
				Glyph glyph ;
				switch (action)
				{
					case MotionEvent.ACTION_DOWN:
						touchDownTime = SystemClock.elapsedRealtime();
						break;
					case MotionEvent.ACTION_UP:
						glyph = Connection.getGlyphPage(x, y, numPage);
						//si clic sur glyph
						if (glyph != null)
						{
							//si une selection n'a pas ete commence
							if (!Selection.isSelectionStart())
							{
								//si clic long sur glyph 
								if (SystemClock.elapsedRealtime() - touchDownTime >= 400)
								{
									if(!Selection.removeSelect(glyph)){
										pageActivity.registerForContextMenu(v); 
										pageActivity.openContextMenu(v); 
										pageActivity.unregisterForContextMenu(v);	
									}
									refreshSelection();
								}
								else
								{
									//si clic mi-long
									if ((SystemClock.elapsedRealtime() - touchDownTime) >= 150 && (SystemClock.elapsedRealtime() - touchDownTime) < 400)
									{
										//si clic mi-long sur glyph ayah
										if (glyph.getType() == Glyph.GLYPH_TYPE_AYAH)
										{
											Ayah ayah = Connection.getAyahAyahId(glyph.getSura_number(), glyph.getAyah_number());

											Kalima kalDeb = Connection.getKalimaKalimaId(ayah.getId_sourat(), ayah.getId_ayah(), 1);
											Glyph glyDeb = Connection.getGlyphKalima(kalDeb);
											Selection.startSelect(kalDeb, glyDeb, Selection.SELECTION_TYPE_AYAH); 
											Kalima kalFin = Connection.getMaxKalima(ayah);
											Glyph glyFin = Connection.getGlyphKalima(kalFin);
											Selection.endSelect(kalFin, glyFin, ayah);
											refreshSelection();

										}
										else
										//si clic mi-long sur glyph kalima
										if (glyph.getType() == Glyph.GLYPH_TYPE_KALIMA)
										{
											Kalima kalDeb = Connection.getKalimaKalimaId(glyph.getSura_number(), glyph.getAyah_number(), glyph.getKalima_number());
											Selection.startSelect(kalDeb, glyph, Selection.SELECTION_TYPE_GLYPH);
											refreshSelection();
										}
									}
									//si clic court
									else
									{
										//si clic court sur glyph selectionne
										if (Selection.isSelectionOK() && Selection.isGlyphInSelection(glyph))
										{
											SelectionDialog calc = new SelectionDialog(pageActivity);
											calc.show(getActivity().getFragmentManager(), "selection");
										}
										//si clic court sur glyph non selectionne
										else
										{
											//si clic court sur glyph miracle
											if (Config.isSHOW_QURAN_19_MIRACLE())
											{
												List<Quran19Group> quran19groups = quran19miracle.isGlyphInQuran19Miracle(glyph);
												if (!quran19groups.isEmpty())
												{
													CalculDialog calc = new CalculDialog(pageActivity, quran19groups);
													calc.show(getActivity().getFragmentManager(), "calcul");	
												}
											}
										}
									}
								}
							}
							//si une selection a ete commence
							else
							{
								//si clic sur glyph kalima
								if (glyph.getType() == Glyph.GLYPH_TYPE_KALIMA)
								{
									Selection.endSelect(Connection.getKalimaGlyph(glyph), glyph, null);
									refreshSelection();
								}
							}
						}
						//si clic sur vide
						else
						{
							//si clic long sur vide
							if (SystemClock.elapsedRealtime() - touchDownTime >= 400)
							{
								pageActivity.registerForContextMenu(v); 
								pageActivity.openContextMenu(v); 
								pageActivity.unregisterForContextMenu(v);
							}
						}
						break;
				}
				return handled;
			}
		};

		page.setOnTouchListener(touchlistener);
		refreshSelection();
		refreshQuranMiracle19();
		showSelectionRects();
		showEditAyahRects();
		LinearLayout bottom_layout = new LinearLayout(this.getActivity());
		TextView num_page = new TextView(this.getActivity());
		num_page.setText(String.valueOf(numPage));
		num_page.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
		bottom_layout.setX(Config.getQuranPageWidth() / 2 - 20);
		bottom_layout.setY(Config.getQuranPageHeight() - 30);
		bottom_layout.addView(num_page);
		layout.addView(bottom_layout);
		LinearLayout top_layout = new LinearLayout(this.getActivity());
		TextView sourat_name = new TextView(this.getActivity());
		sourat_name.setText(Connection.getSouratNameFromPage(numPage));
		sourat_name.setTextAlignment(TextView.TEXT_ALIGNMENT_VIEW_END);
		sourat_name.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sourat_name.setWidth(100);
		sourat_name.setTextSize(Config.getTextSize());
		top_layout.setX(Config.getQuranPageWidth() - 140);
		top_layout.setY(0);
		top_layout.addView(sourat_name);
		layout.addView(top_layout);
		return layout;
	}

	public int getScreenOrientation()
	{
		//Get current screen orientation
		int rotationType = Constant.ORIENTATION_TYPE_PORTRAIT;

        Display display = ((WindowManager) this.getActivity().getSystemService(this.getActivity().WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();
		if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180)
		{
			rotationType = Constant.ORIENTATION_TYPE_PORTRAIT;
		}
		if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270)
		{
			rotationType = Constant.ORIENTATION_TYPE_PAYSAGE;
		}
		return rotationType;
	}

	public void refreshSelection()
	{
		page.selection = Selection.getSelectPageRect(numPage);
		page.invalidate();
	}

	public void showSelectionRects()
	{
		if (Config.isSHOW_QURAN_SELECTION_RECTS())
		{		
			page.selectionRects = Selection.getSelectionPageRects(numPage);
			page.invalidate();
		}
	}

	public void showEditAyahRects()
	{
		if (Config.isSHOW_EDIT_AYAH_RECTS())
		{		
			page.editAyahRects = Selection.getEditAyahPageRects(numPage);
			page.invalidate();
		}
	}

	public void refreshQuranMiracle19()
	{
		System.out.println(Config.isSHOW_QURAN_19_MIRACLE());
		if (Config.isSHOW_QURAN_19_MIRACLE())
		{
			quran19miracle = new Quran19Miracle(numPage);
			page.quran19miracle = quran19miracle.getQuran19MiraclePageRect(numPage);
			page.invalidate();
		}
	}
	
	
}
