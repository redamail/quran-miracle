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

public class CalculDialog extends DialogFragment
{

	public PageActivity pageActivity;
	public List<Quran19Group> quran19groups; 


	public CalculDialog(PageActivity pageActivity, List<Quran19Group> quran19groups)
	{
		this.pageActivity = pageActivity;
		this.quran19groups = quran19groups;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		LayoutInflater inflater = getActivity().getLayoutInflater();
		ScrollView scrollview = (ScrollView)inflater.inflate(R.layout.calcul_dialog_layout, null);
		LinearLayout view = (LinearLayout)scrollview.findViewById(R.id.calcul_dialog_linearlayout);

		LinearLayout quran19Layout = new LinearLayout(getActivity());
		quran19Layout .setOrientation(LinearLayout.VERTICAL);
		view.addView(quran19Layout);

		for (final Quran19Group quran19group : quran19groups)
		{
			LinearLayout quran19groupType1Layout = new LinearLayout(getActivity());
			quran19groupType1Layout.setOrientation(LinearLayout.VERTICAL);
			quran19Layout.addView(quran19groupType1Layout);
			LinearLayout quran19groupType2Layout = new LinearLayout(getActivity());
			quran19groupType2Layout.setOrientation(LinearLayout.VERTICAL);
			quran19Layout.addView(quran19groupType2Layout);

			int numvaltottype1 = 0;
			int sumkaltottype1 = 0;
			int sumhartottype1 = 0;
			int compttype1 = 0;

			int numvaltottype2 = 0;
			int sumkaltottype2 = 0;
			int sumhartottype2 = 0;
			int compttype2 = 0;


			for (final Quran19Part part:quran19group.quran19parts)
			{
				LinearLayout quran19partLayout = new LinearLayout(getActivity());
				quran19partLayout .setOrientation(LinearLayout.VERTICAL);

				//add view hier
				int numval = Connection.getNumVal(part.kalimaDeb, part.kalimaFin);
				int sumkal = Connection.getSumKal(part.kalimaDeb, part.kalimaFin);
				int sumhar = Connection.getSumHar(part.kalimaDeb, part.kalimaFin);
				String numvalstr = "القيمة العددية : " ;
				numvalstr += numval + " = " + (numval / 19) + " * 19 " + (((numval % 19) > 0) ?" + " + (numval % 19): "");
				String sumkalstr = "عدد الكلمات : " ;
				sumkalstr += sumkal;
				String sumharstr = "عدد حروف الكلمات : " ;
				sumharstr += sumhar;
				String ayah="";

				//recuperer le text du quran19part
				ayah = Connection.getKalimateText(part.kalimaDeb, part.kalimaFin);
				TextView tvAyah = new TextView(getActivity());
				tvAyah.setText(ayah);
				tvAyah.setTextDirection(TextView.TEXT_DIRECTION_RTL);
				tvAyah.setTextSize(Config.getTextSize());
				TextView num_val = new TextView(getActivity());
				num_val.setText(numvalstr);
				num_val.setTextDirection(TextView.TEXT_DIRECTION_RTL);
				num_val.setTextSize(Config.getTextSize());
				if ((numval % 19) == 0)
				{
					//num_val.setTextAppearance(num_val.getContext(),1); 
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
				if (part.type == 1)
				{

					if ((compttype1 % 2) > 0)
					{
						quran19partLayout.setBackgroundColor(Color.LTGRAY);
						quran19partLayout.setAlpha(0.6f);
						tvAyah.setTextColor(Color.BLACK);
						num_val.setTextColor(Color.BLACK);
						sum_kal.setTextColor(Color.BLACK);
						sum_har.setTextColor(Color.BLACK);
					}
				}

				if (part.type == 2)
				{

					if ((compttype2 % 2) > 0)
					{
						quran19partLayout.setBackgroundColor(Color.LTGRAY);
						quran19partLayout.setAlpha(0.6f);
						tvAyah.setTextColor(Color.BLACK);
						num_val.setTextColor(Color.BLACK);
						sum_kal.setTextColor(Color.BLACK);
						sum_har.setTextColor(Color.BLACK);
					}
				}
				tvAyah.setOnClickListener(new TextView.OnClickListener(){

						public void onClick(View p1)
						{
							int numPage = Connection.getNumPage(part.kalimaDeb.getId_sourat(), part.kalimaDeb.getId_ayah());
							showPage2(numPage);
						}
					});

				quran19partLayout.addView(tvAyah);
				quran19partLayout.addView(num_val);
				quran19partLayout.addView(sum_kal);
				quran19partLayout.addView(sum_har);

				if (part.type == 1)
				{
					sumhartottype1 += sumhar;
					numvaltottype1 += numval;
					sumkaltottype1 += sumkal;

					quran19groupType1Layout.addView(quran19partLayout);
					compttype1++;
				}
				if (part.type == 2)
				{
					sumhartottype2 += sumhar;
					numvaltottype2 += numval;
					sumkaltottype2 += sumkal;

					quran19groupType2Layout.addView(quran19partLayout);
					compttype2++;
				}
			}

			if (compttype1 > 0)
			{
				LinearLayout calculTotType1 = new LinearLayout(getActivity());
				calculTotType1.setOrientation(LinearLayout.VERTICAL);
				calculTotType1.setBackgroundColor(Color.YELLOW);

				String numvalstr = "مجموع القيم العددية : " ;
				numvalstr += numvaltottype1 + " = " + ((((numvaltottype1 / 19) % 19) == 0) ?((numvaltottype1 / 19 / 19) + " * 19"): (numvaltottype1 / 19)) + " * 19 " + (((numvaltottype1 % 19) > 0) ?" + " + (numvaltottype1 % 19): "");
				String sumkalstr = "مجموع الكلمات : " ;
				sumkalstr += sumkaltottype1;
				String sumharstr = "مجموع حروف الكلمات : " ;
				sumharstr += sumhartottype1;

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
				if ((numvaltottype1  % 19) == 0)
				{
					//num_val.setTextAppearance(num_val.getContext(),1); 
					num_val_tot.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
					num_val_tot.setBackgroundColor(Color.argb(90, 62, 102, 191));
				}
				
				calculTotType1.addView(num_val_tot);
				calculTotType1.addView(sum_kal_tot);
				calculTotType1.addView(sum_har_tot);
				quran19groupType1Layout.addView(calculTotType1);
			}
			if (compttype2 > 0)
			{
				LinearLayout calculTotType2 = new LinearLayout(getActivity());
				calculTotType2.setOrientation(LinearLayout.VERTICAL);
				calculTotType2.setBackgroundColor(Color.GREEN);

				String numvalstr = "مجموع القيم العددية : " ;
				numvalstr += numvaltottype2 + " = " + ((((numvaltottype2 / 19) % 19) == 0) ?((numvaltottype2 / 19 / 19) + " * 19"): (numvaltottype2 / 19)) + " * 19 " + (((numvaltottype2 % 19) > 0) ?" + " + (numvaltottype2 % 19): "");
				String sumkalstr = "مجموع الكلمات : " ;
				sumkalstr += sumkaltottype2;
				String sumharstr = "مجموع حروف الكلمات : " ;
				sumharstr += sumhartottype2;

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
				if ((numvaltottype2  % 19) == 0)
				{
					//num_val.setTextAppearance(num_val.getContext(),1); 
					num_val_tot.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
					num_val_tot.setBackgroundColor(Color.argb(90, 62, 102, 191));
				}
				
				calculTotType2.addView(num_val_tot);
				calculTotType2.addView(sum_kal_tot);
				calculTotType2.addView(sum_har_tot);
				quran19groupType2Layout.addView(calculTotType2);
			}

			LinearLayout buttonsLayout = new LinearLayout(getActivity());
			buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
			buttonsLayout.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
			buttonsLayout.setGravity(Gravity.RIGHT);
			if (Config.isCAN_EDIT_QURAN_19_MIRACLE())
			{
				Button nonMiracle19Button = new Button(getActivity());
				nonMiracle19Button.setText("حذف من المعجزة ١٩");
				nonMiracle19Button.setTextSize(Config.getTextSize());
				
				nonMiracle19Button.setOnClickListener(new Button.OnClickListener(){
						public void onClick(View p1)
						{
							deleteFrom19Miracle(quran19group);
						}
					});
				buttonsLayout.addView(nonMiracle19Button);
			}
			Button selectGroupe = new Button(getActivity());
			selectGroupe.setText("إختيار الكلمات");
			selectGroupe.setTextSize(Config.getTextSize());
			selectGroupe.setOnClickListener(new Button.OnClickListener(){
					public void onClick(View p1)
					{
						selectQuran19Group(quran19group);
					}
				});
			buttonsLayout.addView(selectGroupe);
			quran19Layout.addView(buttonsLayout);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(scrollview);
		return builder.create();
	}

	public void deleteFrom19Miracle(Quran19Group group)
	{
		boolean result = Connection.delFromQuranMiracle19(this.getActivity(), group);
		if (result)
		{
			pageActivity.refreshActiveFragments();
		}
	}

	public void selectQuran19Group(Quran19Group group)
	{
		for (Quran19Part part : group.quran19parts)
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
}
