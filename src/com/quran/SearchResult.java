package com.quran;

public class SearchResult
{

	private int idAyah;
	private String ayah;
	private String ayahinfo;
	private int idSourat;
	private int idKalima;
	private String kalima;

	public SearchResult(int idSourat, int idAyah, String ayah, String ayahinfo, int idKalima, String kalima){
		this.idAyah = idAyah;
		this.ayah = ayah;
		this.idSourat = idSourat;
		this.ayahinfo = ayahinfo;
		this.idKalima = idKalima;
		this.kalima = kalima;
	}

	public void setIdKalima(int idKalima)
	{
		this.idKalima = idKalima;
	}

	public int getIdKalima()
	{
		return idKalima;
	}

	public void setKalima(String kalima)
	{
		this.kalima = kalima;
	}

	public String getKalima()
	{
		return kalima;
	}

	public void setAyahinfo(String ayahinfo)
	{
		this.ayahinfo = ayahinfo;
	}

	public String getAyahinfo()
	{
		return ayahinfo;
	}

	public void setIdSourat(int idSourat)
	{
		this.idSourat = idSourat;
	}

	public int getIdSourat()
	{
		return idSourat;
	}

	public void setAyah(String ayah)
	{
		this.ayah = ayah;
	}

	public String getAyah()
	{
		return ayah;
	}

	public void setIdAyah(int idAyah)
	{
		this.idAyah = idAyah;
	}

	public int getIdAyah()
	{
		return idAyah;
	}
}


