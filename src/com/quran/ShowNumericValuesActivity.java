package com.quran;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import java.util.List;
import java.util.ArrayList;

public class ShowNumericValuesActivity extends Activity
{

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.show_numeric_values_layout);
		
		GridView gr1 = (GridView )findViewById(R.id.gridView1);
		gr1.setColumnWidth((int)Config.getQuranPageWidth());
		//gr1.setLayoutDirection(GridView.LAYOUT_DIRECTION_RTL);
		gr1.setAdapter( new ShowNumValAdapter(this, Connection.getGridList()));

	}
	
	
	
	
}

