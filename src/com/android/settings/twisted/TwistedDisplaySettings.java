package com.android.settings.twisted;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;

import com.android.internal.logging.MetricsLogger;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.List;

public class TwistedDisplaySettings extends SettingsPreferenceFragment {

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.APPLICATION;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.twisted_display);
    }
}

