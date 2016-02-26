package com.android.settings.twisted;

import com.android.internal.logging.MetricsLogger;
import android.os.Bundle;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class TwistedApps extends SettingsPreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.twisted_apps);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.DISPLAY;
    }
}
