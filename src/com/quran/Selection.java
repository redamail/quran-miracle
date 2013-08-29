package com.quran;

import android.util.Log;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Rect;

public class Selection
{

	private static boolean selectStarted = false;
	private static boolean selectionOk = false;
	static final int SELECTION_TYPE_GLYPH=1;
	static final int SELECTION_TYPE_AYAH=2;
	static final int SELECTION_TYPE_MULTI=3;
	static final int SELECTION_TYPE_SEPARATOR=4;
	//static final int SELECTION_MIRACLE_TYPE_SOURCE=1;
	//static final int SELECTION_MIRACLE_TYPE_EXPLICATION=2;
	static List<Select> selects = new ArrayList<Select>();
	static int nextSelPos = 0;

	public static void addSelection(int idSourat, int idAyah)
	{
		Ayah ayah = Connection.getAyahAyahId(idSourat, idAyah);

		Kalima kalDeb = Connection.getKalimaKalimaId(ayah.getId_sourat(), ayah.getId_ayah(), 1);
		Glyph glyDeb = Connection.getGlyphKalima(kalDeb);
		Selection.startSelect(kalDeb, glyDeb, Selection.SELECTION_TYPE_AYAH); 
		Kalima kalFin = Connection.getMaxKalima(ayah);
		Glyph glyFin = Connection.getGlyphKalima(kalFin);
		Selection.endSelect(kalFin, glyFin, ayah);
	}

	public static void addSelection(int idSourat, int idAyah, int idKalima)
	{
		Kalima kalDeb = Connection.getKalimaKalimaId(idSourat, idAyah, idKalima);
		Glyph glyDeb = Connection.getGlyphKalima(kalDeb);
		Selection.startSelect(kalDeb, glyDeb, Selection.SELECTION_TYPE_GLYPH); 
		Selection.endSelect(kalDeb, glyDeb, null);
	}
	
	public static boolean isGlyphInSelection(Glyph glyph)
	{

		boolean retour = false;
		Cursor cur;
		String query;

		for (Select sel :selects)
		{
			if (sel.selectionType != SELECTION_TYPE_SEPARATOR)
			{
				query = "select glyph_id from glyphs where type=1 and glyph_id>=" + sel.glyphDeb.getGlyph_id() + " and glyph_id<=" + sel.glyphFin.getGlyph_id() + " and glyph_id=" + glyph.getGlyph_id();
				cur = Connection.db2.rawQuery(query, new String [] {});

				if (cur.getCount() > 0)
				{
					retour = true;
					cur.close();
					break;
				}
				cur.close();
			}
		}

		return retour;
	}
	
	public static void addSeparator(){
		Select separator = new Select(null, null, SELECTION_TYPE_SEPARATOR);
		selects.add(separator);
	}

	public static void startSelect(Kalima kalDeb, Glyph glyDeb, int selType)
	{
		selectStarted = true;
		selectionOk = false;
		Select select = new Select(kalDeb, glyDeb, selType);
		
		if (selects.isEmpty())
		{
			addSeparator();
		}
		int sepPos = getSeparatorPosition();
	
		selects.add(sepPos,  select);
		//separatorPosition ++;
		
	}
	
	public static int getSeparatorPosition(){
		int sepPos = -1;
		if(!selects.isEmpty()){
			int i = 0;
			for(Select sel : selects){
				if(sel.selectionType == SELECTION_TYPE_SEPARATOR){
					sepPos = i;
					//break;
				}
				i++;
			}
		}
		
		return sepPos;
	}

	public static void cancelSelect()
	{
		selectStarted = false;
		if (!selects.isEmpty())
		{
			selects.remove(getSeparatorPosition() - 1);
			//separatorPosition --;
			if (!selects.isEmpty())selectionOk = true;
		}
	}

	
	public static boolean removeSelect(Glyph glyph)
	{
		Cursor cur;
		String query;
		boolean removed = false;
		for (int i = selects.size() - 1; i >= 0; i--)
		{
			Select sel = selects.get(i);
			if (sel.selectionType != SELECTION_TYPE_SEPARATOR)
			{
				query = "select glyph_id from glyphs where type=1 and glyph_id>=" + sel.glyphDeb.getGlyph_id() + " and glyph_id<=" + sel.glyphFin.getGlyph_id() + " and glyph_id=" + glyph.getGlyph_id();
				cur = Connection.db2.rawQuery(query, new String [] {});

				if (cur.getCount() > 0)
				{
					removed = true;
					selects.remove(sel);
					cur.close();
					break;
				}
				cur.close();
			}
		}
		return removed;
	}

	public static void endSelect(Kalima kalFin, Glyph glyFin, Ayah ayah)
	{

		Select lastsel = getLastSelect();

		if (lastsel.glyphDeb.getGlyph_id() > glyFin.getGlyph_id())
		{
			Glyph glyphTemp = lastsel.glyphDeb;
			lastsel.glyphDeb = glyFin;
			glyFin = glyphTemp;
			Kalima kalimaTemp = lastsel.kalimaDeb;
			lastsel.kalimaDeb = kalFin;
			kalFin = kalimaTemp;
		}
		
		lastsel.setFin(kalFin, glyFin, ayah);
		
		int numval = Connection.getNumVal(lastsel.kalimaDeb, lastsel.kalimaFin);
		int sumkal = Connection.getSumKal(lastsel.kalimaDeb, lastsel.kalimaFin);
		int sumhar = Connection.getSumHar(lastsel.kalimaDeb, lastsel.kalimaFin);
		lastsel.setCalc(numval,sumkal, sumhar);
		
		selectStarted = false;
		selectionOk = true;
		
	}

	public static void cancelSelection()
	{
		selectStarted = false;
		selectionOk = false;
		selects.clear();
	}

	public static boolean isSelectionStart()
	{
		return selectStarted;
	}

	public static boolean isSelectionOK()
	{
		return selectionOk;
	}

	public static Select getLastSelect()
	{
		return selects.get(getSeparatorPosition() - 1);
	}

	public static List<Rect> getSelectPageRect(int numPage)
	{

		Cursor cur;
		List<Rect> rects = new ArrayList<Rect>();

		for (Select sel: selects)
		{
			if (sel.selectionType != SELECTION_TYPE_SEPARATOR)
			{


				String query = "select glyph_id, min_x, max_x, min_y, max_y, kalima_number from glyphs where type=1 and page_number=" + numPage + " and glyph_id>=" + sel.glyphDeb.getGlyph_id();
				if (sel.kalimaFin != null)
				{
					query += " and glyph_id<=" + sel.glyphFin.getGlyph_id();
				}
				else
				{
					query += " and glyph_id<=" + sel.glyphDeb.getGlyph_id();	
				}

				cur = Connection.db2.rawQuery(query, new String [] {});
				cur.moveToFirst();
				while (cur.isAfterLast() == false)
				{
					float minX = (cur.getInt(1) * Config.getRatioImageWidthDb()) + Config.getImageMinX();
					float maxX = (cur.getInt(2) * Config.getRatioImageWidthDb()) + Config.getImageMinX();
					float minY = (cur.getInt(3) * Config.getRatioImageHeightDb()) + Config.getImageMinY();
					float maxY = (cur.getInt(4) * Config.getRatioImageHeightDb()) + Config.getImageMinY();
					rects.add(new Rect((int)minX, (int)minY, (int)maxX, (int)maxY));

					cur.moveToNext();
				}

				cur.close();
			}
		}
		return rects;
	}

	public static List<Rect> getEditAyahPageRects(int numPage)
	{

		Cursor cur;
		List<Rect> rects = new ArrayList<Rect>();

		String query = "select glyph_id, min_x, max_x, min_y, max_y, kalima_number, sura_number, ayah_number from glyphs where type=" + Glyph.GLYPH_TYPE_AYAH + " and page_number=" + numPage;

		cur = Connection.db2.rawQuery(query, new String [] {});
		cur.moveToFirst();
		while (cur.isAfterLast() == false)
		{
			float minX = (cur.getInt(1) * Config.getRatioImageWidthDb()) + Config.getImageMinX();
			float maxX = (cur.getInt(2) * Config.getRatioImageWidthDb()) + Config.getImageMinX();
			float minY = (cur.getInt(3) * Config.getRatioImageHeightDb()) + Config.getImageMinY();
			float maxY = (cur.getInt(4) * Config.getRatioImageHeightDb()) + Config.getImageMinY();
			if (isEditAyah(cur.getInt(6), cur.getInt(7)))
			{
				rects.add(new Rect((int)minX, (int)minY, (int)maxX, (int)maxY));
			}
			cur.moveToNext();
		}

		cur.close();
		return rects;
	}

	public static boolean isEditAyah(int sourat, int ayah)
	{
		String query = "select id from ayah2 where id_sourat=" + sourat + " and id_ayah=" + ayah + " and num_value<>0";

		Cursor cur = Connection.db.rawQuery(query, new String [] {});

		if (cur.getCount() > 0)
		{
			return true;
		}
		return false;
	}

	public static List<Rect> getSelectionPageRects(int numPage)
	{

		Cursor cur;
		List<Rect> rects = new ArrayList<Rect>();

		String query = "select glyph_id, min_x, max_x, min_y, max_y, kalima_number from glyphs where (type=" + Glyph.GLYPH_TYPE_KALIMA + " or type=" + Glyph.GLYPH_TYPE_AYAH + ")and page_number=" + numPage;

		cur = Connection.db2.rawQuery(query, new String [] {});
		cur.moveToFirst();
		while (cur.isAfterLast() == false)
		{
			float minX = (cur.getInt(1) * Config.getRatioImageWidthDb()) + Config.getImageMinX();
			float maxX = (cur.getInt(2) * Config.getRatioImageWidthDb()) + Config.getImageMinX();
			float minY = (cur.getInt(3) * Config.getRatioImageHeightDb()) + Config.getImageMinY();
			float maxY = (cur.getInt(4) * Config.getRatioImageHeightDb()) + Config.getImageMinY();
			rects.add(new Rect((int)minX, (int)minY, (int)maxX, (int)maxY));

			cur.moveToNext();
		}

		cur.close();
		return rects;
	}
	
	public static void updateSelectType(){
		int type = 1;
		for(Select sel : selects){
			if(sel.selectionType == SELECTION_TYPE_SEPARATOR){
				type++;
				continue;
			}
			sel.setMiracleType(type);
		}
	}
	
	public static void updateSelectPositions(){
		int i = 1;
		for(Select sel : selects){
			if(sel.selectionType == SELECTION_TYPE_SEPARATOR){
				continue;
			}
			sel.selPos = i;
			if(i==selects.size()){
				sel.selNextPosition = 0;
				
			}else{
				sel.selNextPosition = i +1;
			}
			i++;
		}
	}
}

class Select
{
	public void setMiracleType(int miracleType)
	{
		this.miracleType = miracleType;
	}

	public int getMiracleType()
	{
		return miracleType;
	}

	public void setFin(Kalima kalFin, Glyph glyFin, Ayah ayah)
	{
		this.kalimaFin = kalFin;
		this.glyphFin = glyFin;
		this.ayah = ayah;
	}

	public void setCalc(int numVal, int sumKal, int sumHar)
	{
		this.numVal = numVal;
		this.sumKal = sumKal;
		this.sumHar = sumHar;
	}
	
	public Select(Kalima kalimaDeb, Kalima kalimaFin, Glyph glyphDeb, Glyph glyphFin, int selectionType, Ayah ayah)
	{
		this.kalimaDeb = kalimaDeb;
		this.kalimaFin = kalimaFin;
		this.glyphDeb = glyphDeb;
		this.glyphFin = glyphFin;
		this.selectionType = selectionType;
		this.ayah = ayah;
	}

	public Select(Kalima kalDeb, Glyph glyDeb, int selType)
	{
		this.kalimaDeb = kalDeb;
		this.glyphDeb = glyDeb;
		this.selectionType = selType;
	}

	Kalima kalimaDeb;
	Glyph glyphDeb;
	int selectionType;
	Kalima kalimaFin;
	Glyph glyphFin;
	Ayah ayah;
	int miracleType;
	int selPos;
	int selNextPosition;
	int numVal;
	int sumKal;
	int sumHar;
}
