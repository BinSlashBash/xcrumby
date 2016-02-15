package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormToggleButton
  extends ToggleButton
  implements FormField
{
  public FormToggleButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public FormToggleButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FormToggleButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public String getArgumentName()
  {
    return getText().toString();
  }
  
  public String getFieldValue()
  {
    if (isChecked()) {
      return "1";
    }
    return "0";
  }
  
  public String getNiceName()
  {
    return null;
  }
  
  public void setFieldValue(String paramString)
  {
    if (((!isChecked()) && (paramString.equals("1"))) || ((isChecked()) && (paramString.equals("0")))) {
      toggle();
    }
  }
  
  public void setWatcher(Watcher paramWatcher)
  {
    paramWatcher.onValueChanged();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/form/FormToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */