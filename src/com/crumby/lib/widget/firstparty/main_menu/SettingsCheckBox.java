package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;

public class SettingsCheckBox
  extends CheckBox
{
  private String preferenceKey;
  
  public SettingsCheckBox(Context paramContext)
  {
    super(paramContext);
  }
  
  public SettingsCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SettingsCheckBox(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void setChecked(boolean paramBoolean)
  {
    super.setChecked(paramBoolean);
    if (this.preferenceKey == null) {
      return;
    }
    Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, this.preferenceKey, paramBoolean + "");
    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(this.preferenceKey, paramBoolean).commit();
  }
  
  public void setPreferenceKey(String paramString, boolean paramBoolean)
  {
    this.preferenceKey = paramString;
    super.setChecked(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(paramString, paramBoolean));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/main_menu/SettingsCheckBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */