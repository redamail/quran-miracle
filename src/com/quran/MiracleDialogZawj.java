package com.quran;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MiracleDialogZawj  extends MiracleDialog
{
	public MiracleDialogZawj(PageActivity pageActivity, List<QuranGroup> qurangroups, QuranMiracle quranMiracle)
	{
		super(pageActivity, qurangroups, quranMiracle);
		showNumericValueInPart = false;
		showKalimaSumInPart = false;
		showHarfSumInPart = false;
		showNumericValueInGroup = false;
		showKalimaSumInGroup = true;
		showHarfSumInGroup = false;
	}

	public void getQuranPartsView(QuranGroup group, List<LinearLayout> quranGroupTypeLayout)
	{

		QuranGroup distinctParts = getDistinctQuranParts(group);

		LinearLayout quranPartLayout = new LinearLayout(getActivity());
		quranPartLayout.setOrientation(LinearLayout.VERTICAL);
		TextView tvAyah = new TextView(getActivity());
		tvAyah.setTextDirection(TextView.TEXT_DIRECTION_RTL);
		tvAyah.setTextSize(Config.getTextSize());

		List<TextView> textviews = new ArrayList<TextView>();
		for (int i = 0; i < quranGroupTypeLayout.size(); i++)
		{
			textviews.add(new TextView(getActivity()));
			textviews.get(i).setTextDirection(TextView.TEXT_DIRECTION_RTL);
			textviews.get(i).setTextSize(Config.getTextSize());
			quranGroupTypeLayout.get(i).addView(textviews.get(i));
		}

		for (final QuranPart part:distinctParts.quranparts)
		{
			if (part.kalimaDeb.getId() != part.kalimaFin.getId())
			{
				textviews.get(part.type - 1).append(super.getAyahString(part) + " ");
			}
			else
			{
				textviews.get(part.type - 1).append(getAyahString(part) + " ");
			}
		}

		quranPartLayout.addView(tvAyah);
		quranGroupTypeLayout.get(0).addView(quranPartLayout);	
	}

	public String getAyahString(QuranPart part)
	{
		return part.kalimaDeb.kalima + "(" + part.nbRepit + ")";
	}

	public QuranGroup getDistinctQuranParts(QuranGroup group)
	{

		QuranGroup retour = group.clone();
		for (int i = 0;i < retour.quranparts.size(); i++)
		{	
			QuranPart part1 = retour.quranparts.get(i);
			for (int j = 0;j < retour.quranparts.size(); j++)
			{
				QuranPart part2 = retour.quranparts.get(j);
				if ((part1.id != part2.id) && (part1.kalimaDeb.getKalima().equalsIgnoreCase(part2.kalimaDeb.getKalima()) && part1.kalimaFin.getKalima().equalsIgnoreCase( part2.kalimaFin.getKalima())))
				{
					retour.quranparts.remove(part2);
					i--;
					break;
				}
			}
		}

		for (QuranPart part1: retour.quranparts)
		{
			for (QuranPart part2: group.quranparts)
			{
				if (part1.kalimaDeb.getKalima().equalsIgnoreCase(part2.kalimaDeb.getKalima()))
				{
					part1.nbRepit++;
				}
			}
		}
		return retour;
	}	
}
