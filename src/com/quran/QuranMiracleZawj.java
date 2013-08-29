package com.quran;

import android.util.Log;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Rect;

public class QuranMiracleZawj extends QuranMiracle
{

	public QuranMiracleZawj(int page)
	{
		miracleTable ="quran_miracle_zawj";
		load(page);
	}
}
