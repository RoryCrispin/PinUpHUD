/*
 * Rory Crispin -rorycrispin.co.uk- rozzles.com
 *
 * Distributed under the Attribution-NonCommercial-ShareAlike 4.0 International License, full conditions can be found here:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * This is free software, and you are welcome to redistribute it under certain conditions;
 *
 *  Go crazy,
 *  Rozz xx
 */

package com.rozzles.pinup.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.rozzles.pinup.R;

/**
 * Created by Rory on 11/07/2015 for PinUp
 * com.rozzles.pinup.settings
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }


}
