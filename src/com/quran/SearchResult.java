package com.quran;

public class SearchResult
{

	private String idAyah;
	private String ayah;
	private String ayahinfo;
	private String idSourat;

	public SearchResult(String idSourat, String idAyah, String ayah, String ayahinfo){
		this.idAyah = idAyah;
		this.ayah = ayah;
		this.idSourat = idSourat;
		this.ayahinfo = ayahinfo;
	}

	public void setAyahinfo(String ayahinfo)
	{
		this.ayahinfo = ayahinfo;
	}

	public String getAyahinfo()
	{
		return ayahinfo;
	}

	public void setIdSourat(String idSourat)
	{
		this.idSourat = idSourat;
	}

	public String getIdSourat()
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

	public void setIdAyah(String idAyah)
	{
		this.idAyah = idAyah;
	}

	public String getIdAyah()
	{
		return idAyah;
	}
}


