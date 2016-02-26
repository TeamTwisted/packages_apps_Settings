package com.android.settings.twisted;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import com.android.internal.logging.MetricsLogger;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.List;

public class TwistedRecentsSettings extends SettingsPreferenceFragment
        implements OnPreferenceChangeListener {

    private static final String IMMERSIVE_RECENTS = "immersive_recents";

    private ListPreference mImmersiveRecents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.twisted_recents);
        ContentResolver resolver = getActivity().getContentResolver();
        PreferenceScreen prefSet = getPreferenceScreen();

        mImmersiveRecents = (ListPreference) findPreference(IMMERSIVE_RECENTS);
        mImmersiveRecents.setValue(String.valueOf(Settings.System.getInt(
               getContentResolver(), Settings.System.IMMERSIVE_RECENTS, 0)));
        mImmersiveRecents.setSummary(mImmersiveRecents.getEntry());
        mImmersiveRecents.setOnPreferenceChangeListener(this);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.APPLICATION;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mImmersiveRecents) {
            Settings.System.putInt(getContentResolver(),
            Settings.System.IMMERSIVE_RECENTS, Integer.valueOf((String) objValue));
            mImmersiveRecents.setValue(String.valueOf(objValue));
            mImmersiveRecents.setSummary(mImmersiveRecents.getEntry());
        }
        return false;
    }
}

