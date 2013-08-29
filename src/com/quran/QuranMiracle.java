package com.quran;

import android.util.Log;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Rect;

public class QuranMiracle
{
	public List<QuranGroup> qurangroups = new ArrayList<QuranGroup>();
	public static final int QURAN_MIRACLE_TYPE_AYAH = 1;
	public String miracleTable = "";
	//public boolean showNumericValue = true;
	//public boolean showKalimaSum = true;
	//public boolean showHarfSum = true;
	
	public void load(int page){
		String query = "select sourat_deb,ayah_deb,kalima_deb,page_deb,sourat_fin,ayah_fin,kalima_fin,page_fin, position, next, id, groupe, type, num_val, sum_kal, sum_har from "+miracleTable+" where page_deb<=" + page + " and page_fin>=" + page;
		Cursor cur = Connection.db.rawQuery(query, new String[]{});
		Glyph glyphDeb;
		Glyph glyphFin;
		Kalima kalDeb;
		Kalima kalFin;
		int position;
		int nextPosition;
		int id;
		int groupe = 0;
		int type;
		int numVal;
		int sumKal;
		int sumHar;

		QuranGroup qurangroup  = new QuranGroup();
		if (cur.getCount() > 0)
		{
			cur.moveToFirst();
			while (!cur.isAfterLast())
			{
				glyphDeb = Connection.getGlyphKalimaId(cur.getInt(0), cur.getInt(1), cur.getInt(2));
				glyphFin = Connection.getGlyphKalimaId(cur.getInt(4), cur.getInt(5), cur.getInt(6));
				kalDeb = Connection.getKalimaKalimaId(cur.getInt(0), cur.getInt(1), cur.getInt(2));
				kalFin = Connection.getKalimaKalimaId(cur.getInt(4), cur.getInt(5), cur.getInt(6));
				position = cur.getInt(8);
				nextPosition = cur.getInt(9);
				id = cur.getInt(10);
				type = cur.getInt(12);
				int grp = cur.getInt(11);
				numVal = cur.getInt(13);
				sumKal = cur.getInt(14);
				sumHar = cur.getInt(15);
				
				if(grp != groupe){
					qurangroup = new QuranGroup();
					qurangroup.setId(grp);
					qurangroups.add(qurangroup);
				}
				groupe = grp;
				QuranPart quranpart = new QuranPart(id, kalDeb, glyphDeb, kalFin, 
													glyphFin, position, nextPosition, 
													groupe, type, numVal, sumKal, sumHar);
				qurangroup.quranparts.add(quranpart);

				cur.moveToNext();
			}
		}
	}

	public List<QuranGroup> isGlyphInQuranMiracle(Glyph glyph)
	{

		List<QuranGroup> retour = new ArrayList<QuranGroup>();
		Cursor cur;
		String query;

		for (QuranGroup qurangroup : qurangroups)
		{

			for (QuranPart quranpart :qurangroup.quranparts)
			{
				query = "select glyph_id from glyphs where type="+Glyph.GLYPH_TYPE_KALIMA+" and glyph_id>=" + quranpart.glyphDeb.getGlyph_id() + " and glyph_id<=" + quranpart.glyphFin.getGlyph_id() + " and glyph_id=" + glyph.getGlyph_id();
				cur = Connection.db2.rawQuery(query, new String [] {});

				if (cur.getCount() > 0)
				{
					retour.add(getQuranGroup(quranpart));
					cur.close();
					break;
				}
				cur.close();
			}
		}

		return retour;
	}

	public List<Rect> getQuranMiraclePageRect(int numPage)
	{

		Cursor cur;
		List<Rect> rects = new ArrayList<Rect>();
		
		for (QuranGroup qurangroup : qurangroups)
		{

			for (QuranPart quranpart: qurangroup.quranparts)
			{
				String query = "select glyph_id, min_x, max_x, min_y, max_y, kalima_number from glyphs where type=1 and page_number=" + numPage + " and glyph_id>=" + quranpart.glyphDeb.getGlyph_id();
				if (quranpart.kalimaFin != null)
				{
					query += " and glyph_id<=" + quranpart.glyphFin.getGlyph_id();
				}
				else
				{
					query += " and glyph_id<=" + quranpart.glyphDeb.getGlyph_id();	
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
	
	/*
		Recupere un qurangroup à partir d'un quranpart
	*/
	public QuranGroup getQuranGroup(QuranPart p_quranpart){
		
		
		String query = "select sourat_deb,ayah_deb,kalima_deb,page_deb,sourat_fin,ayah_fin,kalima_fin,page_fin, position, next, id, groupe, type, num_val, sum_kal, sum_har from "+miracleTable+" where groupe="+p_quranpart.groupe;
		Cursor cur = Connection.db.rawQuery(query, new String[]{});
		
		QuranGroup qurangroup  = new QuranGroup();
		Glyph glyphDeb;
		Glyph glyphFin;
		Kalima kalDeb;
		Kalima kalFin;
		int position;
		int nextPosition;
		int id;
		int groupe;
		int type;
		int numVal;
		int sumKal;
		int sumHar;
		
		if (cur.getCount() > 0)
		{
			cur.moveToFirst();
			while (!cur.isAfterLast())
			{
				glyphDeb = Connection.getGlyphKalimaId(cur.getInt(0), cur.getInt(1), cur.getInt(2));
				glyphFin = Connection.getGlyphKalimaId(cur.getInt(4), cur.getInt(5), cur.getInt(6));
				kalDeb = Connection.getKalimaKalimaId(cur.getInt(0), cur.getInt(1), cur.getInt(2));
				kalFin = Connection.getKalimaKalimaId(cur.getInt(4), cur.getInt(5), cur.getInt(6));
				position = cur.getInt(8);
				nextPosition = cur.getInt(9);
				id = cur.getInt(10);
				groupe = cur.getInt(11);
				qurangroup.setId(groupe);
				type = cur.getInt(12);
				numVal = cur.getInt(13);
				sumKal = cur.getInt(14);
				sumHar = cur.getInt(15);
				
				QuranPart quranpart = new QuranPart(id, kalDeb, glyphDeb, kalFin, 
													glyphFin, position, nextPosition, 
													groupe, type, numVal, sumKal, sumHar);
				qurangroup.quranparts.add(quranpart);

				cur.moveToNext();
			}
		}
		return qurangroup;
	}
}

class QuranGroup
{
	public void setId(int id){
		this.id = id;
	}
	
	public QuranGroup(){
	}
	
	public QuranGroup(int id){
		this.id = id;
		quranparts = new ArrayList<QuranPart>();
	}
	
	public QuranGroup clone(){
		QuranGroup group = new QuranGroup (id);
		
		for(QuranPart part:quranparts){
			group.quranparts.add(part);
		}
		return group;
	}
	
	public int id;
	public List<QuranPart> quranparts = new ArrayList<QuranPart>();
	
	public int getMaxType(){
		int maxType = 0;
		for(QuranPart part: quranparts){
			if(part.type > maxType) maxType = part.type;
		}
		return maxType ;
	}
	
	public int getNumVal(){
		int numVal = 0;
		
		for(QuranPart part: quranparts){
			numVal += part.numVal;
		}
		return numVal;
	}
	
	public int getNumVal(int type){
		int numVal = 0;

		for(QuranPart part: quranparts){
			if(part.type == type)
			numVal += part.numVal;
		}
		return numVal;
	}

	public int getSumKal(){
		int sumKal = 0;

		for(QuranPart part: quranparts){
			sumKal += part.sumKal;
		}
		return sumKal;
	}

	public int getSumKal(int type){
		int sumKal = 0;

		for(QuranPart part: quranparts){
			if(part.type == type)
				sumKal += part.sumKal;
		}
		return sumKal;
	}
	
	public int getSumHar(){
		int sumHar = 0;

		for(QuranPart part: quranparts){
			sumHar += part.sumHar;
		}
		return sumHar;
	}

	public int getSumHar(int type){
		int sumHar = 0;

		for(QuranPart part: quranparts){
			if(part.type == type)
				sumHar += part.sumHar;
		}
		return sumHar;
	}
	
	public int getCount(int type){
		int count = 0;

		for(QuranPart part: quranparts){
			if(part.type == type)
				count++;
		}
		return count;
	}
	
	public int getCount(){
		return quranparts.size();
	}
}

class QuranPart
{
	int id;
	Kalima kalimaDeb;
	Glyph glyphDeb;
	Kalima kalimaFin;
	Glyph glyphFin;
	int position;
	int nextPosition;
	int groupe;
	int type;
	int numVal;
	int sumKal;
	int sumHar;
	int nbRepit = 0;
	public QuranPart(int id, Kalima kalimaDeb, Glyph glyphDeb, Kalima kalimaFin, Glyph glyphFin, int position, int nextPosition, int groupe, int type, int numVal, int sumKal, int sumHar)
	{
		this.id = id;
		this.kalimaDeb = kalimaDeb;
		this.glyphDeb = glyphDeb;
		this.kalimaFin = kalimaFin;
		this.glyphFin = glyphFin;
		this.position = position;
		this.nextPosition = nextPosition;
		this.groupe = groupe;
		this.type = type;
		this.numVal = numVal;
		this.sumKal = sumKal;
		this.sumHar = sumHar;
	}

	public List<Rect> getPageRect(int numPage)
	{

		Cursor cur;
		List<Rect> rects = new ArrayList<Rect>();
		
		String query = "select glyph_id, min_x, max_x, min_y, max_y, kalima_number from glyphs where type=1 and page_number=" + numPage + " and glyph_id>=" + glyphDeb.getGlyph_id();
		if (kalimaFin != null)
		{
			query += " and glyph_id<=" + glyphFin.getGlyph_id();
		}
		else
		{
			query += " and glyph_id<=" + glyphDeb.getGlyph_id();	
		}

		cur = Connection.db2.rawQuery(query, new String [] {});
		cur.moveToFirst();
		while (cur.isAfterLast() == false)
		{
			float minX = (cur.getInt(1) * Config.getRatioImageWidthDb()) + Config.getImageMinX();
			float maxX = (cur.getInt(2) * Config.getRatioImageWidthDb()) + Config.getImageMinX();
			float minY = (cur.getInt(4) * Config.getRatioImageHeightDb()) + Config.getImageMinY() - 5;
			float maxY = (cur.getInt(4) * Config.getRatioImageHeightDb()) + Config.getImageMinY();
			rects.add(new Rect((int)minX, (int)minY, (int)maxX, (int)maxY));

			cur.moveToNext();
		}

		cur.close();
		return rects;
	}
}
