/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.preference.PreferenceManager
 *  android.util.AttributeSet
 *  android.widget.CheckBox
 */
package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;

public class SettingsCheckBox
extends CheckBox {
    private String preferenceKey;

    public SettingsCheckBox(Context context) {
        super(context);
    }

    public SettingsCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SettingsCheckBox(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void setChecked(boolean bl2) {
        super.setChecked(bl2);
        if (this.preferenceKey == null) {
            return;
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, this.preferenceKey, "" + bl2 + "");
        PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).edit().putBoolean(this.preferenceKey, bl2).commit();
    }

    public void setPreferenceKey(String string2, boolean bl2) {
        this.preferenceKey = string2;
        super.setChecked(PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).getBoolean(string2, bl2));
    }
}

