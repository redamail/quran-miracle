package com.quran;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.view.ContextMenu.*;

public class PageActivity extends FragmentActivity implements ViewPager.OnPageChangeListener 
{

	ViewPager mPager;
	PageSlideAdapter mPagerAdapter;
	public static int NUM_PAGES = 1;
	int CALL_TYPE = Constant.PAGE_CALL_TYPE_PAGE;
	boolean selectAyah = false;
	static Ayah ayah;
	int activePage;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewpager_layout);

		NUM_PAGES = Connection.getNumPages();

		Intent intent = getIntent();
		int type = intent.getIntExtra(Constant.CALL_PARAM_TYPE, 0);

		if (type != 0)
		{
			CALL_TYPE = type;
		}
		int nopage=0;

		if (CALL_TYPE == Constant.PAGE_CALL_TYPE_PAGE)
		{
			int numpage = intent.getIntExtra(Constant.CALL_PARAM_NUM_PAGE, 0);
			if (numpage != 0)
			{
				nopage = numpage;
			}
		}
		int idsourat=0;
		int idayah=0;
		if (CALL_TYPE == Constant.PAGE_CALL_TYPE_AYAH)
		{
			idayah = intent.getIntExtra(Constant.CALL_PARAM_NUM_AYAH, 0);
			idsourat = intent.getIntExtra(Constant.CALL_PARAM_NUM_SOURAT, 0);

			if (idayah != 0 && idsourat != 0)
			{
				nopage = Connection.getNumPage(idsourat, idayah);
				ayah = Connection.getAyahAyahId(idsourat, idayah);
				selectAyah = true;
			}
		}

		if (CALL_TYPE == Constant.PAGE_CALL_TYPE_SOURAT)
		{
			idsourat = intent.getIntExtra(Constant.CALL_PARAM_NUM_SOURAT, 0);

			if (idsourat != 0)
			{
				nopage = Connection.getNumPage(idsourat, 1);
			}
		}

		mPager = getViewPager();
		int current_item = getPosFromArPage(1);
		if (nopage > 0)
		{
			current_item = getPosFromArPage(nopage);
		}
		mPager.setCurrentItem(current_item);
    }

	/*
	 @Override
	 public void onBackPressed(){

	 }
	 */

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Select action");
		menu.add(0, v.getId(), 0, "show selection");
		menu.add(0, v.getId(), 0, "cancel all selections");
		menu.add(0, v.getId(), 0, "add separator");
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getTitle()=="show selection"){showSelection();}
		else if(item.getTitle()=="cancel all selections"){cancelSelection();}
		else if(item.getTitle()=="add separator"){Selection.addSeparator();}
		else {return false;}
		return true;
	}
	
	public ViewPager getViewPager()
	{
		if (null == mPager)
		{
			mPager = (ViewPager) findViewById(R.id.pager);
			mPager.setOnPageChangeListener(this);
			mPagerAdapter = new PageSlideAdapter(getSupportFragmentManager(), this);
			mPager.setAdapter(mPagerAdapter);
		}

		return mPager;
	}

	public static int getArPageFromPos(int pos)
	{
		return NUM_PAGES - pos;
	}

	public static int getPosFromArPage(int page)
	{
		return NUM_PAGES - page;
	}

	public void saveCurrentPageNumber(int num)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("quran_current_quran_page", num); // value to store
		editor.commit();//This is needed or the edits will not be put into the prefs file
	}

	public void onPageScrolled(int p1, float p2, int p3)
	{
		// TODO: Implement this method
	}

	public void onPageSelected(int p1)
	{
		saveCurrentPageNumber(getArPageFromPos(p1));
		activePage = p1;
	}

	public void onPageScrollStateChanged(int p1)
	{
		// TODO: Implement this method
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.menu_page_show_selection:
				showSelection();
				return true;
			case R.id.menu_page_cancel_selection:
				cancelSelection();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void cancelSelection()
	{
		Selection.cancelSelection();
		refreshActiveFragments();
	}

	private void showSelection()
	{
		if (Selection.isSelectionOK() && Selection.selects.size() > 1)
		{
			SelectionDialog selectionDialog = new SelectionDialog(PageActivity.this);
			selectionDialog.show(this.getFragmentManager(), "selection");
		}
	}


	public void refreshActiveFragments()
	{
		PageSlideFragment frag;
		frag =  mPagerAdapter.getActiveFragment(mPager, activePage);
		if (frag != null)frag.refreshAll();
		frag =  mPagerAdapter.getActiveFragment(mPager, activePage - 1);
		if (frag != null)frag.refreshAll();
		frag =  mPagerAdapter.getActiveFragment(mPager, activePage + 1);
		if (frag != null)frag.refreshAll();
	}
}

	
