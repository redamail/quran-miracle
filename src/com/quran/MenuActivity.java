
package com.quran;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import android.view.ViewGroup;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;
import android.view.Menu;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.*;
import android.content.*;

public class MenuActivity extends FragmentActivity
{

	ViewPager mPager;
	PagerAdapter mPagerAdapter;
	public static final int NUM_PAGES = 3;
	public static final int SOURAT_LIST_PAGE=2;
	public static final int QURAN_SEARCH_PAGE = 1;
	public static final int QUICK_GO_PAGE=0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewpager_layout);

		Display display= ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
		int width = display.getWidth(); 
		int height = display.getHeight(); 
		
		//System.out.println("width : "+width);
		//System.out.println("height : "+height);
		
		Config.setQuranPagePortraitWidth(width);
		Config.setQuranPagePortraitHeight(height);
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new MenuSlideAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this );
		mPager.setCurrentItem(NUM_PAGES-1);
		Config.setSHOW_QURAN_19_MIRACLE(prefs.getBoolean("show_quran_19_miracle", true));
		Config.setSHOW_QURAN_SELECTION_RECTS(prefs.getBoolean("show_Quran_selection_rects",true));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
			case R.id.menu_about:
				// Comportement du bouton "A Propos"
				return true;
			case R.id.menu_help:
				// Comportement du bouton "A Propos"
				return true;
			case R.id.menu_quran:
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
				int numPage = prefs.getInt("quran_current_quran_page", 1);
				showPage(numPage);
				return true;
			case R.id.menu_list_sourat:
				showFragment(MenuActivity.SOURAT_LIST_PAGE);
				return true;
			case R.id.menu_quick_go:
				showFragment(MenuActivity.QUICK_GO_PAGE);
				return true;
			case R.id.menu_quran_search:
				showFragment(MenuActivity.QURAN_SEARCH_PAGE);
				return true;
			case R.id.menu_settings:
				showSettings();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onPageSelected(int p1)
	{
		if(p1 == this.SOURAT_LIST_PAGE){
			
		}else
		if(p1 == this.QURAN_SEARCH_PAGE ){
			
		}else
		if(p1 == this.QUICK_GO_PAGE){
			
		}
	}

	public void showFragment(int fragId){
		this.mPager.setCurrentItem(fragId);
	}

	public void showSettings(){
		Intent intent = new Intent(this, PreferenceWithHeaders.class);
		startActivity(intent);
		
	}
	
	/*
	 @Override
	 public void onBackPressed(){

	 }
	 */

	private class MenuSlideAdapter extends FragmentPagerAdapter
	{

		public MenuSlideAdapter(FragmentManager fm)
		{
			super(fm);
		}


		@Override
		public Fragment instanciateItem(View container, int position)
		{
			MenuSlideFragment psf = new MenuSlideFragment(position);
			System.out.println("instanciateItem "+position);
			return psf;
		}		
		
		@Override
		public Fragment getItem(int position)
		{
			MenuSlideFragment psf = new MenuSlideFragment(position);
			System.out.println("getItem "+position);
			
			return psf;
		}

		@Override
		public int getCount()
		{
			return NUM_PAGES;
		}
	}
	
	public void showPage(int numpage)
	{
		Intent intent = new Intent(this, PageActivity.class);
		intent.putExtra(Constant.CALL_PARAM_NUM_PAGE, numpage);
		intent.putExtra(Constant.CALL_PARAM_TYPE, Constant.PAGE_CALL_TYPE_PAGE);
		startActivity(intent);
	}
	
}
	
