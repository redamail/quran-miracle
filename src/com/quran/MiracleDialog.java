package com.quran;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.Dialog;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.graphics.Color;
import android.content.Intent;
import java.util.List;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ImageView;
import android.graphics.Typeface;
import android.view.Gravity;
import java.util.*;

public class MiracleDialog extends DialogFragment
{

	public PageActivity pageActivity;
	public List<QuranGroup> qurangroups; 
	public QuranMiracle quranMiracle;
	public int rootViewId = R.layout.miracle_dialog_layout;
	public boolean showNumericValueInPart = true;
	public boolean showKalimaSumInPart = true;
	public boolean showHarfSumInPart = true;
	public boolean showNumericValueInGroup = true;
	public boolean showKalimaSumInGroup = true;
	public boolean showHarfSumInGroup = true;

	public MiracleDialog(PageActivity pageActivity, List<QuranGroup> qurangroups, QuranMiracle quranMiracle)
	{
		this.pageActivity = pageActivity;
		this.qurangroups = qurangroups;
		this.quranMiracle = quranMiracle;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		View rootView = getRootView();
		LinearLayout view = (LinearLayout)rootView.findViewById(R.id.miracle_dialog_linearlayout);

		LinearLayout quranLayout = new LinearLayout(getActivity());
		quranLayout .setOrientation(LinearLayout.VERTICAL);
		view.addView(quranLayout);

		//QuranGroup contient une liste de QuranPart
		//
		for (final QuranGroup quranGroup : qurangroups)
		{
			quranLayout.addView(getQuranGroupView(quranGroup));
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(rootView);
		return builder.create();
	}

	public View getGroupButtonView(final QuranGroup quranGroup)
	{
		LinearLayout buttonsLayout = new LinearLayout(getActivity());
		buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
		buttonsLayout.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
		buttonsLayout.setGravity(Gravity.RIGHT);
		if (Config.isCanEditQuranMiracle())
		{
			if (quranMiracle instanceof QuranMiracle19)
			{
				Button nonMiracle19Button = new Button(getActivity());
				nonMiracle19Button.setText("حذف من المعجزة ١٩");
				nonMiracle19Button.setTextSize(Config.getTextSize());

				nonMiracle19Button.setOnClickListener(new Button.OnClickListener(){
						public void onClick(View p1)
						{
							deleteFromMiracle19(quranGroup);
						}
					});
				buttonsLayout.addView(nonMiracle19Button);
			}

			if (quranMiracle instanceof QuranMiracleZawj)
			{
				Button nonMiracleZawjButton = new Button(getActivity());
				nonMiracleZawjButton.setText("حذف من معجزة تكرار الكلمة");
				nonMiracleZawjButton.setTextSize(Config.getTextSize());

				nonMiracleZawjButton.setOnClickListener(new Button.OnClickListener(){
						public void onClick(View p1)
						{
							deleteFromMiracleZawj(quranGroup);
						}
					});
				buttonsLayout.addView(nonMiracleZawjButton);
			}
		}

		Button selectGroupe = new Button(getActivity());
		selectGroupe.setText("إختيار الكلمات");
		selectGroupe.setTextSize(Config.getTextSize());
		selectGroupe.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View p1)
				{
					selectQuranGroup(quranGroup);
				}
			});
		buttonsLayout.addView(selectGroupe);
		return buttonsLayout;
	}

	public View getTotalGroupType2View(QuranGroup group)
	{
		LinearLayout calculTotType2 = new LinearLayout(getActivity());
		calculTotType2.setOrientation(LinearLayout.VERTICAL);
		calculTotType2.setBackgroundColor(Color.GREEN);

		String numvalstr = "مجموع القيم العددية : " ;
		numvalstr += group.getNumVal(2) + " = " + ((((group.getNumVal(2) / 19) % 19) == 0) ?((group.getNumVal(2) / 19 / 19) + " * 19"): (group.getNumVal(2) / 19)) + " * 19 " + (((group.getNumVal(2) % 19) > 0) ?" + " + (group.getNumVal(2) % 19): "");
		TextView num_val_tot = new TextView(getActivity());
		num_val_tot.setText(numvalstr);
		num_val_tot.setTextColor(Color.BLACK);
		num_val_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		num_val_tot.setTextSize(Config.getTextSize());

		String sumkalstr = "مجموع الكلمات : " ;
		sumkalstr += group.getSumKal(2);
		TextView sum_kal_tot = new TextView(getActivity());
		sum_kal_tot.setText(sumkalstr);
		sum_kal_tot.setTextColor(Color.BLACK);
		sum_kal_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sum_kal_tot.setTextSize(Config.getTextSize());

		String sumharstr = "مجموع حروف الكلمات : " ;
		sumharstr += group.getSumHar(2);
		TextView sum_har_tot = new TextView(getActivity());
		sum_har_tot.setText(sumharstr);
		sum_har_tot.setTextColor(Color.BLACK);
		sum_har_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sum_har_tot.setTextSize(Config.getTextSize());
		if ((group.getNumVal(2)  % 19) == 0)
		{
			num_val_tot.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			num_val_tot.setBackgroundColor(Color.argb(90, 62, 102, 191));
		}

		if (showNumericValueInGroup)calculTotType2.addView(num_val_tot);
		if (showKalimaSumInGroup)calculTotType2.addView(sum_kal_tot);
		if (showHarfSumInGroup)calculTotType2.addView(sum_har_tot);
		return calculTotType2;
	}

	public View getTotalGroupType1View(QuranGroup group)
	{
		LinearLayout calculTotType1 = new LinearLayout(getActivity());
		calculTotType1.setOrientation(LinearLayout.VERTICAL);
		calculTotType1.setBackgroundColor(Color.YELLOW);

		String numvalstr = "مجموع القيم العددية : " ;
		numvalstr += group.getNumVal(1) + " = " + ((((group.getNumVal(1) / 19) % 19) == 0) ?((group.getNumVal(1) / 19 / 19) + " * 19"): (group.getNumVal(1) / 19)) + " * 19 " + (((group.getNumVal(1) % 19) > 0) ?" + " + (group.getNumVal(1) % 19): "");
		String sumkalstr = "مجموع الكلمات : " ;
		sumkalstr += group.getSumKal(1);
		String sumharstr = "مجموع حروف الكلمات : " ;
		sumharstr += group.getSumHar(1);

		TextView num_val_tot = new TextView(getActivity());
		num_val_tot.setText(numvalstr);
		num_val_tot.setTextColor(Color.BLACK);
		num_val_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		num_val_tot.setTextSize(Config.getTextSize());
		TextView sum_kal_tot = new TextView(getActivity());
		sum_kal_tot.setText(sumkalstr);
		sum_kal_tot.setTextColor(Color.BLACK);
		sum_kal_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sum_kal_tot.setTextSize(Config.getTextSize());
		TextView sum_har_tot = new TextView(getActivity());
		sum_har_tot.setText(sumharstr);
		sum_har_tot.setTextColor(Color.BLACK);
		sum_har_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sum_har_tot.setTextSize(Config.getTextSize());
		if ((group.getNumVal(1)  % 19) == 0)
		{
			num_val_tot.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			num_val_tot.setBackgroundColor(Color.argb(90, 62, 102, 191));
		}

		if (showNumericValueInGroup)calculTotType1.addView(num_val_tot);
		if (showKalimaSumInGroup)calculTotType1.addView(sum_kal_tot);
		if (showHarfSumInGroup)calculTotType1.addView(sum_har_tot);

		return calculTotType1;
	}

	public View getTotalGroupTypeView(QuranGroup group, int type)
	{
		LinearLayout calculTotType = new LinearLayout(getActivity());
		calculTotType.setOrientation(LinearLayout.VERTICAL);
		calculTotType.setBackgroundColor(Color.YELLOW);

		String numvalstr = "مجموع القيم العددية : " ;
		numvalstr += group.getNumVal(type) + " = " + ((((group.getNumVal(type) / 19) % 19) == 0) ?((group.getNumVal(type) / 19 / 19) + " * 19"): (group.getNumVal(type) / 19)) + " * 19 " + (((group.getNumVal(type) % 19) > 0) ?" + " + (group.getNumVal(type) % 19): "");
		String sumkalstr = "مجموع الكلمات : " ;
		sumkalstr += group.getSumKal(type);
		String sumharstr = "مجموع حروف الكلمات : " ;
		sumharstr += group.getSumHar(type);

		TextView num_val_tot = new TextView(getActivity());
		num_val_tot.setText(numvalstr);
		num_val_tot.setTextColor(Color.BLACK);
		num_val_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		num_val_tot.setTextSize(Config.getTextSize());
		TextView sum_kal_tot = new TextView(getActivity());
		sum_kal_tot.setText(sumkalstr);
		sum_kal_tot.setTextColor(Color.BLACK);
		sum_kal_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sum_kal_tot.setTextSize(Config.getTextSize());
		TextView sum_har_tot = new TextView(getActivity());
		sum_har_tot.setText(sumharstr);
		sum_har_tot.setTextColor(Color.BLACK);
		sum_har_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sum_har_tot.setTextSize(Config.getTextSize());
		if ((group.getNumVal(type) % 19) == 0)
		{
			num_val_tot.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			num_val_tot.setBackgroundColor(Color.argb(90, 62, 102, 191));
		}

		if (showNumericValueInGroup)calculTotType.addView(num_val_tot);
		if (showKalimaSumInGroup)calculTotType.addView(sum_kal_tot);
		if (showHarfSumInGroup)calculTotType.addView(sum_har_tot);

		return calculTotType;
	}

	public View getQuranGroupView(final QuranGroup group)
	{
		LinearLayout quranGroupLayout = new LinearLayout(getActivity());
		quranGroupLayout.setOrientation(LinearLayout.VERTICAL);

		List<LinearLayout> quranGroupTypeLayout = new ArrayList<LinearLayout>();
		System.out.println(group.getMaxType());
		for (int i=0; i < group.getMaxType(); i++)
		{
			LinearLayout typeLayout = new LinearLayout(getActivity());
			typeLayout.setOrientation(LinearLayout.VERTICAL);
			quranGroupLayout.addView(typeLayout);
			quranGroupTypeLayout.add(typeLayout);
		}

		LinearLayout separatorView = new LinearLayout(getActivity());
		separatorView.setBackgroundColor(Color.WHITE);
		quranGroupLayout.addView(separatorView);
		LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams) separatorView.getLayoutParams(); 
		params.height = 10; 
		separatorView.setLayoutParams(params);

		getQuranPartsView(group, quranGroupTypeLayout);
		
		for (int i=0; i < quranGroupTypeLayout.size(); i++)
		{
			if (group.getCount(i + 1) > 0)
			{
				quranGroupTypeLayout.get(i).addView(getTotalGroupTypeView(group, i + 1));
			}
		}

		quranGroupLayout.addView(getGroupButtonView(group));

		return quranGroupLayout;
	}
	
	public void getQuranPartsView(QuranGroup group, List<LinearLayout> quranGroupTypeLayout){
		for (final QuranPart part:group.quranparts)
		{
			LinearLayout quranPartLayout =  (LinearLayout) getQuranPartView(part); 
			quranGroupTypeLayout.get(part.type - 1).addView(quranPartLayout);
		}	
	}

	public View getQuranPartView(final QuranPart part)
	{
		LinearLayout quranpartLayout = new LinearLayout(getActivity());
		quranpartLayout .setOrientation(LinearLayout.VERTICAL);

		//add view hier
		String numvalstr = "القيمة العددية : " ;
		numvalstr += part.numVal + " = " + (part.numVal / 19) + " * 19 " + (((part.numVal % 19) > 0) ?" + " + (part.numVal % 19): "");
		String sumkalstr = "عدد الكلمات : " ;
		sumkalstr += part.sumKal;
		String sumharstr = "عدد حروف الكلمات : " ;
		sumharstr += part.sumHar;
		String ayah="";

		//recuperer le text du quran19part
		TextView tvAyah = new TextView(getActivity());
		ayah = getAyahString(part);
		tvAyah.setText(ayah);
		tvAyah.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		tvAyah.setTextSize(Config.getTextSize());
		TextView num_val = new TextView(getActivity());
		num_val.setText(numvalstr);
		num_val.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		num_val.setTextSize(Config.getTextSize());
		if ((part.numVal % 19) == 0)
		{
			num_val.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			num_val.setBackgroundColor(Color.argb(90, 62, 102, 191));
		}
		TextView sum_kal = new TextView(getActivity());
		sum_kal.setText(sumkalstr);
		sum_kal.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sum_kal.setTextSize(Config.getTextSize());
		TextView sum_har = new TextView(getActivity());
		sum_har.setText(sumharstr);
		sum_har.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		sum_har.setTextSize(Config.getTextSize());
	
		tvAyah.setOnClickListener(new TextView.OnClickListener(){

				public void onClick(View p1)
				{
					int numPage = Connection.getNumPage(part.kalimaDeb.getId_sourat(), part.kalimaDeb.getId_ayah());
					showPage2(numPage);
				}
			});

		quranpartLayout.addView(tvAyah);
		if (showNumericValueInPart)quranpartLayout.addView(num_val);
		if (showKalimaSumInPart)quranpartLayout.addView(sum_kal);
		if (showHarfSumInPart)quranpartLayout.addView(sum_har);

		return quranpartLayout;
	}

	public String getAyahString(QuranPart part)
	{
		return Connection.getKalimateText(part.kalimaDeb, part.kalimaFin);
	}
	
	public View getRootView()
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View rootView = inflater.inflate(rootViewId, null);
		return rootView;
	}

	public void deleteFromMiracle19(QuranGroup group)
	{
		boolean result = Connection.delFromQuranMiracle19(this.getActivity(), group);
		if (result)
		{
			pageActivity.refreshActiveFragments();
		}
	}

	public void deleteFromMiracleZawj(QuranGroup group)
	{
		boolean result = Connection.delFromQuranMiracleZawj(this.getActivity(), group);
		if (result)
		{
			pageActivity.refreshActiveFragments();
		}
	}

	public void selectQuranGroup(QuranGroup group)
	{
		for (QuranPart part : group.quranparts)
		{
			Selection.startSelect(part.kalimaDeb, part.glyphDeb, Selection.SELECTION_TYPE_GLYPH); 
			Selection.endSelect(part.kalimaFin, part.glyphFin, null);	
		}
		pageActivity.refreshActiveFragments();
	}

	public void showPage(int numPage)
	{
		Intent intent = new Intent(this.getActivity(), PageActivity.class);
		intent.putExtra(Constant.CALL_PARAM_NUM_PAGE, numPage);
		intent.putExtra(Constant.CALL_PARAM_TYPE, Constant.PAGE_CALL_TYPE_PAGE);
		startActivity(intent);
	}

	public void showPage2(int numPage)
	{
		int current_item = PageActivity.getPosFromArPage(numPage);
		pageActivity.mPager.setCurrentItem(current_item);
	}

	class Calcul
	{
		int numvaltottype1 = 0;
		int sumkaltottype1 = 0;
		int sumhartottype1 = 0;
		int compttype1 = 0;

		int numvaltottype2 = 0;
		int sumkaltottype2 = 0;
		int sumhartottype2 = 0;
		int compttype2 = 0;
	}
}


