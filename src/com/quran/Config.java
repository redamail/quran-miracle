package com.quran;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Config
{
	private static int orientationType = Constant.ORIENTATION_TYPE_PORTRAIT;
	private static int quranSelectionRectsAlpha = 20;
	private static int quranSelectionRectsColor = Color.GRAY;
	private static int editAyahRectsAlpha = 20;
	private static int editAyahRectsColor = Color.RED;
	private static int quranSelectionAlpha = 100;
	private static int quran19MiracleAlpha = 100;
	private static int quranSelectionColor = Color.GREEN;
	private static boolean showQuran19Miracle = true;
	private static boolean showQuranSelectionRects = true;
	private static boolean showEditAyahRects = true;
	private static boolean canEditQuran19Miracle = false;
	private static String db2Adress = Constant.AYAHINFO_800_ADRESS;
	private static String db1Adress = Constant.QURAN_ADRESS;
	private static String imageAdress = Constant.IMAGE_1024_ADRESS;
	private static float textSize = Constant.TEXT_SIZE_20;
	private static float quranPagePortraitWidth = Constant.DEFAULT_QURAN_PAGE_PORTRAIT_WIDTH;
	private static float quranPagePortraitHeight = Constant.DEFAULT_QURAN_PAGE_PORTRAIT_HEIGHT;
	private static float quranPagePaysageWidth = Constant.DEFAULT_QURAN_PAGE_PAYSAGE_WIDTH;
	private static float quranPagePaysageHeight = Constant.DEFAULT_QURAN_PAGE_PAYSAGE_HEIGHT;

	public static void setQuranPagePaysageHeight(float quranPagePaysageHeight)
	{
		Config.quranPagePaysageHeight = quranPagePaysageHeight;
	}

	public static float getQuranPagePaysageHeight()
	{
		return quranPagePaysageHeight;
	}

	public static void setQuranPagePaysageWidth(float quranPagePaysageWidth)
	{
		Config.quranPagePaysageWidth = quranPagePaysageWidth;
	}

	public static float getQuranPagePaysageWidth()
	{
		return quranPagePaysageWidth;
	}

	public static void setQuranPagePortraitHeight(float quranPagePortraitHeight)
	{
		Config.quranPagePortraitHeight = quranPagePortraitHeight;
	}

	public static float getQuranPagePortraitHeight()
	{
		return quranPagePortraitHeight;
	}

	public static void setQuranPagePortraitWidth(float quranPagePortraitWidth)
	{
		Config.quranPagePortraitWidth = quranPagePortraitWidth;
	}

	public static float getQuranPagePortraitWidth()
	{
		return quranPagePortraitWidth;
	}
	
	
	public static void setTextSize(float textSize)
	{
		Config.textSize = textSize;
	}

	public static float getTextSize()
	{
		return textSize;
	}
	
	public static void setORIENTATION_TYPE(int oRIENTATION_TYPE)
	{
		orientationType = oRIENTATION_TYPE;
	}

	public static int getORIENTATION_TYPE()
	{
		return orientationType;
	}

	public static void setQURAN_SELECTION_RECTS_ALPHA(int qURAN_SELECTION_RECTS_ALPHA)
	{
		quranSelectionRectsAlpha = qURAN_SELECTION_RECTS_ALPHA;
	}

	public static int getQURAN_SELECTION_RECTS_ALPHA()
	{
		return quranSelectionRectsAlpha;
	}

	public static void setQURAN_SELECTION_RECTS_COLOR(int qURAN_SELECTION_RECTS_COLOR)
	{
		quranSelectionRectsColor = qURAN_SELECTION_RECTS_COLOR;
	}

	public static int getQURAN_SELECTION_RECTS_COLOR()
	{
		return quranSelectionRectsColor;
	}

	public static void setEDIT_AYAH_RECTS_ALPHA(int eDIT_AYAH_RECTS_ALPHA)
	{
		editAyahRectsAlpha = eDIT_AYAH_RECTS_ALPHA;
	}

	public static int getEDIT_AYAH_RECTS_ALPHA()
	{
		return editAyahRectsAlpha;
	}

	public static void setEDIT_AYAH_RECTS_COLOR(int eDIT_AYAH_RECTS_COLOR)
	{
		editAyahRectsColor = eDIT_AYAH_RECTS_COLOR;
	}

	public static int getEDIT_AYAH_RECTS_COLOR()
	{
		return editAyahRectsColor;
	}

	public static void setQURAN_SELECTION_ALPHA(int qURAN_SELECTION_ALPHA)
	{
		quranSelectionAlpha = qURAN_SELECTION_ALPHA;
	}

	public static int getQURAN_SELECTION_ALPHA()
	{
		return quranSelectionAlpha;
	}

	public static void setQURAN_19_MIRACLE_ALPHA(int qURAN_19_MIRACLE_ALPHA)
	{
		quran19MiracleAlpha = qURAN_19_MIRACLE_ALPHA;
	}

	public static int getQURAN_19_MIRACLE_ALPHA()
	{
		return quran19MiracleAlpha;
	}

	public static void setQURAN_SELECTION_COLOR(int qURAN_SELECTION_COLOR)
	{
		quranSelectionColor = qURAN_SELECTION_COLOR;
	}

	public static int getQURAN_SELECTION_COLOR()
	{
		return quranSelectionColor;
	}

	public static void setIMAGE_ADRESS(String iMAGE_ADRESS)
	{
		imageAdress = iMAGE_ADRESS;
	}

	public static String getIMAGE_ADRESS()
	{
		return imageAdress;
	}
	
	public static void setDB1_ADRESS(String dB1_ADRESS)
	{
		db1Adress = dB1_ADRESS;
	}

	public static String getDB1_ADRESS()
	{
		return db1Adress;
	}

	public static void setDB2_ADRESS(String dB2_ADRESS)
	{
		db2Adress = dB2_ADRESS;
	}

	public static String getDB2_ADRESS()
	{
		return db2Adress;
	}
	
	
	public static void setCAN_EDIT_QURAN_19_MIRACLE(boolean CAN_EDIT_QURAN_19_MIRACLE)
	{
		Config.canEditQuran19Miracle = CAN_EDIT_QURAN_19_MIRACLE;
	}

	public static boolean isCAN_EDIT_QURAN_19_MIRACLE()
	{
		return canEditQuran19Miracle;
	}

	public static void setSHOW_EDIT_AYAH_RECTS(boolean sHOW_EDIT_AYAH_RECTS)
	{
		showEditAyahRects = sHOW_EDIT_AYAH_RECTS;
	}

	public static boolean isSHOW_EDIT_AYAH_RECTS()
	{
		return showEditAyahRects;
	}
	
	
	public static void setSHOW_QURAN_19_MIRACLE(boolean sHOW_QURAN_19_MIRACLE)
	{
		showQuran19Miracle = sHOW_QURAN_19_MIRACLE;
	}

	public static boolean isSHOW_QURAN_19_MIRACLE()
	{
		return showQuran19Miracle;
	}

	
	public static void setSHOW_QURAN_SELECTION_RECTS(boolean sHOW_QURAN_SELECTION_RECTS)
	{
		showQuranSelectionRects = sHOW_QURAN_SELECTION_RECTS;
	}

	public static boolean isSHOW_QURAN_SELECTION_RECTS()
	{
		return showQuranSelectionRects;
	}
	
	public static float getQuranPageWidth(){
		float width = 0;
		switch(orientationType){
			case Constant.ORIENTATION_TYPE_PORTRAIT:
				width = Config.getQuranPagePortraitWidth();
				break;
			case Constant.ORIENTATION_TYPE_PAYSAGE:
				width = Config.getQuranPagePaysageWidth();
				break;
		}
		return width;
	}


	public static float getQuranPageHeight(){
		float height = 0;
		switch(orientationType){
			case Constant.ORIENTATION_TYPE_PORTRAIT:
				height = Config.getQuranPagePortraitHeight();
				break;
			case Constant.ORIENTATION_TYPE_PAYSAGE:
				height = Config.getQuranPagePaysageHeight();
				break;
		}
		return height;
	}

	public static float getDbPageWidth(){
		float width = Constant.DB_PAGE_WIDTH;	
		return width;
	}


	public static float getDbPageHeight(){
		float height = Constant.DB_PAGE_HEIGHT;
		return height;
	}

	public static float getImageWidth(){
		float width = Config.getQuranPageWidth() * Constant.IMAGE_SCREEN_RATIO;	
		return width;
	}
	
	public static float getImageHeight(){
		float height = Config.getQuranPageHeight() * Constant.IMAGE_SCREEN_RATIO;
		return height;
	}
	
	public static float getImageMinX(){
		float minX = (Config.getQuranPageWidth() - Config.getImageWidth())/2;
		return minX;
	}
	
	public static float getImageMinY(){
		float minY = (Config.getQuranPageHeight() - Config.getImageHeight())/2;
		return minY;
	}
	
	public static float getRatioImageWidthDb(){
		float ratio = Config.getImageWidth() / Config.getDbPageWidth();
		return ratio;
	}
	
	public static float getRatioImageHeightDb(){
		float ratio = Config.getImageHeight() / Config.getDbPageHeight();
		return ratio;
	}
	
	public static float getRatioImageWidthQuran(){
		float ratio = Config.getImageWidth() / Config.getQuranPageWidth();
		return ratio;
	}

	public static float getRatioImageHeightQuran(){
		float ratio = Config.getImageHeight() / Config.getQuranPageHeight();
		return ratio;
	}
	
	public static float getRatioQuranPageWidthDb(){
		float ratio = Config.getQuranPageWidth() / Config.getDbPageWidth();
		return ratio;
	}
	
	public static float getRatioQuranPageHeightDb(){
		float ratio = Config.getQuranPageHeight() / Config.getDbPageHeight();
		return ratio;
	}
	
	
	
}
