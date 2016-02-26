package com.android.settings.twisted;

import com.android.internal.logging.MetricsLogger;

import android.os.Bundle;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class TwistedMiscSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String KEY_LOCK_CLOCK = "lock_clock";
    private static final String KEY_LOCK_CLOCK_PACKAGE_NAME = "com.cyanogenmod.lockclock";

    private PreferenceScreen mLockClock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.twisted_misc);

        PreferenceScreen prefSet = getPreferenceScreen();

    // mLockClock 
    	mLockClock = (PreferenceScreen) findPreference(KEY_LOCK_CLOCK);
        if (!Utils.isPackageInstalled(getActivity(), KEY_LOCK_CLOCK_PACKAGE_NAME)) {
            prefSet.removePreference(mLockClock);
    }
}

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.APPLICATION;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {

		// preference changes here
        return false;
    }
}
