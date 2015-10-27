package com.android.settings.twisted;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.MetricsLogger;

public class StatusBarSettings extends SettingsPreferenceFragment {


    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.DEVELOPMENT;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.twisted_statusbar);
    }
}

