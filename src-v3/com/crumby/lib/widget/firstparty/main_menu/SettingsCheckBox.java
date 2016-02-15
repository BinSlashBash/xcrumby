package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.impl.crumby.UnsupportedUrlFragment;

public class SettingsCheckBox extends CheckBox {
    private String preferenceKey;

    public SettingsCheckBox(Context context) {
        super(context);
    }

    public SettingsCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingsCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (this.preferenceKey != null) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, this.preferenceKey, checked + UnsupportedUrlFragment.DISPLAY_NAME);
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(this.preferenceKey, checked).commit();
        }
    }

    public void setPreferenceKey(String preferenceKey, boolean defaultValue) {
        this.preferenceKey = preferenceKey;
        super.setChecked(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(preferenceKey, defaultValue));
    }
}
