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
import android.content.*;

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
	
	//int screenOrientation = -1;
	QuranMiracle19 quranmiracle19;
	QuranMiracleZawj quranmiraclezawj;
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

		quranmiracle19 = new QuranMiracle19(numPage);
		quranmiraclezawj = new QuranMiracleZawj(numPage);
		setRetainInstance(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancrState)
	{	
		Display display= ((WindowManager) this.getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 

		Config.setOrientationType(getScreenOrientation());

		if(Config.getOrientationType() == Constant.ORIENTATION_TYPE_PORTRAIT){
			Config.setQuranPagePortraitWidth(display.getWidth());
			Config.setQuranPagePortraitHeight(display.getHeight());
		}

		if(Config.getOrientationType() == Constant.ORIENTATION_TYPE_PAYSAGE){
			Config.setQuranPagePaysageWidth(display.getWidth());
			Config.setQuranPagePaysageHeight(display.getHeight());
		}
		
		String numPageStr = (numPage > 99) ? String.valueOf(numPage): (numPage > 9) ?"0" + String.valueOf(numPage): "00" + String.valueOf(numPage);
		Bitmap image = BitmapFactory.decodeFile(Config.getImageAdress()+"page" + numPageStr + ".png");
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
											List<QuranGroup> qurangroups19 = null;
											//si clic court sur glyph miracle
											if (Config.isShowQuranMiracle19())
											{
												qurangroups19 = quranmiracle19.isGlyphInQuranMiracle(glyph);
												/*
												if (!qurangroups.isEmpty())
												{
													MiracleDialog calc = new MiracleDialog19(pageActivity, qurangroups, quranmiracle19);
													calc.show(getActivity().getFragmentManager(), "miracle19");	
												}
												*/
											}
											
											List<QuranGroup> qurangroupszawj = null;
											if (Config.isShowQuranMiracleZawj())
											{
												qurangroupszawj = quranmiraclezawj.isGlyphInQuranMiracle(glyph);
												/*
												if (!qurangroups.isEmpty())
												{
													MiracleDialog calc = new MiracleDialogZawj(pageActivity, qurangroups, quranmiraclezawj);
													calc.show(getActivity().getFragmentManager(), "miraclezawj");	
												}
												*/
											}
											
											if(qurangroups19 != null || qurangroupszawj  != null){
												AllMiracleDialog calc = new AllMiracleDialog(pageActivity, glyph, quranmiracle19,  quranmiraclezawj, qurangroups19, qurangroupszawj  );
												calc.show(getActivity().getFragmentManager(), "allmiracle");	
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
		refreshQuranMiracleZawj();
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
		if (Config.isShowQuranSelectionRects())
		{		
			page.selectionRects = Selection.getSelectionPageRects(numPage);
			page.invalidate();
		}
	}

	public void showEditAyahRects()
	{
		if (Config.isShowEditAyahRects())
		{		
			page.editAyahRects = Selection.getEditAyahPageRects(numPage);
			page.invalidate();
		}
	}

	public void refreshQuranMiracle19()
	{
		if (Config.isShowQuranMiracle19())
		{
			quranmiracle19 = new QuranMiracle19(numPage);
			page.quranmiracle19 = quranmiracle19.getQuranMiraclePageRect(numPage);
			page.invalidate();
		}
	}
	
	public void refreshQuranMiracleZawj()
	{
		if (Config.isShowQuranMiracleZawj())
		{
			quranmiraclezawj = new QuranMiracleZawj(numPage);
			page.quranmiraclezawj = quranmiraclezawj.getQuranMiraclePageRect(numPage);
			page.invalidate();
		}
	}
	
	public void refreshAll(){
		refreshSelection();
		refreshQuranMiracle19();
		refreshQuranMiracleZawj();
	}
	
}
