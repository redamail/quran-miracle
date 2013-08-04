

package com.quran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.content.Intent;

	public class CalculDialogGroupAdapter extends BaseAdapter
	{
		private Context context;
		private final List<Quran19Group> groups;
		private PageActivity pageActivity;

	public CalculDialogGroupAdapter (Context context, List<Quran19Group> groups, PageSlideFragment pageSlideFragment)
		{
			this.context = context;
			this.groups = groups;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			LinearLayout gridView;

			if (convertView == null)
			{

				gridView = new LinearLayout(context);
				
				LinearLayout selectionLayout = new LinearLayout(context);
				selectionLayout.setOrientation(LinearLayout.VERTICAL);
				gridView.addView(selectionLayout);

				int numvaltot = 0;
				int sumkaltot = 0;
				int sumhartot = 0;
				int compt = 0;

				for (final Select sel:Selection.selects)
				{
					//add view hier
					int numval = Connection.getNumVal(sel.kalimaDeb, sel.kalimaFin);
					int sumkal = Connection.getSumKal(sel.kalimaDeb, sel.kalimaFin);
					int sumhar = Connection.getSumHar(sel.kalimaDeb, sel.kalimaFin);
					numvaltot += numval;
					sumkaltot += sumkal;
					sumhartot += sumhar;
					String numvalstr = "القيمة العددية : " ;
					numvalstr += numval + " = " + (numval / 19) + " * 19 " + (((numval % 19) > 0) ?" + " + (numval % 19): "");
					String sumkalstr = "عدد الكلمات : " ;
					sumkalstr += sumkal;
					String sumharstr = "عدد حروف الكلمات : " ;
					sumharstr += sumhar;
					String ayah="";
					if (sel.selectionType == Selection.SELECTION_TYPE_AYAH)
					{
						ayah = sel.ayah.getAyah2() + "(" + sel.ayah.getId_ayah() + ") [" + Connection.getSouratNameFromId(sel.ayah.getId_sourat()) + "]";
					}
					else
					if (sel.selectionType == Selection.SELECTION_TYPE_GLYPH)
					{

						ayah = Connection.getKalimateText(sel.kalimaDeb, sel.kalimaFin);
					}
					LinearLayout selLayout = new LinearLayout(context);
					selLayout.setOrientation(LinearLayout.VERTICAL);
					/*
					CheckBox cbAyah = new CheckBox(context);
					cbAyah.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

							public void onCheckedChanged(CompoundButton p1, boolean p2)
							{
								if (p2)
								{
									updateMiracleType(sel, 2);
								}
								else
								{
									updateMiracleType(sel, 1);
								}
							}
						});
						*/
					TextView tvAyah = new TextView(context);
					tvAyah.setText(ayah);
					TextView num_val = new TextView(context);
					num_val.setText(numvalstr);
					num_val.setTextDirection(TextView.TEXT_DIRECTION_RTL);
					TextView sum_kal = new TextView(context);
					sum_kal.setText(sumkalstr);
					sum_kal.setTextDirection(TextView.TEXT_DIRECTION_RTL);
					TextView sum_har = new TextView(context);
					sum_har.setText(sumharstr);
					sum_har.setTextDirection(TextView.TEXT_DIRECTION_RTL);
					if ((compt % 2) > 0)
					{
						selLayout.setBackgroundColor(Color.LTGRAY);
						selLayout.setAlpha(0.6f);
						tvAyah.setTextColor(Color.BLACK);
						num_val.setTextColor(Color.BLACK);
						sum_kal.setTextColor(Color.BLACK);
						sum_har.setTextColor(Color.BLACK);
					} 

					tvAyah.setOnClickListener(new TextView.OnClickListener(){

							public void onClick(View p1)
							{
								int idayah = sel.kalimaDeb.getId_ayah();
								int idsourat = sel.kalimaDeb.getId_sourat();

								int numPage = Connection.getNumPage(idsourat, idayah);
								showPage2(numPage);

							}
						});

					if (sel.selectionType == Selection.SELECTION_TYPE_AYAH)
					{	
						tvAyah.setOnLongClickListener(new TextView.OnLongClickListener(){

								public boolean onLongClick(View p1)
								{
									Intent intent = new Intent(p1.getContext(), AyahEditorActivity.class);
									int idayah = sel.kalimaDeb.getId_ayah();
									int idsourat = sel.kalimaDeb.getId_sourat();
									intent.putExtra(Constant.CALL_PARAM_NUM_AYAH, idayah);
									intent.putExtra(Constant.CALL_PARAM_NUM_SOURAT, idsourat);
									p1.getContext().startActivity(intent);

									return false;
								}
							});	
					}

					//selLayout.addView(cbAyah);
					selLayout.addView(tvAyah);
					selLayout.addView(num_val);
					selLayout.addView(sum_kal);
					selLayout.addView(sum_har);
					selectionLayout.addView(selLayout);
					compt++;
				}

				LinearLayout calculTot = new LinearLayout(context);
				calculTot.setOrientation(LinearLayout.VERTICAL);
				calculTot.setBackgroundColor(Color.YELLOW);

				String numvalstr = "مجموع القيم العددية : " ;
				numvalstr += numvaltot + " = " + ((((numvaltot / 19) % 19) == 0) ?((numvaltot / 19 / 19) + " * 19"): (numvaltot / 19)) + " * 19 " + (((numvaltot % 19) > 0) ?" + " + (numvaltot % 19): "");
				String sumkalstr = "مجموع الكلمات : " ;
				sumkalstr += sumkaltot;
				String sumharstr = "مجموع حروف الكلمات : " ;
				sumharstr += sumhartot;
				TextView num_val_tot = new TextView(context);
				num_val_tot.setText(numvalstr);
				num_val_tot.setTextColor(Color.BLACK);
				num_val_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
				TextView sum_kal_tot = new TextView(context);
				sum_kal_tot.setText(sumkalstr);
				sum_kal_tot.setTextColor(Color.BLACK);
				sum_kal_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
				TextView sum_har_tot = new TextView(context);
				sum_har_tot.setText(sumharstr);
				sum_har_tot.setTextColor(Color.BLACK);
				sum_har_tot.setTextDirection(TextView.TEXT_DIRECTION_RTL);
				Button miracle19Button = new Button(context);
				miracle19Button.setText("من المعجزة ١٩");
				miracle19Button.setOnClickListener(new Button.OnClickListener(){
						public void onClick(View p1)
						{
							addTo19Miracle();
						}
					});
				calculTot.addView(num_val_tot);
				calculTot.addView(sum_kal_tot);
				calculTot.addView(sum_har_tot);
				calculTot.addView(miracle19Button);
				selectionLayout.addView(calculTot);
				
			}
			else
			{
				gridView = (LinearLayout)convertView;
			}

			return gridView;
		}

		@Override
		public int getCount()
		{
			return groups.size();
		}

		@Override
		public Quran19Group getItem(int position) {
			return groups.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}	
	
	public void addTo19Miracle()
	{
		boolean result = Connection.insertIntoQuranMiracle19(context);
		if (result)
		{
			pageActivity.refreshActiveFragments();
		}
	}

	public void deleteFrom19Miracle(Quran19Group group)
	{
		boolean result = Connection.delFromQuranMiracle19(context, group);
		if (result)
		{
			pageActivity.refreshActiveFragments();
		}
	}

	public void selectQuran19Group(Quran19Group group)
	{
		for(Quran19Part part : group.quran19parts){
			Selection.startSelect(part.kalimaDeb, part.glyphDeb, Selection.SELECTION_TYPE_GLYPH); 
			Selection.endSelect(part.kalimaFin, part.glyphFin, null);	
		}
		pageActivity.refreshActiveFragments();
	}

	public void addTotal()
	{

	}

	
	public void showPage(int numPage)
	{
		Intent intent = new Intent(context, PageActivity.class);
		intent.putExtra(Constant.CALL_PARAM_NUM_PAGE, numPage);
		intent.putExtra(Constant.CALL_PARAM_TYPE, Constant.PAGE_CALL_TYPE_PAGE);
		context.startActivity(intent);
	}

	public void showPage2(int numPage)
	{
		int current_item = PageActivity.getPosFromArPage(numPage);
		pageActivity.mPager.setCurrentItem(current_item);
	}

	/*
	public void updateMiracleType(Select pos, int type)
	{
		Selection.updateMiracleType(pos, type);
	}
	*/

	}

