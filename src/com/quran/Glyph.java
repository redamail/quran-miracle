package com.quran;

public class Glyph
{

	int glyph_id;
	int page_number;
	int line_number;
	int sura_number;
	int ayah_number;
	int position;
	int min_x;
	int max_x;
	int min_y;
	int max_y;
	int type;
	int kalima_number;
	static final int GLYPH_TYPE_KALIMA=1;
	static final int GLYPH_TYPE_AYAH=3;
	static final int GLYPH_TYPE_HIZB=2;
	static final int GLYPH_TYPE_AUTRE=4;
	
	
	public Glyph(int glyph_id, int page_number, int line_number, int sura_number, int ayah_number, int position, int min_x, int max_x, int min_y, int max_y, int type, int kalima_number)
	{
		this.glyph_id = glyph_id;
		this.page_number = page_number;
		this.line_number = line_number;
		this.sura_number = sura_number;
		this.ayah_number = ayah_number;
		this.position = position;
		this.min_x = min_x;
		this.max_x = max_x;
		this.min_y = min_y;
		this.max_y = max_y;
		this.type = type;
		this.kalima_number = kalima_number;
	}

	public void setGlyph_id(int glyph_id)
	{
		this.glyph_id = glyph_id;
	}

	public int getGlyph_id()
	{
		return glyph_id;
	}

	public void setPage_number(int page_number)
	{
		this.page_number = page_number;
	}

	public int getPage_number()
	{
		return page_number;
	}

	public void setLine_number(int line_number)
	{
		this.line_number = line_number;
	}

	public int getLine_number()
	{
		return line_number;
	}

	public void setSura_number(int sura_number)
	{
		this.sura_number = sura_number;
	}

	public int getSura_number()
	{
		return sura_number;
	}

	public void setAyah_number(int ayah_number)
	{
		this.ayah_number = ayah_number;
	}

	public int getAyah_number()
	{
		return ayah_number;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	public int getPosition()
	{
		return position;
	}

	public void setMin_x(int min_x)
	{
		this.min_x = min_x;
	}

	public int getMin_x()
	{
		return min_x;
	}

	public void setMax_x(int max_x)
	{
		this.max_x = max_x;
	}

	public int getMax_x()
	{
		return max_x;
	}

	public void setMin_y(int min_y)
	{
		this.min_y = min_y;
	}

	public int getMin_y()
	{
		return min_y;
	}

	public void setMax_y(int max_y)
	{
		this.max_y = max_y;
	}

	public int getMax_y()
	{
		return max_y;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getType()
	{
		return type;
	}

	public void setKalima_number(int kalima_number)
	{
		this.kalima_number = kalima_number;
	}

	public int getKalima_number()
	{
		return kalima_number;
	}
	
	

}
