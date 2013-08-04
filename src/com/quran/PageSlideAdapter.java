package com.quran;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager;

public class PageSlideAdapter extends FragmentPagerAdapter
{
	
	PageActivity pageActivity;
	FragmentManager fm;
	
	public PageSlideAdapter(FragmentManager fm, PageActivity pageActivity)
	{
		super(fm);
		this.fm = fm;
		this.pageActivity = pageActivity;
	}

	@Override
	public Fragment getItem(int position)
	{
		PageSlideFragment psf = new PageSlideFragment(PageActivity.getArPageFromPos(position), pageActivity);

		return psf;
	}

	/*
	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}
	*/

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		FragmentManager manager = ((Fragment)object).getFragmentManager();
		FragmentTransaction trans = manager.beginTransaction();
		trans.remove((Fragment)object);
		trans.commit();
	}

	@Override
	public int getCount()
	{
		return PageActivity.NUM_PAGES;
	}
	
	public PageSlideFragment getActiveFragment(ViewPager container, int position) {
		String name = makeFragmentName(container.getId(), position);
		
		return  (PageSlideFragment)fm.findFragmentByTag(name);
	}

	private static String makeFragmentName(int viewId, int index) {
		return "android:switcher:" + viewId + ":" + index;
	}
}
	

