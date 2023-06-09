package com.psu.dxj5305.chatproject.activities;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.psu.dxj5305.chatproject.utils.CustomSharedPreferences;
import com.psu.dxj5305.chatproject.R;

public class SettingsActivity extends AppCompatActivity {


    private CustomSharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new CustomSharedPreferences(this);
        super.onCreate(savedInstanceState);

    }

    public static class SettingsFragment extends PreferenceFragment {

        private CustomSharedPreferences sharedPreferences;
        private CheckBoxPreference toggleTheme;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            sharedPreferences = new CustomSharedPreferences(getActivity().getApplicationContext());
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            toggleTheme = (CheckBoxPreference) findPreference("dark");
            if (sharedPreferences.loadNightModeState()) {
                toggleTheme.isEnabled();
            }
            toggleTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean isChecked = (boolean) newValue;
                    if (isChecked) {
                        sharedPreferences.setNightModeState(true);
                    } else {
                        sharedPreferences.setNightModeState(false);
                    }
                    return true;
                }
            });

        }

    }

    public void onStart(){
        super.onStart();
        if (sharedPreferences.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.darkColorPrimaryDark));
        } else {
            setTheme(R.style.LightTheme);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.lightColorPrimaryDark));
        }
        setContentView(R.layout.settings_activity);

    }
}