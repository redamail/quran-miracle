package com.quran;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.content.Context;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import java.util.List;
import java.util.ArrayList;

public class AyahEditorActivity extends Activity
{

	Ayah ayah;
	EditText ayahEditText;
	TextView report;
	public List<String> message = new ArrayList<String>();
	private Handler handler;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//Selection.cancelSelection();
		Intent intent = getIntent();

		int idsourat=0;
		int idayah=0;
		idayah = intent.getIntExtra(Constant.CALL_PARAM_NUM_AYAH, 0);
		//System.out.println(idayah);
		//Log.i("id_ayah",String.valueOf(idayah));
		idsourat = intent.getIntExtra(Constant.CALL_PARAM_NUM_SOURAT, 0);
		//System.out.println(idsourat);
		//Log.i("id_sourat",String.valueOf(idsourat));

		if (idayah != 0 && idsourat != 0)
		{
			ayah = Connection.getAyahAyahId(idsourat, idayah);
		}

		this.setContentView(R.layout.ayah_editor_layout);
		
		ayahEditText = (EditText)findViewById(R.id.ayah_editor_text);
		String before = "ٱ";
		String after = "ا";
		ayah.setAyah2(ayah.getAyah2().replace(before, after));
		before = "آ";
		after = "ا";
		ayah.setAyah2(ayah.getAyah2().replace(before, after));
		ayahEditText.setText(ayah.getAyah2());
		
		report = (TextView)findViewById(R.id.ayah_editor_report);
		
		Button editButton = (Button) findViewById(R.id.ayah_editor_button);
		editButton.setOnClickListener(new Button.OnClickListener(){

				public void onClick(View p1)
				{
					editAyah(p1.getContext());
					// TODO: Implement this method
				}
		});
		
		Button updateHizbButton = (Button) findViewById(R.id.ayah_editor_button_update_hizb);
		updateHizbButton.setOnClickListener(new Button.OnClickListener(){

				public void onClick(View p1)
				{
					updateHizb(p1.getContext());
					// TODO: Implement this method
				}
		});		
		report.setMovementMethod(new ScrollingMovementMethod());
		handler = new Handler(){
			public void handleMessage(android.os.Message msg){
				switch(msg.what){
					case 0 : 
						report.append(message.get(0));
						message.remove(0);
						break;
					case 1 : 
						message.remove(0);
						break;
					case 2 :
						message.remove(0);
						break;
					case 3 : 
						message.remove(0);
						break;
				}	
			}
		};
	
	}
	
	public void editAyah(Context con){
		
		new Thread(new Runnable(){

				public void run()
				{
					ayah.setAyah2(ayahEditText.getText().toString());
					//Toast.makeText(con, ayahText, Toast.LENGTH_LONG);
					setMessage("Traitement ayah\n",0);
					Connection.updateAyahAyah2(ayah);
					setMessage("Traitement kalima\n",0);
					Connection.updateKalimaFromAyah(ayah);
					setMessage("Traitement harf\n",0);
					Connection.updateHarfFromAyah(ayah);
					setMessage("Traitement calcul\n",0);
					Connection.updateCalculFromAyah(ayah);
					setMessage("Fin traitement\n",0);
					
					// TODO: Implement this method
				}

		}).start();
	}
	
	public void updateHizb(Context con){

		new Thread(new Runnable(){

				public void run()
				{
					//ayah.setAyah2(ayahEditText.getText().toString());
					//Toast.makeText(con, ayahText, Toast.LENGTH_LONG);
					//System.out.println("Traitement ayah hizb\n");
					setMessage("Traitement ayah\n",0);
					Connection.updateHizb(ayah);
					setMessage("Fin traitement hizb\n",0);
				
					// TODO: Implement this method
				}

			}).start();
	}
	
	public void setMessage(String msg,int msgtype){
		message.add(msg);
		handler.sendEmptyMessage(msgtype);
	}
	
}
