package com.crumby.lib.widget.firstparty.form;

import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public abstract interface FormField
{
  public abstract String getArgumentName();
  
  public abstract String getFieldValue();
  
  public abstract String getNiceName();
  
  public abstract void setFieldValue(String paramString);
  
  public abstract void setWatcher(Watcher paramWatcher);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/form/FormField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */