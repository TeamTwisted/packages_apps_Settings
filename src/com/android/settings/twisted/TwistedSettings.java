package com.android.settings.twisted;
 
import android.os.Bundle;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import android.app.IActivityManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.ServiceManager;
import android.util.DisplayMetrics;
import android.preference.ListPreference;
import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.os.SystemProperties;
import android.util.Log;
import android.os.RemoteException;
import android.app.Activity;
import android.app.ActivityManagerNative;
import android.app.Dialog;
import android.app.IActivityManager;
 
public class TwistedSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
 	
  private static final String KEY_LCD_DENSITY = "lcd_density";
  private ListPreference mLcdDensityPreference;	
  private static final String TAG = "DisplaySettings";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.twisted_settings);

	// lcd densitty
        mLcdDensityPreference = (ListPreference) findPreference(KEY_LCD_DENSITY);
        int defaultDensity = DisplayMetrics.DENSITY_DEVICE;
        int currentDensity = DisplayMetrics.DENSITY_CURRENT;
        int currentIndex = -1;
        String[] densityEntries = new String[8];
        for (int idx = 0; idx < 8; ++idx) {
            int pct = (75 + idx*5);
            int val = defaultDensity * pct / 100;
            densityEntries[idx] = Integer.toString(val);
            if (pct == 100) {
                densityEntries[idx] += " (" + getResources().getString(R.string.lcd_density_default) + ")";
            }
            if (currentDensity == val) {
                currentIndex = idx;
            }
        }
        mLcdDensityPreference.setEntries(densityEntries);
        mLcdDensityPreference.setEntryValues(densityEntries);
        if (currentIndex != -1) {
            mLcdDensityPreference.setValueIndex(currentIndex);
        }
        mLcdDensityPreference.setOnPreferenceChangeListener(this);
        updateLcdDensityPreferenceDescription(currentDensity);

    	}

	private void updateLcdDensityPreferenceDescription(int currentDensity) {
        int defaultDensity = DisplayMetrics.DENSITY_DEVICE;
        ListPreference preference = mLcdDensityPreference;
        String summary;
        if (currentDensity < 10 || currentDensity >= 1000) {
            // Unsupported value
            summary = getResources().getString(R.string.lcd_density_unsupported);
        }
        else {
            summary = String.format(getResources().getString(R.string.lcd_density_summary),
                    currentDensity);
            if (currentDensity == defaultDensity) {
                summary += " (" + getResources().getString(R.string.lcd_density_default) + ")";
            }
        }
        preference.setSummary(summary);
    }

    public void writeLcdDensityPreference(final Context context, int value) {
        try {
            SystemProperties.set("persist.sys.lcd_density", Integer.toString(value));
        }
        catch (Exception e) {
            Log.e(TAG, "Unable to save LCD density");
            return;
        }
        final IActivityManager am = ActivityManagerNative.asInterface(ServiceManager.checkService("activity"));
        if (am != null) {
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    ProgressDialog dialog = new ProgressDialog(context);
                    dialog.setMessage(getResources().getString(R.string.restarting_ui));
                    dialog.setCancelable(false);
                    dialog.setIndeterminate(true);
                    dialog.show();
                }
                @Override
                protected Void doInBackground(Void... arg0) {
                    // Give the user a second to see the dialog
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        // Ignore
                   }
                    // Restart the UI
                    try {
                        am.restart();
                    }
                    catch (RemoteException e) {
                        Log.e(TAG, "Failed to restart");
                    }
                    return null;
                }
            };
            task.execute((Void[])null);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();	

	    if (KEY_LCD_DENSITY.equals(key)) {
            try {
                // The value must begin with a decimal number.  It may
                // optionally be follewed by a space and arbitrary text.
                String strValue = (String) objValue;
                int idx = strValue.indexOf(' ');
                if (idx > 0) {
                    strValue = strValue.substring(0, idx);
                }
                int value = Integer.parseInt(strValue);
                writeLcdDensityPreference(preference.getContext(), value);
                updateLcdDensityPreferenceDescription(value);
            }
            catch (NumberFormatException e) {
                Log.e(TAG, "could not persist display density setting", e);
            }
        }

  return true;
  }
}

