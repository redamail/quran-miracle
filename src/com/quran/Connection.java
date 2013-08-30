package com.quran;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Toast;
import android.content.Context;
import java.security.acl.Group;
import java.util.List;
import java.util.ArrayList;
import android.util.*;

public class Connection
{

	static SQLiteDatabase db2 = SQLiteDatabase.openDatabase(Config.getDb2Adress(), null, SQLiteDatabase.OPEN_READWRITE);
	static SQLiteDatabase db = SQLiteDatabase.openDatabase(Config.getDb1Adress(), null, SQLiteDatabase.OPEN_READWRITE);

	public static int getSumHar(Kalima kalDeb, Kalima kalFin)
	{
		String query = "select sum(sum_harf) from kalima where id>=" + kalDeb.getId() + " and id<=" + kalFin.getId();
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int num_har = cur.getInt(0);
		cur.close();
		return num_har;
	}

	public static Kalima getKalimaKalimaId(int sourat, int ayah, int kalima)
	{
		String query = "select id, id_sourat, id_ayah, id_kalima, kalima, num_value, sum_harf from kalima where id_sourat=" + sourat + " and id_ayah=" + ayah + " and id_kalima=" + kalima;
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		Kalima kal = null;
		cur.moveToFirst();
		kal = new Kalima(cur.getInt(0), cur.getInt(1), cur.getInt(2), cur.getInt(3), cur.getString(4), cur.getInt(5), cur.getInt(6));
		cur.close();
		return kal;
	}

	public static Kalima getKalimaGlyph(Glyph glyph)
	{
		String query = "select id, id_sourat, id_ayah, id_kalima, kalima, num_value, sum_harf from kalima where id_sourat=" + glyph.getSura_number() + " and id_ayah=" + glyph.getAyah_number() + " and id_kalima=" + glyph.getKalima_number();
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		Kalima kal = new Kalima(cur.getInt(0), cur.getInt(1), cur.getInt(2), cur.getInt(3), cur.getString(4), cur.getInt(5), cur.getInt(6));
		cur.close();
		return kal;
	}

	public static String getKalimateText(Kalima deb, Kalima fin)
	{
		String kalimate = "";
		String query = "select id_ayah, kalima from kalima where id>=" + deb.getId() + " and id<=" + fin.getId();
		System.out.println(query);
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int id_ayah=cur.getInt(0);
		while (cur.isAfterLast() == false)
		{
			if (id_ayah != cur.getInt(0))
			{
				kalimate = kalimate + "(" + id_ayah + ") ";
				id_ayah = cur.getInt(0);
			}
			kalimate = kalimate + cur.getString(1) + " ";
			cur.moveToNext();
		}
		kalimate = kalimate + "(" + id_ayah + ") ";
		kalimate = kalimate + "[" + getSouratNameFromId(fin.getId_sourat()) + "] ";
		cur.close();
		return kalimate;
	}

	public static Kalima getMaxKalima(Ayah ayah)
	{
		String query = "select max(id_kalima) from kalima where id_sourat=" + ayah.getId_sourat() + " and id_ayah=" + ayah.getId_ayah();
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int max_kalima_id = cur.getInt(0);
		cur.close();
		Kalima kal = getKalimaKalimaId(ayah.getId_sourat(), ayah.getId_ayah(), max_kalima_id);
		return kal;
	}

	public static int getMaxAyah(int souratId)
	{
		String query = "select max(id_ayah) from ayah2 where id_sourat=" + souratId;
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int max_ayah_id = cur.getInt(0);
		cur.close();
		return max_ayah_id;
	}

	public static Ayah getAyahAyahId(int sourat_id, int ayah_id)
	{
		String query = "select id, id_sourat, id_ayah, sum_harf, sum_kalima, num_value, status, ayah, ayah2 from ayah2 where id_sourat=" + sourat_id + " and id_ayah=" + ayah_id;
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		Ayah ayah = new Ayah(cur.getInt(0), cur.getInt(1), cur.getInt(2), cur.getInt(3), cur.getInt(4), cur.getInt(5), cur.getInt(6), cur.getString(7), cur.getString(8));
		cur.close();
		return ayah;
	}

	public static Glyph getGlyphKalimaId(int sourat, int ayah, int kalima)
	{
		String query = "select glyph_id, page_number, line_number, sura_number, ayah_number, position, min_x, max_x, min_y, max_y, type, kalima_number from glyphs where sura_number=" + sourat + " and ayah_number=" + ayah + " and kalima_number=" + kalima;
		Cursor cur = Connection.db2.rawQuery(query, new String [] {});
		Glyph glyph = null;
		try{
			cur.moveToFirst();
			glyph = new Glyph(cur.getInt(0), cur.getInt(1), cur.getInt(2), cur.getInt(3), cur.getInt(4), cur.getInt(5), cur.getInt(6), cur.getInt(7), cur.getInt(8), cur.getInt(9), cur.getInt(10), cur.getInt(11));
		}catch(Exception e){
			System.err.println(e);
			Log.i("QURAN ERROR","Error on getting glyph for Kalima ("+sourat+","+ayah+","+kalima+")");
		}
		cur.close();
		return glyph;
	}

	public static int getSouratIdFromPage(int page)
	{
		String query = "select min(sura_number) from glyphs where page_number=" + page;
		Cursor cur = Connection.db2.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int sourat = cur.getInt(0);
		cur.close();
		return sourat;
	}

	public static String getSouratNameFromId(int idSourat)
	{
		String query = "select sourat from sourat where id_sourat=" + idSourat;
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		String sourat = cur.getString(0);
		cur.close();
		return sourat;
	}

	public static String getSouratNameFromPage(int page)
	{
		return getSouratNameFromId(getSouratIdFromPage(page));
	}

	public static Glyph getGlyphKalima(Kalima kalima)
	{
		return getGlyphKalimaId(kalima.getId_sourat(), kalima.getId_ayah(), kalima.getId_kalima());
	}

	public static int getNumVal(Kalima kalDeb, Kalima kalFin)
	{
		String query = "select sum(num_value) from kalima where id>=" + kalDeb.getId() + " and id<=" + kalFin.getId();
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int num_val = cur.getInt(0);
		cur.close();
		return num_val;
	}

	public static int getSumKal(Kalima kalDeb, Kalima kalFin)
	{
		String query = "select count(*) from kalima where id>=" + kalDeb.getId() + " and id<=" + kalFin.getId();
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int num_kal = cur.getInt(0);
		cur.close();
		return num_kal;
	}


	public static Glyph getGlyphPage(float x, float y, int numpage)
	{
		x = (x - Config.getImageMinX()) / Config.getRatioImageWidthDb() ;
		y = (y  - Config.getImageMinY()) / Config.getRatioImageHeightDb() ;
		String query = "select glyph_id, page_number, line_number, sura_number, ayah_number, position, min_x, max_x, min_y, max_y, type, kalima_number from glyphs where page_number=" + numpage + " and " + x + ">min_x and " + x + "<max_x and " + y + ">min_y and " + y + "<max_y";
		Cursor cur = Connection.db2.rawQuery(query, new String [] {});
		Glyph glyph = null;
		if (cur.getCount() > 0)
		{
			cur.moveToFirst();
			glyph = new Glyph(cur.getInt(0), cur.getInt(1), cur.getInt(2), cur.getInt(3), cur.getInt(4), cur.getInt(5), cur.getInt(6), cur.getInt(7), cur.getInt(8), cur.getInt(9), cur.getInt(10), cur.getInt(11));
		}
		cur.close();
		return glyph;
	}


	public static int getNumPages()
	{
		String query = "select max(page_number) from glyphs";
		Cursor cur = Connection.db2.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int num_page = cur.getInt(0);
		cur.close();
		return num_page;
	}

	public static int getNumPage(int idsourat, int idayah)
	{
		String query = "select max(page_number) from glyphs where sura_number=" + idsourat + " and ayah_number=" + idayah;
		Cursor cur = Connection.db2.rawQuery(query, new String [] {});
		cur.moveToFirst();
		int nopage = cur.getInt(0);
		cur.close();
		return nopage;
	}

	public static int getSouratId(String sourat)
	{
		String query = "select id_sourat from sourat where sourat like '%" + sourat + "%'";
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		int idsourat = -1;
		if (cur.getCount() > 0)
		{
			cur.moveToFirst();
			idsourat += cur.getInt(0);
		}
		cur.close();
		return idsourat;
	}
	
	public static int getCompleteSouratId(String sourat)
	{
		String query = "select id_sourat from sourat where sourat like '" + sourat + "'";
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		int idsourat = -1;
		if (cur.getCount() > 0)
		{
			cur.moveToFirst();
			idsourat += cur.getInt(0);
		}
		cur.close();
		return idsourat;
	}
	
	public static boolean insertIntoQuranMiracle19(Context context)
	{
		String query="";

		int groupe = getNextQuranMiracle19Groupe();
		query = "insert into quran_miracle_19 (sourat_deb,ayah_deb,kalima_deb,page_deb,sourat_fin,ayah_fin,kalima_fin,page_fin, position, next, groupe, type, num_val, sum_kal, sum_har) values ";	

		int i = 0;
		for (Select sel : Selection.selects)
		{
			if (sel.selectionType != Selection.SELECTION_TYPE_SEPARATOR)
			{
				if(i>10){
					query = query.substring(0, query.lastIndexOf(",")) + ";";
					Connection.db.execSQL(query);
					query = "insert into quran_miracle_zawj (sourat_deb,ayah_deb,kalima_deb,page_deb,sourat_fin,ayah_fin,kalima_fin,page_fin, position, next, groupe, type) values ";
					i = 0;
				}
				query += "(" + sel.kalimaDeb.getId_sourat() + "," + sel.kalimaDeb.getId_ayah() + "," + sel.kalimaDeb.getId_kalima() + "," + sel.glyphDeb.getPage_number() + "," + sel.kalimaFin.getId_sourat() + "," + sel.kalimaFin.getId_ayah() + "," + sel.kalimaFin.getId_kalima() + "," + sel.glyphFin.getPage_number();
				query += "," + sel.selPos + "," + sel.selNextPosition + "," + groupe + "," + sel.getMiracleType() + "),";
				i++;
			}
		}

		query = query.substring(0, query.lastIndexOf(",")) + ";";
		Connection.db.execSQL(query);
		Connection.db.execSQL("update quran_miracle_19 set num_val=(select sum(num_value) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_19 set sum_kal=(select count(id) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_19 set sum_har=(select sum(sum_harf) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set num_val=(select sum(num_value) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set sum_kal=(select count(id) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set sum_har=(select sum(sum_harf) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		return true;

	}

	public static boolean insertIntoQuranMiracleZawj(Context context)
	{
		String query="";

		int groupe = getNextQuranMiracleZawjGroupe();
		query = "insert into quran_miracle_zawj (sourat_deb,ayah_deb,kalima_deb,page_deb,sourat_fin,ayah_fin,kalima_fin,page_fin, position, next, groupe, type) values ";	

		int i = 0;
		for (Select sel : Selection.selects)
		{
			if (sel.selectionType != Selection.SELECTION_TYPE_SEPARATOR)
			{
				if(i>10){
					query = query.substring(0, query.lastIndexOf(",")) + ";";
					Connection.db.execSQL(query);
					query = "insert into quran_miracle_zawj (sourat_deb,ayah_deb,kalima_deb,page_deb,sourat_fin,ayah_fin,kalima_fin,page_fin, position, next, groupe, type) values ";
					i = 0;
				}
				query += "(" + sel.kalimaDeb.getId_sourat() + "," + sel.kalimaDeb.getId_ayah() + "," + sel.kalimaDeb.getId_kalima() + "," + sel.glyphDeb.getPage_number() + "," + sel.kalimaFin.getId_sourat() + "," + sel.kalimaFin.getId_ayah() + "," + sel.kalimaFin.getId_kalima() + "," + sel.glyphFin.getPage_number();
				query += "," + sel.selPos + "," + sel.selNextPosition + "," + groupe + "," + sel.getMiracleType() + "),";
				i++;
			}
		}

		query = query.substring(0, query.lastIndexOf(",")) + ";";
		Connection.db.execSQL(query);
		Connection.db.execSQL("update quran_miracle_19 set num_val=(select sum(num_value) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_19 set sum_kal=(select count(id) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_19 set sum_har=(select sum(sum_harf) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set num_val=(select sum(num_value) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set sum_kal=(select count(id) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set sum_har=(select sum(sum_harf) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		return true;

	}
	
	public static int getNextQuranMiracle19Groupe()
	{

		int id = 0;
		String query = "select max(groupe) from quran_miracle_19";
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		if (cur.getCount() > 0)
		{
			cur.moveToFirst();
			id = cur.getInt(0);
		}
		cur.close();

		return id + 1;

	}

	public static int getNextQuranMiracleZawjGroupe()
	{

		int id = 0;
		String query = "select max(groupe) from quran_miracle_zawj";
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		if (cur.getCount() > 0)
		{
			cur.moveToFirst();
			id = cur.getInt(0);
		}
		cur.close();

		return id + 1;

	}
	public static List<String[]> getGridList()
	{
		String query = "select id, harf, num_value from ref_harf order by num_value, id";
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		List<String[]> numbers = new ArrayList<String[]>();

		while (cur.isAfterLast() == false)
		{
			String[] str = new String[2];
			int num_val = cur.getInt(2);
			str[0] = String.valueOf(num_val);

			String harf = cur.getString(1);
			str[1] = String.valueOf(harf);

			numbers.add(str);
			cur.moveToNext();

		}
		cur.close();
		return numbers;
	}

	public static boolean delFromQuranMiracle19(Context context, QuranGroup group)
	{
		String query="";

		for (QuranPart part : group.quranparts)
		{
			query = "delete from quran_miracle_19 where id=" + part.id;	
			Connection.db.execSQL(query);
		}
		return true;

	}

	public static boolean delFromQuranMiracleZawj(Context context, QuranGroup group)
	{
		String query="";

		for (QuranPart part : group.quranparts)
		{
			query = "delete from quran_miracle_zawj where id=" + part.id;	
			Connection.db.execSQL(query);
		}
		return true;

	}
	
	public static void updateAyahAyah2(Ayah ayah)
	{
		updateAyahAyah2(ayah.getId_sourat(), ayah.getId_ayah(), ayah.getAyah2());	
	}

	public static void updateAyahAyah2(int idsourat, int idayah, String ayah2)
	{
		String query="";
		query += "update ayah2 set ayah2='" + ayah2 + "' where id="+getId(idsourat,idayah);
		Connection.db.execSQL(query);
	}
	
	public static int getId(int idSourat, int idAyah){
		return idSourat * 1000 + idAyah;
	}

	public static int getId(int idSourat, int idAyah, int idKalima){
		return idSourat * 1000000 + idAyah * 1000 + idKalima;
	}
	
	public static int getId(int idSourat, int idAyah, int idKalima, int idHarf){
		return idSourat * 100000000 + idAyah * 100000 + idKalima * 100 + idHarf;
	}
	
	public static void updateKalimaFromAyah(Ayah ayah)
	{
		updateKalima(ayah.getId_sourat(), ayah.getId_ayah(), ayah.getAyah2());
	}

	public static void updateHarfFromAyah(Ayah ayah)
	{
		updateHarf(ayah.getId_sourat(), ayah.getId_ayah());
	}

	public static void updateCalculFromAyah(Ayah ayah)
	{
		updateCalcul(ayah.getId_sourat(), ayah.getId_ayah());
	}

	public static void updateKalima(final int idSourat, final int idAyah, final String ayah2)
	{

		String query = "";

		query = "delete from kalima where id_sourat=" + idSourat + " and id_ayah=" + idAyah;

		Connection.db.execSQL(query);

		String [] kalimat = ayah2.split(" ");
		int i = 1;
		for (String kalima : kalimat)
		{
			if (! kalima.equalsIgnoreCase("") && !kalima.equalsIgnoreCase(" "))
			{
				int id = getId(idSourat,idAyah,i);
				Connection.db.execSQL("insert into kalima(id, id_sourat, id_ayah, id_kalima, kalima) values (" + id + "," + idSourat + ", " + idAyah + ", " + i + ", '" + kalima + "')");
				i++;
			}
		}
	}
	
	public static void updateHarf(final int idSourat, final int idAyah)
	{

		String query = "";

		query = "delete from harf where id_sourat=" + idSourat + " and id_ayah=" + idAyah;

		Connection.db.execSQL(query);

		query = "select id_sourat, id_ayah, id_kalima, kalima from kalima where id_sourat=" + idSourat + " and id_ayah=" + idAyah + " order by id_sourat, id_ayah";
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		while (!cur.isAfterLast())
		{
			String kalima = cur.getString(3);
			int idKalima = cur.getInt(2);
			String [] hourouf = kalima.split("");
			int i = 1;
			query = "insert into harf(id, id_sourat, id_ayah, id_kalima, id_harf, harf) values ";
			for (String harf : hourouf)
			{
				if (!harf.equalsIgnoreCase(""))
				{
					int id = getId(idSourat,idAyah,idKalima,i);
					query += ("(" + id + "," + idSourat + ", " + idAyah + ", " + idKalima + ", " + i + ", '" + harf + "'),");
					i++;	
				}
			}
			query = query.substring(0, query.length() - 1) + ";";
			Connection.db.execSQL(query);
			cur.moveToNext();
		}
		cur.close();
	}

	public static void updateHizb(Ayah ayah)
	{

		String query = "";

		query = "update glyphs set type=" + Glyph.GLYPH_TYPE_HIZB + ", kalima_number=null where position=1 and sura_number=" + ayah.getId_sourat() + " and ayah_number=" + ayah.getId_ayah();
		Connection.db2.execSQL(query, new String [] {});

		query = "update glyphs set kalima_number=kalima_number-1 where type=" + Glyph.GLYPH_TYPE_KALIMA + " and sura_number=" + ayah.getId_sourat() + " and ayah_number=" + ayah.getId_ayah();
		Connection.db2.execSQL(query, new String [] {});

	}


	public static void updateCalcul(final int idSourat, final int idAyah)
	{


		String tableAyah="ayah2";

		String cond = "";

		String cond2 = "";

		if (idSourat != 0)
		{
			cond = "where id_sourat=" + idSourat;
			cond2 = "where id_sourat=" + idSourat;
			if (idAyah != 0)
			{
				cond += " and id_ayah=" + idAyah;
			}
		}

		Connection.db.execSQL("update harf set num_value=(select num_value from ref_harf where harf.harf = ref_harf.harf)" + cond);

		String query = "select id_sourat, id_ayah, id_kalima, id_harf, h1.harf from harf h1, ref_harf f1 " + cond + " and h1.harf = f1.harf and f1.id=2 and id_harf > 1 and id_harf<(select max(id_harf) from harf h2 where h1.id_sourat=h2.id_sourat and h1.id_ayah=h2.id_ayah and h1.id_kalima=h2.id_kalima)";
		Cursor cur = Connection.db.rawQuery(query, new String [] {});
		cur.moveToFirst();
		while (!cur.isAfterLast())
		{
			int id_sourat = cur.getInt(0);
			int id_ayah = cur.getInt(1);
			int id_kalima = cur.getInt(2);
			int id_harf = cur.getInt(3);
			query = "update harf set num_value=6 where id_sourat=" + id_sourat + " and id_ayah=" + id_ayah + " and id_kalima=" + id_kalima + " and id_harf=" + id_harf;

			Connection.db.execSQL(query);
			cur.moveToNext();
		}
		cur.close();

		Connection.db.execSQL("update kalima set num_value=(select sum(num_value) from harf where kalima.id_kalima = harf.id_kalima and kalima.id_ayah=harf.id_ayah and kalima.id_sourat=harf.id_sourat) " + cond);

		Connection.db.execSQL("update kalima set sum_harf=(select count(id) from harf where kalima.id_kalima = harf.id_kalima and kalima.id_ayah = harf.id_ayah and kalima.id_sourat = harf.id_sourat ) " + cond);

		Connection.db.execSQL("update " + tableAyah + " set num_value=(select sum(num_value) from kalima where kalima.id_ayah = " + tableAyah + ".id_ayah and kalima.id_sourat = " + tableAyah + ".id_sourat ) " + cond);

		Connection.db.execSQL("update " + tableAyah + " set sum_harf=(select sum(sum_harf) from kalima where kalima.id_ayah = " + tableAyah + ".id_ayah and kalima.id_sourat = " + tableAyah + ".id_sourat ) " + cond);

		Connection.db.execSQL("update " + tableAyah + " set sum_kalima=(select count(id) from kalima where " + tableAyah + ".id_sourat = kalima.id_sourat and " + tableAyah + ".id_ayah = kalima.id_ayah) " + cond);

		Connection.db.execSQL("update sourat set num_value=(select sum(num_value ) from " + tableAyah + " where " + tableAyah + ".id_sourat = sourat.id_sourat ) " + cond2);

		Connection.db.execSQL("update sourat set sum_harf=(select sum(sum_harf) from " + tableAyah + " where " + tableAyah + ".id_sourat = sourat.id_sourat ) " + cond2);

		Connection.db.execSQL("update sourat set sum_ayah=(select count(id) from " + tableAyah + " where " + tableAyah + ".id_sourat = sourat.id_sourat ) " + cond2);

		Connection.db.execSQL("update sourat set sum_kalima=(select sum(sum_kalima) from " + tableAyah + " where " + tableAyah + ".id_sourat = sourat.id_sourat ) " + cond2);
		
		Connection.db.execSQL("update quran_miracle_19 set num_val=(select sum(num_value) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_19 set sum_kal=(select count(id) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_19 set sum_har=(select sum(sum_harf) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set num_val=(select sum(num_value) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set sum_kal=(select count(id) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		Connection.db.execSQL("update quran_miracle_zawj set sum_har=(select sum(sum_harf) from kalima where id>=(select id from kalima where id_sourat=sourat_deb and id_ayah = ayah_deb and id_kalima = kalima_deb) and id<= (select id from kalima where id_sourat=sourat_fin and id_ayah = ayah_fin and id_kalima = kalima_fin))");
		
	}

}
