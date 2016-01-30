package com.android.settings.twisted;

import android.os.Bundle;
import android.preference.SwitchPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.slim.DeviceUtils;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;

public class TwistedNavBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String TAG = "NavBar";
    private static final String ENABLE_NAVIGATION_BAR = "enable_nav_bar";
    private static final String PREF_BUTTON = "navbar_button_settings";
    private static final String PREF_STYLE_DIMEN = "navbar_style_dimen_settings";
    private static final String PREF_NAVIGATION_BAR_CAN_MOVE = "navbar_can_move";
    private static final String STATUS_BAR_IME_ARROWS = "status_bar_ime_arrows";

    private int mNavBarMenuDisplayValue;

    SwitchPreference mEnableNavigationBar;
    SwitchPreference mNavigationBarCanMove;
    PreferenceScreen mButtonPreference;
    PreferenceScreen mStyleDimenPreference;
    SwitchPreference mStatusBarImeArrows;

    @Override
    protected int getMetricsCategory() {
        // todo add a constant in MetricsLogger.java
        return MetricsLogger.INPUTMETHOD_LANGUAGE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.twisted_navbar);

        PreferenceScreen prefs = getPreferenceScreen();

        mButtonPreference = (PreferenceScreen) findPreference(PREF_BUTTON);
        mStyleDimenPreference = (PreferenceScreen) findPreference(PREF_STYLE_DIMEN);

        // A Nexus will always have a NavBar by default
        boolean enableNavigationBar = Settings.System.getInt(getContentResolver(),
                Settings.System.NAVIGATION_BAR_SHOW, 1) == 1;
        mEnableNavigationBar = (SwitchPreference) findPreference(ENABLE_NAVIGATION_BAR);
        mEnableNavigationBar.setChecked(enableNavigationBar);
        mEnableNavigationBar.setOnPreferenceChangeListener(this);

        mNavigationBarCanMove = (SwitchPreference) findPreference(PREF_NAVIGATION_BAR_CAN_MOVE);
        mNavigationBarCanMove.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.NAVIGATION_BAR_CAN_MOVE,
                DeviceUtils.isPhone(getActivity()) ? 1 : 0) == 0);
        mNavigationBarCanMove.setOnPreferenceChangeListener(this);

        mStatusBarImeArrows = (SwitchPreference) findPreference(STATUS_BAR_IME_ARROWS);
        mStatusBarImeArrows.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.STATUS_BAR_IME_ARROWS, 0) == 1);
        mStatusBarImeArrows.setOnPreferenceChangeListener(this);

        updateNavbarPreferences(enableNavigationBar);
    }

    private void updateNavbarPreferences(boolean show) {
        mButtonPreference.setEnabled(show);
        mStyleDimenPreference.setEnabled(show);
        mNavigationBarCanMove.setEnabled(show);
       mStatusBarImeArrows.setEnabled(show);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mEnableNavigationBar) {
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.NAVIGATION_BAR_SHOW,
                    ((Boolean) newValue) ? 1 : 0);
            updateNavbarPreferences((Boolean) newValue);
            return true;
        } else if (preference == mNavigationBarCanMove) {
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.NAVIGATION_BAR_CAN_MOVE,
                    ((Boolean) newValue) ? 0 : 1);
            return true;
        } else if (preference == mStatusBarImeArrows) {
            Settings.System.putInt(getActivity().getContentResolver(),
                Settings.System.STATUS_BAR_IME_ARROWS,
                    ((Boolean) newValue) ? 1 : 0);
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
