package com.quran;

public class Kalima
{
	int id;
	int id_sourat;
	int id_ayah; 
	int id_kalima;
	int kalima;
	int num_value;
	int sum_harf;

	public Kalima(int id, int id_sourat, int id_ayah, int id_kalima, int kalima, int num_value, int sum_harf)
	{
		this.id = id;
		this.id_sourat = id_sourat;
		this.id_ayah = id_ayah;
		this.id_kalima = id_kalima;
		this.kalima = kalima;
		this.num_value = num_value;
		this.sum_harf = sum_harf;
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

	public void setId_kalima(int id_kalima)
	{
		this.id_kalima = id_kalima;
	}

	public int getId_kalima()
	{
		return id_kalima;
	}

	public void setKalima(int kalima)
	{
		this.kalima = kalima;
	}

	public int getKalima()
	{
		return kalima;
	}

	public void setNum_value(int num_value)
	{
		this.num_value = num_value;
	}

	public int getNum_value()
	{
		return num_value;
	}

	public void setSum_harf(int sum_harf)
	{
		this.sum_harf = sum_harf;
	}

	public int getSum_harf()
	{
		return sum_harf;
	}

}
