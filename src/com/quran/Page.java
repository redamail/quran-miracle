package com.quran;
import android.content.Context;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.Rect;
import java.util.List;
import java.util.ArrayList;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class Page extends View
{

	Paint paint = new Paint();
	Canvas canvas;
	Bitmap image;
	List<Rect> selection = new ArrayList<Rect>();
	List<Rect> quranmiracle19 = new ArrayList<Rect>();
	List<Rect> quranmiraclezawj = new ArrayList<Rect>();
	List<Rect> selectionRects = new ArrayList<Rect>();
	List<Rect> editAyahRects = new ArrayList<Rect>();
	//int [] colors = new int[6];
	PageSlideFragment fragment;

	public void setFragment(PageSlideFragment frag)
	{
		this.fragment = frag;
	}

	public Page(Context context, AttributeSet attr)
	{
		super(context, attr);
		//colors[0] = Color.RED;
		//colors[1] = Color.BLUE;
		//colors[2] = Color.CYAN;
		//colors[3] = Color.rgb(42,226,215);
		//colors[4] = Color.MAGENTA;
		//colors[5] = Color.YELLOW;
		//colors[6] = Color.RED;
		//colors[7] = Color.RED;
	}

	public void setImage(Bitmap image)
	{
		this.image = image;
	}

	@Override
	public void onDraw(Canvas canvas)
	{

		this.canvas = canvas;

		//affichage de la selection 
		if (!selection.isEmpty())
		{
			paint.setColor(Config.getQuranSelectionColor());
			paint.setAlpha(Config.getQuranSelectionAlpha());
			for (Rect rec : selection)
			{
				canvas.drawRect(rec, paint);
			}
		}

		if (Config.isShowQuranSelectionRects())
		{
			if (!selectionRects.isEmpty())
			{
				paint.setColor(Config.getQuranSelectionRectsColor());
				paint.setAlpha(Config.getQuranSelectionRectsAlpha());
				for (Rect rec : selectionRects)
				{
					canvas.drawRect(rec, paint);
				}
			}
		}
		
		//affichage des ayah editées
		if (Config.isShowEditAyahRects())
		{
			if (!editAyahRects.isEmpty())
			{
				paint.setColor(Config.getEditAyahRectsColor());
				paint.setAlpha(Config.getEditAyahRectsAlpha());
				for (Rect rec : editAyahRects)
				{
					canvas.drawRect(rec, paint);
				}
			}
		}
		
		// affichage du miracle du nombre 19
		if (Config.isShowQuranMiracle19())
		{
			for (QuranGroup qurangroup : fragment.quranmiracle19.qurangroups)
			{
				for (QuranPart part : qurangroup.quranparts)
				{
					paint.setColor(Config.getQuranMiracle19Color());
					paint.setAlpha(Config.getQuranMiracle19Alpha());
					for (Rect rec : part.getPageRect(fragment.numPage, 3, 3))
					{
						canvas.drawRect(rec, paint);
					}
				}
			}
		}
		
		// affichage du miracle zawj
		if (Config.isShowQuranMiracleZawj())
		{
			for (QuranGroup qurangroup : fragment.quranmiraclezawj.qurangroups)
			{
				for (QuranPart part : qurangroup.quranparts)
				{
					paint.setColor(Config.getQuranMiracleZawjColor());
					paint.setAlpha(Config.getQuranMiracleZawjAlpha());
					for (Rect rec : part.getPageRect(fragment.numPage, 0, 3))
					{
						canvas.drawRect(rec, paint);
					}
				}
			}
		}
		
		// affichage de l'image de la page
		if (image != null)
		{
			canvas.drawBitmap(image, (Config.getImageMinX()), (Config.getImageMinY()), new Paint());
		}

		// affichage des bordures de page
		if ((fragment.numPage % 2) != 0)
		{
			Rect lat = new Rect((int)Config.getQuranPageWidth() - 2, 0, (int)Config.getQuranPageWidth(), (int)Config.getQuranPageHeight());
			paint.setColor(Color.rgb(54, 0, 5));
			canvas.drawRect(lat, paint);

			paint.setColor(Color.rgb(24, 8, 55));
			for (int ii = 0; ii < 5; ii ++)
			{
				lat = new Rect(0 + ii , 0, 0 + ii + 1, (int)Config.getQuranPageHeight());
				paint.setAlpha(255 - (50 * ii));
				canvas.drawRect(lat, paint);

			}
		}
		else
		{

			Rect lat = new Rect(0, 0, 2, (int)Config.getQuranPageHeight());
			paint.setColor(Color.rgb(54, 0, 5));
			canvas.drawRect(lat, paint);

			paint.setColor(Color.rgb(24, 8, 55));
			for (int ii = 5; ii > 0; ii --)
			{
				lat = new Rect((int)Config.getQuranPageWidth() - ii , 0, (int)Config.getQuranPageWidth() - ii + 1, (int)Config.getQuranPageHeight());
				paint.setAlpha(255 - (50 * ii));
				canvas.drawRect(lat, paint);

			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{

		int desiredWidth = (int)Config.getQuranPageWidth();
		int desiredHeight = (int)Config.getQuranPageHeight();

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		//Measure Width
		if (widthMode == MeasureSpec.EXACTLY)
		{
			//Must be this size
			width = widthSize;
		}
		else if (widthMode == MeasureSpec.AT_MOST)
		{
			//Can't be bigger than...
			width = Math.min(desiredWidth, widthSize);
		}
		else
		{
			//Be whatever you want
			width = desiredWidth;
		}

		//Measure Height
		if (heightMode == MeasureSpec.EXACTLY)
		{
			//Must be this size
			height = heightSize;
		}
		else if (heightMode == MeasureSpec.AT_MOST)
		{
			//Can't be bigger than...
			height = Math.min(desiredHeight, heightSize);
		}
		else
		{
			//Be whatever you want
			height = desiredHeight;
		}

		//MUST CALL THIS
		setMeasuredDimension(width, height);
	}


}
