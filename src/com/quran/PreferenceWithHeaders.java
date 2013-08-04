package com.quran;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.widget.Button;
import java.util.List;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CheckBox;
import android.content.SharedPreferences;

public class PreferenceWithHeaders extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add a button to the header list.
        /*
		if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("Some action");
            setListFooter(button);
        }
		*/
    }

    /**
     * Populate the activity with the top-level headers.
     */
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
		
    }
	
	@Override
    public void onDestroy() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		Config.setSHOW_QURAN_19_MIRACLE(sp.getBoolean("show_quran_19_miracle", true));
		Config.setSHOW_QURAN_SELECTION_RECTS(sp.getBoolean("show_Quran_selection_rects", true));
		Config.setCAN_EDIT_QURAN_19_MIRACLE(sp.getBoolean("edit_quran_19_miracle", true));
		super.onDestroy();
	
    }
	

    /**
     * This fragment shows the preferences for the first header.
     */
    public static class Prefs1Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.
            //PreferenceManager.setDefaultValues(getActivity(), R.xml.advanced_preferences, false);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.fragmented_preferences_1);
			/*
			CheckBox show_quran_19_miracle_check= (CheckBox)this.getView().findViewById(R.id.show_quran_19_miracle_check);
			show_quran_19_miracle_check.setActivated(Config.SHOW_QURAN_19_MIRACLE);
			CheckBox show_Quran_selection_rects_check = (CheckBox)this.getView().findViewById(R.id.show_Quran_selection_rects_check);
			show_Quran_selection_rects_check.setActivated(Config.SHOW_QURAN_SELECTION_RECTS);
			*/
        }
    }

    /**
     * This fragment contains a second-level set of preference that you
     * can get to by tapping an item in the first preferences fragment.
     */
    /*
	public static class Prefs1FragmentInner extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Can retrieve arguments from preference XML.
            Log.i("args", "Arguments: " + getArguments());

            // Load the preferences from an XML resource
           // addPreferencesFromResource(R.xml.fragmented_preferences_inner);
        }
    }
	*/
    /**
     * This fragment shows the preferences for the second header.
     */
    
	public static class Prefs2Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Can retrieve arguments from headers XML.
            //Log.i("args", "Arguments: " + getArguments());

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.fragmented_preferences_2);
        }
    }
	
	/**
     * This fragment shows the preferences for the first header.
     */
    public static class Prefs3Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.
            //PreferenceManager.setDefaultValues(getActivity(), R.xml.advanced_preferences, false);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.fragmented_preferences_3);
			/*
			 CheckBox show_quran_19_miracle_check= (CheckBox)this.getView().findViewById(R.id.show_quran_19_miracle_check);
			 show_quran_19_miracle_check.setActivated(Config.SHOW_QURAN_19_MIRACLE);
			 CheckBox show_Quran_selection_rects_check = (CheckBox)this.getView().findViewById(R.id.show_Quran_selection_rects_check);
			 show_Quran_selection_rects_check.setActivated(Config.SHOW_QURAN_SELECTION_RECTS);
			 */
        }
    }
	
	
}
