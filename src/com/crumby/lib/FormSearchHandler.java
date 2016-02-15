package com.crumby.lib;

import com.crumby.lib.router.FragmentIndex;

public abstract interface FormSearchHandler
{
  public abstract int getIcon(FragmentIndex paramFragmentIndex, String paramString);
  
  public abstract String getTitle(FragmentIndex paramFragmentIndex, String paramString);
  
  public abstract String getUrlForSearch(String paramString, SearchForm paramSearchForm);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/FormSearchHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */