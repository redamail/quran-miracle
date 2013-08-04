package com.quran;

public class Ayah
{
	int id;
	int id_sourat;
	int id_ayah;
	int sum_harf;
	int sum_kalima;
	int num_value;
	int status;
	String ayah;
	String ayah2;

	public Ayah(int id, int id_sourat, int id_ayah, int sum_harf, int sum_kalima, int num_value, int status, String ayah, String ayah2)
	{
		this.id = id;
		this.id_sourat = id_sourat;
		this.id_ayah = id_ayah;
		this.sum_harf = sum_harf;
		this.sum_kalima = sum_kalima;
		this.num_value = num_value;
		this.status = status;
		this.ayah = ayah;
		this.ayah2 = ayah2;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setId_sourat(int id_sourat)
	{
		this.id_sourat = id_sourat;
	}

	public int getId_sourat()
	{
		return id_sourat;
	}

	public void setId_ayah(int id_ayah)
	{
		this.id_ayah = id_ayah;
	}

	public int getId_ayah()
	{
		return id_ayah;
	}

	public void setSum_harf(int sum_harf)
	{
		this.sum_harf = sum_harf;
	}

	public int getSum_harf()
	{
		return sum_harf;
	}

	public void setSum_kalima(int sum_kalima)
	{
		this.sum_kalima = sum_kalima;
	}

	public int getSum_kalima()
	{
		return sum_kalima;
	}

	public void setNum_value(int num_value)
	{
		this.num_value = num_value;
	}

	public int getNum_value()
	{
		return num_value;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getStatus()
	{
		return status;
	}

	public void setAyah(String ayah)
	{
		this.ayah = ayah;
	}

	public String getAyah()
	{
		return ayah;
	}

	public void setAyah2(String ayah2)
	{
		this.ayah2 = ayah2;
	}

	public String getAyah2()
	{
		return ayah2;
	}

}
