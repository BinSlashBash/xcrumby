package com.crumby.lib.widget.firstparty.omnibar;

import android.view.View;
import java.util.List;

public abstract interface BreadcrumbListModifier
{
  public abstract void addNew(UrlCrumb... paramVarArgs);
  
  public abstract int getBreadcrumbCount();
  
  public abstract List<Breadcrumb> getChildren();
  
  public abstract void scrollIntoView(View paramView);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/BreadcrumbListModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */