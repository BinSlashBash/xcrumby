package com.crumby.lib.widget.firstparty.omnibar;

import android.view.View;
import android.view.View.OnClickListener;

public class TabManager
  implements View.OnClickListener
{
  private FormTabButton current;
  private final FormTabButton[] formTabButtons;
  private final TabSwitchListener tabSwitchListener;
  
  public TabManager(TabSwitchListener paramTabSwitchListener, FormTabButton... paramVarArgs)
  {
    this.formTabButtons = paramVarArgs;
    this.tabSwitchListener = paramTabSwitchListener;
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      paramVarArgs[i].initialize(this);
      i += 1;
    }
    clear();
  }
  
  public void clear()
  {
    FormTabButton[] arrayOfFormTabButton = this.formTabButtons;
    int j = arrayOfFormTabButton.length;
    int i = 0;
    while (i < j)
    {
      arrayOfFormTabButton[i].deactivate();
      i += 1;
    }
  }
  
  public void onClick(View paramView)
  {
    if (this.current == paramView) {
      return;
    }
    clear();
    this.current = ((FormTabButton)paramView);
    this.current.activate();
    this.tabSwitchListener.onTabSwitch(this.current);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/TabManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */