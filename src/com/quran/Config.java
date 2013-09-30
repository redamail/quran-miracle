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
	private static int quranMiracle19Alpha = 100;
	private static int quranMiracleZawjAlpha = 100;
	private static int quranMiracle19Color = Color.RED;
	private static int quranMiracleZawjColor = Color.BLUE;
	private static int quranSelectionColor = Color.GREEN;
	private static boolean showQuranMiracle19 = true;
	private static boolean showQuranMiracleZawj = true;
	private static boolean showQuranSelectionRects = true;
	private static boolean showEditAyahRects = true;
	private static boolean canEditQuranMiracle = false;
	private static String db2Adress = Constant.AYAHINFO_800_ADRESS;
	private static String db1Adress = Constant.QURAN_ADRESS;
	private static String imageAdress = Constant.IMAGE_800_ADRESS;
	private static float textSize = Constant.TEXT_SIZE_16;
	private static float quranPagePortraitWidth = Constant.DEFAULT_QURAN_PAGE_PORTRAIT_WIDTH;
	private static float quranPagePortraitHeight = Constant.DEFAULT_QURAN_PAGE_PORTRAIT_HEIGHT;
	private static float quranPagePaysageWidth = Constant.DEFAULT_QURAN_PAGE_PAYSAGE_WIDTH;
	private static float quranPagePaysageHeight = Constant.DEFAULT_QURAN_PAGE_PAYSAGE_HEIGHT;

	public static void setCanEditQuranMiracle(boolean canEditQuranMiracle)
	{
		Config.canEditQuranMiracle = canEditQuranMiracle;
	}

	public static boolean isCanEditQuranMiracle()
	{
		return canEditQuranMiracle;
	}

	public static void setShowQuranMiracleZawj(boolean showQuranMiracleZawj)
	{
		Config.showQuranMiracleZawj = showQuranMiracleZawj;
	}

	public static boolean isShowQuranMiracleZawj()
	{
		return showQuranMiracleZawj;
	}

	public static void setQuranMiracleZawjColor(int quranMiracleZawjColor)
	{
		Config.quranMiracleZawjColor = quranMiracleZawjColor;
	}

	public static int getQuranMiracleZawjColor()
	{
		return quranMiracleZawjColor;
	}
	
	public static void setQuranMiracleZawjAlpha(int quranMiracleZawjAlpha)
	{
		Config.quranMiracleZawjAlpha = quranMiracleZawjAlpha;
	}

	public static int getQuranMiracleZawjAlpha()
	{
		return quranMiracleZawjAlpha;
	}
	
	public static void setQuranMiracle19Color(int quranMiracle19Color)
	{
		Config.quranMiracle19Color = quranMiracle19Color;
	}

	public static int getQuranMiracle19Color()
	{
		return quranMiracle19Color;
	}

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
	
	public static void setOrientationType(int orientationType)
	{
		Config.orientationType = orientationType;
	}

	public static int getOrientationType()
	{
		return Config.orientationType;
	}

	public static void setQuranSelectionRectsAlpha (int quranSelectionRectsAlpha)
	{
		Config.quranSelectionRectsAlpha = quranSelectionRectsAlpha;
	}

	public static int getQuranSelectionRectsAlpha()
	{
		return quranSelectionRectsAlpha;
	}

	public static void setQuranSelectionRectsColor (int quranSelectionRectsColor)
	{
		Config.quranSelectionRectsColor = quranSelectionRectsColor;
	}

	public static int getQuranSelectionRectsColor()
	{
		return quranSelectionRectsColor;
	}

	public static void setEditAyahRectsAlpha (int editAyahRectsAlpha)
	{
		Config.editAyahRectsAlpha = editAyahRectsAlpha;
	}

	public static int getEditAyahRectsAlpha()
	{
		return editAyahRectsAlpha;
	}

	public static void setEditAyahRectsColor (int editAyahRectsColor)
	{
		Config.editAyahRectsColor = editAyahRectsColor;
	}

	public static int getEditAyahRectsColor()
	{
		return editAyahRectsColor;
	}

	public static void setQuranSelectionAlpha (int quranSelectionAlpha)
	{
		Config.quranSelectionAlpha = quranSelectionAlpha;
	}

	public static int getQuranSelectionAlpha()
	{
		return quranSelectionAlpha;
	}

	public static void setQuranMiracle19Alpha(int quranMiracle19Alpha)
	{
		Config.quranMiracle19Alpha = quranMiracle19Alpha;
	}

	public static int getQuranMiracle19Alpha()
	{
		return quranMiracle19Alpha;
	}

	public static void setQuranSelectionColor (int quranSelectionColor)
	{
		Config.quranSelectionColor = quranSelectionColor;
	}

	public static int getQuranSelectionColor()
	{
		return quranSelectionColor;
	}

	public static void setImageAdress(String imageAdress)
	{
		Config.imageAdress = imageAdress ;
	}

	public static String getImageAdress()
	{
		return imageAdress;
	}
	
	public static void setDb1Adress(String db1Adress)
	{
		Config.db1Adress = db1Adress;
	}

	public static String getDb1Adress ()
	{
		return db1Adress;
	}

	public static void setDb2Adress (String db2Adress)
	{
		Config.db2Adress = db2Adress ;
	}

	public static String getDb2Adress()
	{
		return db2Adress;
	}
	
	public static void setShowEditAyahRects (boolean showEditAyahRects)
	{
		Config.showEditAyahRects = showEditAyahRects ;
	}

	public static boolean isShowEditAyahRects ()
	{
		return showEditAyahRects;
	}
	
	
	public static void setShowQuranMiracle19 (boolean showQuranMiracle19 )
	{
		Config.showQuranMiracle19 = showQuranMiracle19 ;
	}

	public static boolean isShowQuranMiracle19 ()
	{
		return showQuranMiracle19;
	}

	
	public static void setShowQuranSelectionRects (boolean showQuranSelectionRects )
	{
		Config.showQuranSelectionRects = showQuranSelectionRects ;
	}

	public static boolean isShowQuranSelectionRects()
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
