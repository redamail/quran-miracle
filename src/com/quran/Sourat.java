package com.quran;

public class Sourat
{
	

	int id;
	int id_sourat; 
	String sourat; 
	int num_value;
	int sum_harf;
	int sum_ayah;
	int sum_kalima;

	public Sourat(int id, int id_sourat, String sourat, int num_value, int sum_harf, int sum_ayah, int sum_kalima)
	{
		this.id = id;
		this.id_sourat = id_sourat;
		this.sourat = sourat;
		this.num_value = num_value;
		this.sum_harf = sum_harf;
		this.sum_ayah = sum_ayah;
		this.sum_kalima = sum_kalima;
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

	public void setSourat(String sourat)
	{
		this.sourat = sourat;
	}

	public String getSourat()
	{
		return sourat;
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

	public void setSum_ayah(int sum_ayah)
	{
		this.sum_ayah = sum_ayah;
	}

	public int getSum_ayah()
	{
		return sum_ayah;
	}

	public void setSum_kalima(int sum_kalima)
	{
		this.sum_kalima = sum_kalima;
	}

	public int getSum_kalima()
	{
		return sum_kalima;
	}
	
	public String getInfo(){
		String info ="";
		info +=this.getSum_ayah()+" ";
		if(this.getSum_ayah()<11){
			info +=  "آيات";
		}else{
			info +=  "آية";	
		}
		info +=" "+this.getSum_kalima()+" ";
		if(this.getSum_kalima()<11){
			info +="كلمات";
		}else{
			info +="كلمة";
		}
		info +=" "+this.getSum_harf()+" ";
		if(this.getSum_harf()<11){
			info +="حروف";
		}else{
			info +="حرف";
		}
		return info;
	}
	
}
