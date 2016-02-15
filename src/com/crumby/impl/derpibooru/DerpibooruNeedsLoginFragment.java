package com.crumby.impl.derpibooru;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.GalleryViewer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;

public abstract class DerpibooruNeedsLoginFragment
  extends GalleryGridFragment
{
  private String user;
  
  private void deferredInitializeAdapter()
  {
    super.initializeAdapter();
  }
  
  protected void initializeAdapter() {}
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    getViewer().authLoginPromptIfNeeded("derpibooru", new AccountManagerCallback()
    {
      public void run(AccountManagerFuture<Bundle> paramAnonymousAccountManagerFuture)
      {
        try
        {
          DerpibooruNeedsLoginFragment.access$002(DerpibooruNeedsLoginFragment.this, ((Bundle)paramAnonymousAccountManagerFuture.getResult()).getString("authAccount"));
          DerpibooruNeedsLoginFragment.this.deferredInitializeAdapter();
          return;
        }
        catch (Exception paramAnonymousAccountManagerFuture)
        {
          DerpibooruNeedsLoginFragment.this.getErrorView().show(DisplayError.COULD_NOT_AUTHENTICATE_USER, paramAnonymousAccountManagerFuture.getMessage(), DerpibooruNeedsLoginFragment.this.getUrl());
        }
      }
    });
    return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }
  
  public void scrollToImageInList(int paramInt)
  {
    if (this.adapter == null) {
      return;
    }
    super.scrollToImageInList(paramInt);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruNeedsLoginFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */