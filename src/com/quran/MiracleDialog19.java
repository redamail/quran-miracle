package com.quran;
import java.util.*;

public class MiracleDialog19 extends MiracleDialog
{
	public MiracleDialog19(PageActivity pageActivity, List<QuranGroup> qurangroups, QuranMiracle quranMiracle)
	{
		super(pageActivity, qurangroups, quranMiracle);
		showNumericValueInPart = true;
		showKalimaSumInPart = false;
		showHarfSumInPart = false;
		showNumericValueInGroup = true;
		showKalimaSumInGroup = false;
		showHarfSumInGroup = false;
	}
}
