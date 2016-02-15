package com.uservoice.uservoicesdk.activity;

import com.uservoice.uservoicesdk.ui.SearchAdapter;

public abstract interface SearchActivity
{
  public abstract SearchAdapter<?> getSearchAdapter();
  
  public abstract void hideSearch();
  
  public abstract void showSearch();
  
  public abstract void updateScopedSearch(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/activity/SearchActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */