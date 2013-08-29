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


public class AllMiracleDialog  extends DialogFragment
{
	public PageActivity pageActivity;
	public QuranMiracle19 quranmiracle19;
	public QuranMiracleZawj quranmiraclezawj;
	public List<QuranGroup> qurangroups19;
	public List<QuranGroup> qurangroupszawj;
	public Glyph glyph;
	public int rootViewId = R.layout.all_miracle_dialog_layout;
	public boolean showNumericValueInPart = true;
	public boolean showKalimaSumInPart = true;
	public boolean showHarfSumInPart = true;
	public boolean showNumericValueInGroup = true;
	public boolean showKalimaSumInGroup = true;
	public boolean showHarfSumInGroup = true;
	public boolean showRepitNumInGroup = true;

	public AllMiracleDialog(PageActivity pageActivity, Glyph glyph, QuranMiracle19 quranmiracle19, QuranMiracleZawj quranmiraclezawj, List<QuranGroup> qurangroups19, List<QuranGroup> qurangroupszawj)
	{
		this.pageActivity = pageActivity;
		this.glyph = glyph;
		this.quranmiracle19 = quranmiracle19;
		this.quranmiraclezawj = quranmiraclezawj;
		this.qurangroups19 = qurangroups19;
		this.qurangroupszawj = qurangroupszawj;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{	
		View rootView = getRootView();
		LinearLayout view = (LinearLayout)rootView.findViewById(R.id.all_miracle_dialog_linearlayout);

		LinearLayout quranLayout = new LinearLayout(getActivity());
		quranLayout.setOrientation(LinearLayout.VERTICAL);
		view.addView(quranLayout);

		//QuranGroup contient une liste de QuranPart
		//

		if (Config.isShowQuranMiracle19())
		{
			//qurangroups19 = quranmiracle19.isGlyphInQuranMiracle(glyph);

			if (qurangroups19 != null && !qurangroups19.isEmpty())
			{
				Button groupbutton = new Button(getActivity()); 
				groupbutton.setText("عرض معجزة العدد ١٩ في القرآن الكريم");
				groupbutton.setTextDirection(TextView.TEXT_DIRECTION_RTL);
				groupbutton.setTextSize(Config.getTextSize());
				groupbutton.setOnClickListener(new Button.OnClickListener(){
						public void onClick(View p1)
						{
							MiracleDialog calc = new MiracleDialog19(pageActivity, qurangroups19, quranmiracle19);
							calc.show(getActivity().getFragmentManager(), "miracle19");	
						}
					});
				quranLayout.addView(groupbutton);
			}
		}

		if (Config.isShowQuranMiracleZawj())
		{
			//qurangroupszawj = quranmiraclezawj.isGlyphInQuranMiracle(glyph);

			if (qurangroupszawj != null && !qurangroupszawj.isEmpty())
			{
				Button groupbutton = new Button(getActivity()); 
				groupbutton.setText("عرض معجزة تكرار الكلمة في القرآن الكريم");
				groupbutton.setTextDirection(TextView.TEXT_DIRECTION_RTL);
				groupbutton.setTextSize(Config.getTextSize());
				groupbutton.setOnClickListener(new Button.OnClickListener(){
						public void onClick(View p1)
						{
							MiracleDialog calc = new MiracleDialogZawj(pageActivity, qurangroupszawj, quranmiraclezawj);
							calc.show(getActivity().getFragmentManager(), "miracle19");	
						}
					});
				quranLayout.addView(groupbutton);
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(rootView);
		return builder.create();
	}

	public View getRootView()
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View rootView = inflater.inflate(rootViewId, null);
		return rootView;
	}
}


