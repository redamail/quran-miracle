package com.quran;

import android.util.Log;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Rect;

public class Quran19Miracle
{

	public List<Quran19Group> quran19groups = new ArrayList<Quran19Group>();
	public static final int QURAN_19_MIRACLE_TYPE_AYAH = 1;
	public Quran19Miracle(int page)
	{
		String query = "select sourat_deb,ayah_deb,kalima_deb,page_deb,sourat_fin,ayah_fin,kalima_fin,page_fin, position, next, id, groupe, type from quran_miracle_19 where page_deb<=" + page + " and page_fin>=" + page;
		//query += " order by sourat_deb,ayah_deb,kalima_deb,sourat_fin,ayah_fin,kalima_fin";
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
		
		Quran19Group quran19group  = new Quran19Group();
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
				
				if(grp != groupe){
					quran19group = new Quran19Group();
					quran19groups.add(quran19group);
				}
				groupe = grp;
				Quran19Part quran19part = new Quran19Part(id, kalDeb, glyphDeb, kalFin, glyphFin, position, nextPosition, groupe, type);
				quran19group.quran19parts.add(quran19part);
				
				cur.moveToNext();
			}
		}
	}

	public List<Quran19Group> isGlyphInQuran19Miracle(Glyph glyph)
	{

		List<Quran19Group> retour = new ArrayList<Quran19Group>();
		Cursor cur;
		String query;

		for (Quran19Group quran19group : quran19groups)
		{

			for (Quran19Part quran19part :quran19group.quran19parts)
			{
				query = "select glyph_id from glyphs where type="+Glyph.GLYPH_TYPE_KALIMA+" and glyph_id>=" + quran19part.glyphDeb.getGlyph_id() + " and glyph_id<=" + quran19part.glyphFin.getGlyph_id() + " and glyph_id=" + glyph.getGlyph_id();
				//Log.i("Req",query);
				cur = Connection.db2.rawQuery(query, new String [] {});

				if (cur.getCount() > 0)
				{
					//System.out.println("num groupe "+quran19part.groupe);
					retour.add(getQuran19Group(quran19part));
					cur.close();
					break;
				}
				cur.close();
			}
		}

		return retour;
	}

	public List<Rect> getQuran19MiraclePageRect(int numPage)
	{

		//System.out.println("numpage 1" + numPage);
		Cursor cur;
		List<Rect> rects = new ArrayList<Rect>();
		//System.out.println("rects size "+rects.size());

		for (Quran19Group quran19group : quran19groups)
		{

			for (Quran19Part quran19part: quran19group.quran19parts)
			{
				String query = "select glyph_id, min_x, max_x, min_y, max_y, kalima_number from glyphs where type=1 and page_number=" + numPage + " and glyph_id>=" + quran19part.glyphDeb.getGlyph_id();
				if (quran19part.kalimaFin != null)
				{
					query += " and glyph_id<=" + quran19part.glyphFin.getGlyph_id();
				}
				else
				{
					query += " and glyph_id<=" + quran19part.glyphDeb.getGlyph_id();	
				}

				cur = Connection.db2.rawQuery(query, new String [] {});
				cur.moveToFirst();
				//page.selection.clear();
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
		Recupere un quran19group à partir d'un quran19part
	*/
	public Quran19Group getQuran19Group(Quran19Part p_quran19part){
		
		
		String query = "select sourat_deb,ayah_deb,kalima_deb,page_deb,sourat_fin,ayah_fin,kalima_fin,page_fin, position, next, id, groupe, type from quran_miracle_19 where groupe="+p_quran19part.groupe;
		Cursor cur = Connection.db.rawQuery(query, new String[]{});
		
		Quran19Group quran19group  = new Quran19Group();
		Glyph glyphDeb;
		Glyph glyphFin;
		Kalima kalDeb;
		Kalima kalFin;
		int position;
		int nextPosition;
		int id;
		int groupe;
		int type;
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
				type = cur.getInt(12);
				Quran19Part quran19part = new Quran19Part(id, kalDeb, glyphDeb, kalFin, glyphFin, position, nextPosition, groupe, type);
				quran19group.quran19parts.add(quran19part);

				cur.moveToNext();
			}
		}
		
		return quran19group;
	}
}


class Quran19Group
{

	public List<Quran19Part> quran19parts = new ArrayList<Quran19Part>();


}

class Quran19Part
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

	public Quran19Part(int id, Kalima kalimaDeb, Glyph glyphDeb, Kalima kalimaFin, Glyph glyphFin, int position, int nextPosition, int groupe, int type)
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
	}

	public List<Rect> getPageRect(int numPage)
	{

		//System.out.println("numpage 1" + numPage);
		Cursor cur;
		List<Rect> rects = new ArrayList<Rect>();
		//System.out.println("rects size "+rects.size());

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
		//page.selection.clear();
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
