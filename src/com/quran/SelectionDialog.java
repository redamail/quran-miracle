

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
import android.widget.ListView;
import android.widget.ListAdapter;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.graphics.Point;

public class SelectionDialog extends DialogFragment
{

	public PageActivity pageActivity;

	SelectionDialogAdapter adapter ;
	DragSortListView listview ;

	private DragSortListView.DropListener onDrop =
	new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to)
		{
			Select item = adapter.getItem(from);

			adapter.remove(item);
			adapter.insert(item, to);
		}
	};

	private DragSortListView.RemoveListener onRemove = 
	new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which)
		{
			adapter.remove(adapter.getItem(which));
			pageActivity.refreshActiveFragments();
		}
	};


	public SelectionDialog(PageActivity pageActivity)
	{
		this.pageActivity = pageActivity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();
		LinearLayout view = (LinearLayout)inflater.inflate(R.layout.selection_dialog_layout, null);
		
		listview = (DragSortListView) view.findViewById(R.id.selection_dialog_listview);

		listview.setDropListener(onDrop);
		listview.setRemoveListener(onRemove);

		adapter = new SelectionDialogAdapter(Selection.selects, pageActivity);

		listview.setAdapter(adapter);
		
		int numvaltot = 0;
		int sumkaltot = 0;
		int sumhartot = 0;

		for (Select sel: Selection.selects)
		{
			if (sel.selectionType != Selection.SELECTION_TYPE_SEPARATOR)
			{
				//add view hier
				int numval = Connection.getNumVal(sel.kalimaDeb, sel.kalimaFin);
				int sumkal = Connection.getSumKal(sel.kalimaDeb, sel.kalimaFin);
				int sumhar = Connection.getSumHar(sel.kalimaDeb, sel.kalimaFin);
				numvaltot += numval;
				sumkaltot += sumkal;
				sumhartot += sumhar;
			}
		}

		
		String numvaltotstr = "مجموع القيم العددية : " ;
		numvaltotstr += numvaltot + " = " + ((((numvaltot / 19) % 19) == 0) ?((numvaltot / 19 / 19) + " * 19"): (numvaltot / 19)) + " * 19 " + (((numvaltot % 19) > 0) ?" + " + (numvaltot % 19): "");
		String sumkaltotstr = "مجموع الكلمات : " ;
		sumkaltotstr += sumkaltot;
		String sumhartotstr = "مجموع حروف الكلمات : " ;
		sumhartotstr += sumhartot;
		TextView num_val_tot = (TextView)view.findViewById(R.id.selection_dialog_calcul_num_val_tot);
		num_val_tot.setText(numvaltotstr);
		num_val_tot.setTextSize(Config.getTextSize());
		TextView sum_kal_tot = (TextView)view.findViewById(R.id.selection_dialog_calcul_sum_kal_tot);
		sum_kal_tot.setText(sumkaltotstr);
		sum_kal_tot.setTextSize(Config.getTextSize());
		TextView sum_har_tot = (TextView)view.findViewById(R.id.selection_dialog_calcul_sum_har_tot);
		sum_har_tot.setText(sumhartotstr);
		sum_har_tot.setTextSize(Config.getTextSize());
		Button miracle19Button = (Button)view.findViewById(R.id.selection_dialog_calcul_button);
		miracle19Button.setText("من المعجزة ١٩");
		miracle19Button.setTextSize(Config.getTextSize());
		miracle19Button.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View p1)
				{
					addTo19Miracle();
				}
			});
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		return builder.create();
	}

	public void addTo19Miracle()
	{
		if (!Selection.selects.isEmpty())
		{
			Selection.updateSelectPositions();
			Selection.updateSelectType();
			boolean result = Connection.insertIntoQuranMiracle19(this.getActivity());
			if (result)
			{
				pageActivity.refreshActiveFragments();
			}
		}
	}

}
