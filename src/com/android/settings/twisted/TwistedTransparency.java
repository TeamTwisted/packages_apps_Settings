package com.android.settings.twisted;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.database.ContentObserver;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import android.view.View;
import com.android.settings.benzo.SeekBarPreference;

import com.android.internal.logging.MetricsLogger;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class TwistedTransparency extends SettingsPreferenceFragment
        implements OnPreferenceChangeListener {

    private static final String PREF_QS_TRANSPARENT_SHADE = "qs_transparent_shade";
    private static final String PREF_QS_TRANSPARENT_HEADER = "qs_transparent_header";
    private static final String PREF_TRANSPARENT_VOLUME_DIALOG = "transparent_volume_dialog";

    private SeekBarPreference mQSShadeAlpha;
    private SeekBarPreference mQSHeaderAlpha;
    private SeekBarPreference mVolumeDialogAlpha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.twisted_transparency);

        PreferenceScreen prefSet = getPreferenceScreen();

            // QS shade alpha
            mQSShadeAlpha =
                  (SeekBarPreference) prefSet.findPreference(PREF_QS_TRANSPARENT_SHADE);
            int qSShadeAlpha = Settings.System.getInt(getContentResolver(),
                   Settings.System.QS_TRANSPARENT_SHADE, 255);
            mQSShadeAlpha.setValue(qSShadeAlpha / 1);
            mQSShadeAlpha.setOnPreferenceChangeListener(this);

            // QS header alpha
            mQSHeaderAlpha =
                    (SeekBarPreference) prefSet.findPreference(PREF_QS_TRANSPARENT_HEADER);
            int qSHeaderAlpha = Settings.System.getInt(getContentResolver(),
                    Settings.System.QS_TRANSPARENT_HEADER, 255);
            mQSHeaderAlpha.setValue(qSHeaderAlpha / 1);
            mQSHeaderAlpha.setOnPreferenceChangeListener(this);

            // Volume dialog alpha
            mVolumeDialogAlpha =
                    (SeekBarPreference) prefSet.findPreference(PREF_TRANSPARENT_VOLUME_DIALOG);
            int volumeDialogAlpha = Settings.System.getInt(getContentResolver(),
                    Settings.System.TRANSPARENT_VOLUME_DIALOG, 255);
            mVolumeDialogAlpha.setValue(volumeDialogAlpha / 1);
            mVolumeDialogAlpha.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mQSShadeAlpha) {
            int alpha = (Integer) objValue;
            Settings.System.putInt(getContentResolver(),
                    Settings.System.QS_TRANSPARENT_SHADE, alpha * 1);
            return true;
            } else if (preference == mQSHeaderAlpha) {
                int alpha = (Integer) objValue;
                Settings.System.putInt(getContentResolver(),
                        Settings.System.QS_TRANSPARENT_HEADER, alpha * 1);
                return true;
            } else if (preference == mVolumeDialogAlpha) {
                int alpha = (Integer) objValue;
                Settings.System.putInt(getContentResolver(),
                        Settings.System.TRANSPARENT_VOLUME_DIALOG, alpha * 1);
                return true;
        }
        return false;
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.APPLICATION;
    }
}