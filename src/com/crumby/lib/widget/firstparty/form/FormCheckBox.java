package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.crumby.R.styleable;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormCheckBox
  extends CheckBox
  implements FormField
{
  private String argumentName;
  private String niceName;
  private Watcher watcher;
  
  public FormCheckBox(Context paramContext)
  {
    super(paramContext);
  }
  
  public FormCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842860);
  }
  
  public FormCheckBox(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setChecked(true);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FormField, paramInt, 0);
    this.niceName = paramContext.getString(1);
    this.argumentName = paramContext.getString(0);
    setFieldValue("1");
    paramContext.recycle();
  }
  
  public String getArgumentName()
  {
    return this.argumentName;
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
    return this.niceName;
  }
  
  public void setFieldValue(String paramString)
  {
    if (((!isChecked()) && (paramString.equals("1"))) || ((isChecked()) && (paramString.equals("0")))) {
      toggle();
    }
  }
  
  public void setWatcher(Watcher paramWatcher)
  {
    this.watcher = paramWatcher;
  }
  
  public void toggle()
  {
    super.toggle();
    if (this.watcher != null) {
      this.watcher.onValueChanged();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/form/FormCheckBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */